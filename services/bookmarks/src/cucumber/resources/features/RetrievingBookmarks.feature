Feature: Retrieving bookmarks
  As an internet user
  I want to retrieve bookmarks previously created
  So that I can access the underlying URLs

  Background: Accessing the bookmarks service
    Given I can access the bookmarks service

  Scenario Outline: Checking the existence of a known bookmark
    Given there is an existing bookmark of URL <url> and title <title>
    When I check the existence of the bookmark
    Then I am told the bookmark exists here

    Examples:
      |                url                 |       title       |
      |     'https://www.google.com/'      | 'Google homepage' |
      | 'https://github.com/fluidicon.png' | 'Github PNG logo' |
      |      'https://www.bbc.co.uk/'      |        ''         |

  Scenario Outline: Retrieving a known bookmark
    Given there is an existing bookmark of URL <url> and title <title>
    When I attempt to retrieve the bookmark
    Then I am told the bookmark exists here
    And I am given the bookmark URL <url>
    And I am given the bookmark title <title>

    Examples:
      |                url                 |       title       |
      |     'https://www.google.com/'      | 'Google homepage' |
      | 'https://github.com/fluidicon.png' | 'Github PNG logo' |
      |      'https://www.bbc.co.uk/'      |        ''         |

  Scenario Outline: Checking the existence of a non-existent bookmark
    Given there is no existing bookmark with id <id>
    When I check the existence of the bookmark with id <id>
    Then I am told the bookmark does not exist

    Examples:
      |                  id                    |
      | 'bb0f6e8b-53f2-4edf-9a7e-01392513a98f' |
      | 'fb67bfb6-d612-4d27-b7ae-dd657adba81f' |
      |            'invalid-uuid'              |

  Scenario Outline: Retrieving a non-existent bookmark
    Given there is no existing bookmark with id <id>
    When I attempt to retrieve the bookmark with id <id>
    Then I am told the bookmark does not exist

    Examples:
      |                  id                    |
      | 'bb0f6e8b-53f2-4edf-9a7e-01392513a98f' |
      | 'fb67bfb6-d612-4d27-b7ae-dd657adba81f' |
      |            'invalid-uuid'              |
