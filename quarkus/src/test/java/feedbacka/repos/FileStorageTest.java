package feedbacka.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import feedbacka.models.TextItem;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class FileStorageTest {

	@Inject
	FileStorage storage;

	void deleteRecursive(File path) throws IOException {
		if (!path.exists())
			return;
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				deleteRecursive(f);
			}
		}
		path.delete();
	}

	@AfterEach
	void clearTestData() throws IOException {
		this.verifyConfigForTesting();
		deleteRecursive(new File(storage.getLocation()));
	}

	@Test
	void verifyConfigForTesting() {
		assertEquals("_data-test", storage.getLocation());
	}

	@Test
	void testGetWhatYouPut() throws IOException {
		String content = "Storage testGetWhatYouPut";
		String id = storage.put(new TextItem(content));
		TextItem textItem = storage.get(id, TextItem.class);

		assertEquals(content, textItem.getText());
	}

}
