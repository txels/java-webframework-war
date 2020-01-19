package feedbacka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import feedbacka.models.Comment;
import io.micronaut.http.HttpRequest;
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
		Comment comment = new Comment("dummy-uuid", "no comment");
		MutableHttpRequest<Comment> request = HttpRequest.POST("/dummy-uuid/comments", comment);
		String commentid = client.toBlocking().retrieve(request, String.class);
		Comment response = client.toBlocking()
				.retrieve(HttpRequest.GET("/dummy-uuid/comments/" + commentid), Comment.class);
		assertEquals(comment.getText(), response.getText());
	}

	@Test
	void getAllComments() {
		String uuid = UUID.randomUUID().toString();
		Comment comment = new Comment(uuid, "no comment");
		MutableHttpRequest<Comment> request = HttpRequest.POST("/" + uuid + "/comments", comment);
		client.toBlocking().retrieve(request);
		client.toBlocking().retrieve(request);
		Comment[] response = client.toBlocking()
				.retrieve(HttpRequest.GET("/" + uuid + "/comments/"), Comment[].class);
		for (Comment c : response) {
			assertEquals("no comment", c.getText());
		}
	}
}