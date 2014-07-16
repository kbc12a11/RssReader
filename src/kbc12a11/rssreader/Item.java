package kbc12a11.rssreader;

public class Item {
	/**記事のタイトル*/
	private CharSequence mTitle;
	
	/**記事の本文*/
	private CharSequence mDescription;
	
	/**記事へのリンク*/
	private CharSequence mLink;
	
	public CharSequence getLink() {
		return mLink;
	}

	public void setLink(CharSequence Link) {
		this.mLink = Link;
	}

	public Item() {
		mTitle = "";
		mDescription = "";
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public void setTitle(CharSequence Title) {
		this.mTitle = Title;
	}

	public CharSequence getDescription() {
		return mDescription;
	}

	public void setDescription(CharSequence Description) {
		this.mDescription = Description;
	}
	
	
}
