package com.github.theipster.openbookmarks.bookmarks;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreatingBookmarksStepDefinitions {

    private static final String UUID_REGEX = "[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";

    private String serviceHost;
    private Response response;

    @Before
    public void initializeScenario() {
        serviceHost = null;
        response = null;
    }

    @Given("I can access the bookmarks service")
    public void i_can_access_the_bookmarks_service() throws Throwable {
        serviceHost = "http://bookmarks-service:80/";   // @todo Load real service
    }

    @Given("there is no existing bookmark of URL {string} and title {string}")
    public void there_is_no_existing_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        // Don't create it. :)
        // @todo Make this idempotent by finding and deleting any existing bookmarks.
    }

    @When("I attempt to create a bookmark of URL {string} and title {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        response = new HttpRequest()
            .setBaseUri(serviceHost)
            .setBody(String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title))
            .post("/bookmarks");
    }

    @Then("I am told the bookmark was successfully created")
    public void i_am_told_the_bookmark_was_successfully_created() throws Throwable {
        assertEquals(201, response.getStatusCode());
    }

    @Then("I am given a link to the bookmark")
    public void i_am_given_a_link_to_the_bookmark() throws Throwable {
        String location = response.getHeader("Location");
        assertTrue(
            String.format("Location header %s does not match pattern /bookmarks/{uuid}.", location),
            location.matches("/bookmarks/" + UUID_REGEX)
        );
    }

    @Given("there is an existing bookmark of URL {string} and title {string}")
    public void there_is_an_existing_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        new HttpRequest()
            .setBaseUri(serviceHost)
            .setBody(String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title))
            .post("/bookmarks");
    }

    @Then("I am told the bookmark exists")
    public void i_am_told_the_bookmark_exists() throws Throwable {
        assertEquals(303, response.getStatusCode());
    }

    @Then("I am told the bookmark details are invalid")
    public void i_am_told_the_bookmark_details_are_invalid() throws Throwable {
        assertEquals(400, response.getStatusCode());
    }

    @When("I attempt to create a bookmark of title {string}")
    public void i_attempt_to_create_a_bookmark_of_title_title(String title) throws Throwable {
        response = new HttpRequest()
            .setBaseUri(serviceHost)
            .setBody(String.format("{\"title\": \"%s\"}", title))
            .post("/bookmarks");
    }

    @When("I attempt to create a bookmark of URL {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url(String url) throws Throwable {
        response = new HttpRequest()
            .setBaseUri(serviceHost)
            .setBody(String.format("{\"url\": \"%s\"}", url))
            .post("/bookmarks");
    }
}
