package feedbacka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;

@MicronautTest
public class WorkControllerSpec {
	@Inject
	EmbeddedServer server;

	@Inject
	@Client("/work")
	HttpClient client;

	@Test
	void putRespondsWithUuid() {
		MutableHttpRequest<String> request = HttpRequest.POST("/", "Carles is bored")
				.contentType(MediaType.TEXT_PLAIN_TYPE);
		String response = client.toBlocking().retrieve(request);
		assertEquals(36, response.length());
	}

	@Test
	void youGetWhatYouPut() {
		MutableHttpRequest<String> request = HttpRequest.POST("/", "Carles is bored")
				.contentType(MediaType.TEXT_PLAIN_TYPE);
		String id = client.toBlocking().retrieve(request, String.class);
		String response = client.toBlocking().retrieve(HttpRequest.GET(id));
		assertEquals("Carles is bored", response);
	}

}
