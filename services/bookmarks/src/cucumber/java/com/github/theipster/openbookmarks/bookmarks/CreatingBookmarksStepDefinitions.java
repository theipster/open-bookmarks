package com.github.theipster.openbookmarks.bookmarks;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreatingBookmarksStepDefinitions {

    private static final String UUID_REGEX = "[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";

    private String serviceHost;
    private HttpClient httpClient;
    private HttpResponse httpResponse;

    @Before
    public void setUp() {
        httpClient = HttpClient.Factory.getDefaultInstance();
        httpResponse = null;
        serviceHost = null;
    }

    @Given("I can access the bookmarks service")
    public void i_can_access_the_bookmarks_service() throws Throwable {
        serviceHost = "http://bookmarks-service:80";   // @todo Load real service
    }

    @Given("there is no existing bookmark of URL {string} and title {string}")
    public void there_is_no_existing_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        // Don't create it. :)
        // @todo Make this idempotent by finding and deleting any existing bookmarks.
    }

    @When("I attempt to create a bookmark of URL {string} and title {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        String bodyContent = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);
        httpResponse = httpClient.post(serviceHost + "/bookmarks", bodyContent);
    }

    @Then("I am told the bookmark was successfully created")
    public void i_am_told_the_bookmark_was_successfully_created() throws Throwable {
        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
    }

    @Then("I am given a link to the bookmark")
    public void i_am_given_a_link_to_the_bookmark() throws Throwable {
        assertTrue(httpResponse.containsHeader("Location"));
        String location = httpResponse.getFirstHeader("Location").getValue();
        assertTrue(
            String.format("Location header %s does not match pattern /bookmarks/{uuid}.", location),
            location.matches("/bookmarks/" + UUID_REGEX)
        );
    }

    @Given("there is an existing bookmark of URL {string} and title {string}")
    public void there_is_an_existing_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        String bodyContent = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);
        httpClient.post(serviceHost + "/bookmarks", bodyContent);
    }

    @Then("I am told the bookmark exists")
    public void i_am_told_the_bookmark_exists() throws Throwable {
        assertEquals(303, httpResponse.getStatusLine().getStatusCode());
    }

    @Then("I am told the bookmark details are invalid")
    public void i_am_told_the_bookmark_details_are_invalid() throws Throwable {
        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
    }

    @When("I attempt to create a bookmark of title {string}")
    public void i_attempt_to_create_a_bookmark_of_title_title(String title) throws Throwable {
        String bodyContent = String.format("{\"title\": \"%s\"}", title);
        httpClient.post(serviceHost + "/bookmarks", bodyContent);
    }

    @When("I attempt to create a bookmark of URL {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url(String url) throws Throwable {
        String bodyContent = String.format("{\"url\": \"%s\"}", url);
        httpResponse = httpClient.post(serviceHost + "/bookmarks", bodyContent);
    }
}
