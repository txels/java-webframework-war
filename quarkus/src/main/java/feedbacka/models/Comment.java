package feedbacka.models;

/**
 * A comment on a piece of work
 */
public class Comment {

	public Comment(String workId, String text) {
		this.workId = workId;
		this.text = text;
		this.createId();
	}

	public Comment() {
        this(null, null);
	};


	public String getWorkId() {
		return this.workId;
	}

	public void setWorkId(String workId) {
        this.workId = workId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id == null) {
			createId();
		} else {
			this.id = id;
		}
	}

	private void createId() {
		this.id = Long.toHexString(System.currentTimeMillis());
	}

	private String workId;
	private String text;
	private String id;
}
