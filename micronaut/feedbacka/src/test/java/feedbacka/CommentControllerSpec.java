package feedbacka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.UUID;

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
public class CommentControllerSpec {
	@Inject
	EmbeddedServer server;

	@Inject
	@Client("/work")
	HttpClient client;

	@Test
	void youGetWhatYouPut() {
		MutableHttpRequest<String> request = HttpRequest.POST("/dummy-uuid/comments", "no comment")
				.contentType(MediaType.TEXT_PLAIN_TYPE);
		String commentid = client.toBlocking().retrieve(request, String.class);
		String response = client.toBlocking().retrieve(HttpRequest.GET("/dummy-uuid/comments/" + commentid));
		assertEquals("no comment", response);
	}

	@Test
	void getAllComments() {
		String uuid = UUID.randomUUID().toString();
		MutableHttpRequest<String> request = HttpRequest.POST("/" + uuid + "/comments", "no comment")
				.contentType(MediaType.TEXT_PLAIN_TYPE);
		client.toBlocking().retrieve(request, String.class);
		client.toBlocking().retrieve(request, String.class);
		Iterable<String> response = client.toBlocking().retrieve(HttpRequest.GET("/" + uuid + "/comments/"),
				Iterable.class);
		assertEquals(Arrays.asList("no comment", "no comment"), response);
	}
}