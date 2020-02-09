Feature: Creating users
  In order to operate on bookmarks
  As an internet user
  I need to register as a user

  Background: Accessing the users service
    Given I can access the users service

  Scenario Outline: Creating a user with a valid username
    Given there is no existing user with username <username>
    When I attempt to create a user with username <username>
    Then I am told the user has been created
    And I am given a link to the user

    Examples:
      | username    |
      | "jane-doe"  |
      | "jimbob123" |

  Scenario: Creating a user with a missing username
    When I attempt to create a user without a username
    Then I am told the user details are invalid

  Scenario: Creating a user with a taken username
    Given there is an existing user with username "joebloggs"
    When I attempt to create a user with username "joebloggs"
    Then I am told the user cannot be created
