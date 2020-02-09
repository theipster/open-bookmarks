<?php

declare(strict_types=1);

namespace Step\Acceptance;

class CreatingUsersSteps extends \AcceptanceTester
{
    /**
     * @Given there is no existing user with username :username
     */
    public function thereIsNoExistingUserWithUsername(string $username)
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `there is no existing user with username :username` is not defined");
    }

    /**
     * @When I attempt to create a user with username :username
     */
    public function iAttemptToCreateAUserWithUsername(string $username)
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I attempt to create a user with username :username` is not defined");
    }

    /**
     * @Then I am told the user has been created
     */
    public function iAmToldTheUserHasBeenCreated()
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I am told the user has been created` is not defined");
    }

    /**
     * @Then I am given a link to the user
     */
    public function iAmGivenALinkToTheUser()
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I am given a link to the user` is not defined");
    }

    /**
     * @Given I can access the users service
     */
    public function iCanAccessTheUsersService()
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I can access the users service` is not defined");
    }

    /**
     * @When I attempt to create a user without a username
     */
    public function iAttemptToCreateAUserWithoutAUsername()
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I attempt to create a user without a username` is not defined");
    }

    /**
     * @Then I am told the user details are invalid
     */
    public function iAmToldTheUserDetailsAreInvalid()
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I am told the user details are invalid` is not defined");
    }

    /**
     * @Given there is an existing user with username :username
     */
    public function thereIsAnExistingUserWithUsername(string $username)
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `there is an existing user with username :username` is not defined");
    }

    /**
     * @Then I am told the user cannot be created
     */
    public function iAmToldTheUserCannotBeCreated()
    {
        throw new \PHPUnit\Framework\IncompleteTestError("Step `I am told the user cannot be created` is not defined");
    }
}
