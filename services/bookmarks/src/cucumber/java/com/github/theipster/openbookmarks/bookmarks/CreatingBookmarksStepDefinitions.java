package com.github.theipster.openbookmarks.bookmarks;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;
import org.apache.http.HttpResponse;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreatingBookmarksStepDefinitions {

    private static final String BOOKMARK_URI_REGEX = "/bookmarks/([0-9a-f]{8}(?:-[0-9a-f]{4}){3}-[0-9a-f]{12})";

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

    private URL getHost(DockerComposeContainer environment) throws MalformedURLException {
        String internalHost = environment.getServiceHost("app", 8080);
        int internalPort = environment.getServicePort("app", 8080);
        return new URL(String.format("http://%s:%d", internalHost, internalPort));
    }

    private URI extractLocationHeaderFromHttpResponse(HttpResponse httpResponse) throws LastResponseMissingLocationHeaderException, URISyntaxException {
        if (!httpResponse.containsHeader("Location")) {
            throw new LastResponseMissingLocationHeaderException();
        }
        String location = httpResponse.getFirstHeader("Location").getValue();
        return new URI(location);
    }

    private UUID extractUUIDFromBookmarkURI(URI uri) throws IllegalArgumentException, IllegalStateException, IndexOutOfBoundsException {
        Matcher matcher = Pattern.compile(BOOKMARK_URI_REGEX).matcher(uri.toString());
        matcher.matches();  // lolJava
        return UUID.fromString(matcher.group(1));
    }

    @Before
    public void setUp() {
        httpClient = HttpClient.Factory.getDefaultInstance();
        httpResponse = null;
        serviceHost = null;
    }

    @Given("I can access the bookmarks service")
    public void i_can_access_the_bookmarks_service() throws Throwable {
        serviceHost = getHost(environment);
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
        URI uri = extractLocationHeaderFromHttpResponse(httpResponse);
        UUID id = extractUUIDFromBookmarkURI(uri);
    }

    @Given("there is an existing bookmark of URL {string} and title {string}")
    public void there_is_an_existing_bookmark_of_URL_url_and_title_title(String url, String title) throws Throwable {
        String bodyContent = String.format("{\"url\": \"%s\", \"title\": \"%s\"}", url, title);
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
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

    @When("I attempt to change the bookmark to URL {string}")
    public void i_attempt_to_change_the_bookmark_to_URL_url(String url) throws Throwable {
        URI uri = extractLocationHeaderFromHttpResponse(httpResponse);
        UUID id = extractUUIDFromBookmarkURI(uri);
        String bodyContent = String.format("{\"id\": \"%s\", \"url\": \"%s\"}", id.toString(), url);
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
    }

    @When("I attempt to change the bookmark to title {string}")
    public void i_attempt_to_change_the_bookmark_to_title_title(String title) throws Throwable {
        URI uri = extractLocationHeaderFromHttpResponse(httpResponse);
        UUID id = extractUUIDFromBookmarkURI(uri);
        String bodyContent = String.format("{\"id\": \"%s\", \"title\": \"%s\"}", id.toString(), title);
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
    }

    @When("I attempt to change the bookmark to URL {string} and title {string}")
    public void i_attempt_to_change_the_bookmark_to_URL_url_and_title_title(String url, String title) throws Throwable {
        URI uri = extractLocationHeaderFromHttpResponse(httpResponse);
        UUID id = extractUUIDFromBookmarkURI(uri);
        String bodyContent = String.format("{\"id\": \"%s\", \"url\": \"%s\", \"title\": \"%s\"}", id.toString(), url, title);
        httpResponse = httpClient.postJson(serviceHost.toString() + "/bookmarks", bodyContent);
    }

    class LastResponseMissingLocationHeaderException extends RuntimeException {
    }
}
