Feature: Creating bookmarks
  As an internet user
  I want to create bookmarks to URLs
  So that I can refer back to the URLs in future

  Background: Accessing the bookmarks service
    Given I can access the bookmarks service

  Scenario Outline: Creating a new bookmark
    Given there is no existing bookmark of URL <url> and title <title>
    When I attempt to create a bookmark of URL <url> and title <title>
    Then I am told the bookmark exists
    And I am given a link to the bookmark

    Examples:
      |                url                 |       title       |
      |     'https://www.google.com/'      | 'Google homepage' |
      | 'https://github.com/fluidicon.png' | 'Github PNG logo' |
      |      'https://www.bbc.co.uk/'      |        ''         |

  Scenario Outline: Creating an existing bookmark
    Given there is an existing bookmark of URL <url> and title <title>
    When I attempt to create a bookmark of URL <url> and title <title>
    Then I am told the bookmark exists
    And I am given a link to the bookmark

    Examples:
      |            url            |       title       |
      | 'https://www.google.com/' | 'Google homepage' |
      |   'https://github.com/'   | 'Github homepage' |

  Scenario Outline: Creating a bookmark with an invalid URL
    When I attempt to create a bookmark of URL <url> and title <title>
    Then I am told the bookmark details are invalid

    Examples:
      |     url     |       title       |
      |     ''      | 'Google homepage' |
      | 'not a url' | 'Github homepage' |

  Scenario: Creating a bookmark with a missing URL
    When I attempt to create a bookmark of title 'Google homepage'
    Then I am told the bookmark details are invalid

  Scenario: Creating a bookmark with a missing title
    When I attempt to create a bookmark of URL 'https://www.google.com/'
    Then I am told the bookmark details are invalid

  Scenario: Malicious attempt to overwrite existing bookmark's URL
    Given there is an existing bookmark of URL 'https://www.google.com/' and title 'Google'
    When I attempt to change the bookmark to URL 'https://www.bing.com/'
    Then I am told the bookmark details are invalid

  Scenario: Malicious attempt to overwrite existing bookmark's title
    Given there is an existing bookmark of URL 'https://www.google.com/' and title 'Google'
    When I attempt to change the bookmark to title 'Bing'
    Then I am told the bookmark details are invalid

  Scenario: Malicious attempt to overwrite existing bookmark's URL and title
    Given there is an existing bookmark of URL 'https://www.google.com/' and title 'Google'
    When I attempt to change the bookmark to URL 'https://www.bing.com/' and title 'Bing'
    Then I am told the bookmark exists
    And I am given a link to the bookmark
