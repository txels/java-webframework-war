package feedbacka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import feedbacka.models.TextItem;
import io.micronaut.http.HttpRequest;
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
	void postGeneratesUuid() {
		TextItem ti = new TextItem("Carles is bored");
		MutableHttpRequest<TextItem> request = HttpRequest.POST("/", ti);
		String response = client.toBlocking().retrieve(request);
		assertEquals(36, response.length());
	}

	@Test
	void youGetWhatYouPut() {
		TextItem ti = new TextItem("Carles is bored");
		MutableHttpRequest<TextItem> request = HttpRequest.POST("/", ti);
		String id = client.toBlocking().retrieve(request, String.class);
		TextItem response = client.toBlocking().retrieve(HttpRequest.GET(id), TextItem.class);
		assertEquals(ti.getText(), response.getText());
		assertEquals(36, ti.getId().length());
	}

}
