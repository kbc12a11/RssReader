package kbc12a11.rssreader;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class RSSReaderActivity extends ListActivity {

//	public static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
	public static final String RSS_FEED_URL = "http://himasoku.com/index.rdf";
	
	private ArrayList<Item> mItems;
	private RSSListAdapter mAdapter;
	
	//////メニュー
	/**更新ボタン*/
	private static final int MENU_ITEM_RELOAD = Menu.FIRST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mItems = new ArrayList<Item>();
		mAdapter = new RSSListAdapter(this, mItems);

		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Item item = mItems.get(position);
		Intent intent = new Intent(this, ItemDetailActivity.class);
		
		intent.putExtra("TITLE", item.getTitle());
		intent.putExtra("DESCRIPTION", item.getDescription());
		intent.putExtra("LINK", item.getLink());
		startActivity(intent);
	}

	/////////////////////メニュー関係
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ITEM_RELOAD, 0, this.getString(R.string.menu_reload));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// 更新
		case MENU_ITEM_RELOAD:
			// アダプタを初期化し、タスクを起動する
			mItems = new ArrayList<Item>();
			mAdapter = new RSSListAdapter(this, mItems);
			// タスクはその都度生成する
			RssParserTask task = new RssParserTask(this, mAdapter);
			task.execute(RSS_FEED_URL);
			return true;
	}
		return super.onOptionsItemSelected(item);
	}
	
	///////////////////////////めにゅーここまで
}
