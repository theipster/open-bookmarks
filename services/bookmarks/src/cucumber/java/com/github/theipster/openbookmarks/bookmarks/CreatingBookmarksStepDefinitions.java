package com.github.theipster.openbookmarks.bookmarks;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreatingBookmarksStepDefinitions {

    private static final String UUID_REGEX = "[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";

    private static DockerComposeContainer environment = getEnvironment();
    private URL serviceHost;
    private HttpClient httpClient;
    private HttpResponse httpResponse;

    private static DockerComposeContainer getEnvironment() {
        DockerComposeContainer env = new DockerComposeContainer(new File("docker-compose.yml"))
            .withExposedService("app", 8080, Wait.forListeningPort())
            .withLocalCompose(true);
        env.start();
        return env;
    }

    private URL getHost() throws MalformedURLException {
        String internalHost = environment.getServiceHost("app", 8080);
        int internalPort = environment.getServicePort("app", 8080);
        return new URL(String.format("http://%s:%d", internalHost, internalPort));
    }

    @Before
    public void setUp() {
        httpClient = HttpClient.Factory.getDefaultInstance();
        httpResponse = null;
        serviceHost = null;
    }

    @Given("I can access the bookmarks service")
    public void i_can_access_the_bookmarks_service() throws Throwable {
        serviceHost = getHost();
    }

    @Given("there is no existing bookmark of URL {string} and title {string}")
    public void there_is_no_existing_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        // Don't create it. :)
        // @todo Make this idempotent by finding and deleting any existing bookmarks.
    }

    @When("I attempt to create a bookmark of URL {string} and title {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        String bodyContent = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
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
        httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
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
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
    }

    @When("I attempt to create a bookmark of URL {string}")
    public void i_attempt_to_create_a_bookmark_of_URL_url(String url) throws Throwable {
        String bodyContent = String.format("{\"url\": \"%s\"}", url);
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
    }
}
