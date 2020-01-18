package feedbacka.models;

import java.util.UUID;

public class TextItem {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextItem other = (TextItem) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public TextItem() {
	};

	public TextItem(String content) {
		this.text = content;
		this.createId();
	}

	public String getText() {
		return this.text;
	}

	public void setText(String content) {
		this.text = content;
	}

	private String createId() {
		id = UUID.randomUUID().toString();
		return id;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String text;

}
