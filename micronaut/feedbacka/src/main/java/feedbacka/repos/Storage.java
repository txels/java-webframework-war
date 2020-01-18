package feedbacka.repos;

import java.io.IOException;

import feedbacka.models.Comment;
import feedbacka.models.TextItem;

public interface Storage {
	public <T> T get(String id, Class<T> clazz) throws IOException;

	public String put(TextItem item) throws IOException;

	public String put(Comment comment) throws IOException;

	public Iterable<Comment> getAll(String workid) throws IOException;
}
