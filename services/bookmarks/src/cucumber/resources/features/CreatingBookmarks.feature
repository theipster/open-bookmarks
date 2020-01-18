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
      |          url          |       title       |
      | 'https://github.com/' | 'Github homepage' |

  Scenario Outline: Creating a bookmark with an invalid URL
    When I attempt to create a bookmark of URL <url> and title <title>
    Then I am told the bookmark details are invalid

    Examples:
      |     url     |       title       |
      |     ''      | 'Google homepage' |
      | 'not a url' | 'Github homepage' |

  Scenario Outline: Creating a bookmark with a missing URL
    When I attempt to create a bookmark of title <title>
    Then I am told the bookmark details are invalid

    Examples:
      |       title       |
      | 'Google homepage' |

  Scenario Outline: Creating a bookmark with a missing title
    When I attempt to create a bookmark of URL <url>
    Then I am told the bookmark details are invalid

    Examples:
      |            url            |
      | 'https://www.google.com/' |
