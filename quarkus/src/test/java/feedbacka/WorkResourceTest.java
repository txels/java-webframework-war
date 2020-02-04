package feedbacka;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.vertx.core.json.JsonObject;

//@formatter:off
@QuarkusTest
public class WorkResourceTest {

	@Test
	void postGeneratesUuid() {
		JsonObject ti = new JsonObject();
		ti.put("text", "Carles is bored");

		ResponseBody body = given()
				.contentType(ContentType.JSON)
				.body(ti.toString())
			.when()
				.post("/work")
			.then()
				.statusCode(200)
				.extract()
				.response()
				.getBody();

		// check that we get parse a UUID
		UUID uuid = UUID.fromString(body.asString());
		assertThat(body.asString(), equalTo(uuid.toString()));
	}

	@Test
	void youGetWhatYouPut() {
		JsonObject ti = new JsonObject();
		ti.put("text", "Carles is putting stuff");
		Response response = given()
				.contentType(ContentType.JSON)
				.body(ti.toString())
			.when()
				.post("/work")
			.then()
				.extract()
				.response();
		String id = response.getBody().asString();

		given()
			.when()
				.get("/work/{id}", id)
			.then()
				.statusCode(200)
				.body("text", equalTo(ti.getString("text")));
	}

}
