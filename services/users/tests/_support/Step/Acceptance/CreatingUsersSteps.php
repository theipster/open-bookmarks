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
        // @todo Make this idempotent by finding and deleting any existing users.
    }

    /**
     * @When I attempt to create a user with username :username
     */
    public function iAttemptToCreateAUserWithUsername(string $username)
    {
        $this->sendPOST('/users', ['username' => $username]);
    }

    /**
     * @Then I am told the user has been created
     */
    public function iAmToldTheUserHasBeenCreated()
    {
        $this->seeResponseCodeIs(201);
    }

    /**
     * @Then I am given a link to the user
     */
    public function iAmGivenALinkToTheUser()
    {
        $this->seeHttpHeader('Location');

        $location = $this->grabHttpHeader('Location');
        $this->assertRegExp('/^\/users\/([0-9a-f]{8}(?:-[0-9a-f]{4}){3}-[0-9a-f]{12})$/', $location);
    }

    /**
     * @Given I can access the users service
     */
    public function iCanAccessTheUsersService()
    {
        // No-op.
    }

    /**
     * @When I attempt to create a user without a username
     */
    public function iAttemptToCreateAUserWithoutAUsername()
    {
        $this->sendPOST('/users', []);
    }

    /**
     * @Then I am told the user details are invalid
     */
    public function iAmToldTheUserDetailsAreInvalid()
    {
        $this->seeResponseCodeIs(400);
    }

    /**
     * @Given there is an existing user with username :username
     */
    public function thereIsAnExistingUserWithUsername(string $username)
    {
        $this->sendPOST('/users', ['username' => $username]);
    }

    /**
     * @Then I am told the user cannot be created
     */
    public function iAmToldTheUserCannotBeCreated()
    {
        $this->seeResponseCodeIs(422);
    }
}
