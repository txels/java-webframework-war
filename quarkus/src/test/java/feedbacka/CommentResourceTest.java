package feedbacka;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import feedbacka.models.Comment;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.vertx.core.json.JsonObject;

//@formatter:off
@QuarkusTest
public class CommentResourceTest {

	@Test
	void youGetWhatYouPut() {
		JsonObject data = new JsonObject();
		data.put("text", "Carles is putting stuff");
		Response postCommentResponse = given()
				.contentType(ContentType.JSON)
				.body(data.toString())
			.when()
				.post("/work/dummy-uuid/comments/")
			.then()
				.statusCode(200)
				.extract()
				.response();

		String id = postCommentResponse.getBody().asString();
		ValidatableResponse response = given()
			.when()
				.get("/work/dummy-uuid/comments/{id}", id)
			.then()
				.statusCode(200);
		response.body("text", equalTo(data.getString("text")));
	}

	@Test
	void getAllComments() {
		String uuid = UUID.randomUUID().toString();

		JsonObject data = new JsonObject();
		data.put("workId", uuid);
		data.put("text", "Carles is putting stuff");
		Response postCommentResponse = given()
				.contentType(ContentType.JSON)
				.body(data.toString())
			.when()
				.post("/work/{id}/comments/", uuid)
			.then()
				.statusCode(200)
				.extract()
				.response();

		String id = postCommentResponse.getBody().asString();
		Response allCommentsResponse = given()
			.when()
				.get("/work/{id}/comments/", id)
			.then()
				.statusCode(200)
				.extract()
				.response();

		Comment[] response = allCommentsResponse.body().as(Comment[].class);
		for (Comment c : response) {
			assertEquals("no comment", c.getText());
		}
	}
}
