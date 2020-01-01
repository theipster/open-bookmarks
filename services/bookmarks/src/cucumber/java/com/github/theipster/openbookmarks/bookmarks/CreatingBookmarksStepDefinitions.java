package com.github.theipster.openbookmarks.bookmarks;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import cucumber.api.PendingException;	// io.cucumber:cucumber-java:4.*
import java.net.URL;
import java.util.Optional;

public class CreatingBookmarksStepDefinitions {

    @Given("I can access the bookmarks service")
    public void i_can_access_the_bookmarks_service() throws Throwable {
	throw new PendingException();
    }

    @Given("there is no existing bookmark of URL {url} and title {string}")
    public void there_is_no_existing_bookmark_of_URL_url_and_title_title(Optional<URL> url, String title) throws Throwable {
	throw new PendingException();
    }

    @When("I attempt to create a bookmark of URL {url} and title {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url_and_title_title(Optional<URL> url, String title) throws Throwable {
	throw new PendingException();
    }

    @Then("I am told the bookmark was successfully created")
    public void i_am_told_the_bookmark_was_successfully_created() throws Throwable {
	throw new PendingException();
    }

    @Then("I am given a link to the bookmark")
    public void i_am_given_a_link_to_the_bookmark() throws Throwable {
	throw new PendingException();
    }

    @Given("there is an existing bookmark of URL {url} and title {string}")
    public void there_is_an_existing_bookmark_of_URL_url_and_title_title(Optional<URL> url, String title) throws Throwable {
	throw new PendingException();
    }

    @Then("I am told the bookmark exists")
    public void i_am_told_the_bookmark_exists() throws Throwable {
	throw new PendingException();
    }

    @Then("I am told the bookmark details are invalid")
    public void i_am_told_the_bookmark_details_are_invalid() throws Throwable {
	throw new PendingException();
    }
}
