package kbc12a11.feedselect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import kbc12a11.rssreader.Item;
import kbc12a11.rssreader.RSSListAdapter;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.SimpleExpandableListAdapter;

public class RssFeedSelectTask extends AsyncTask<String, Integer, SimpleExpandableListAdapter>{
	private RssFeedSelectActivity mActivity;
	private ProgressDialog mDialog;
	
	public static final String PARSE_TYPE = "type";
	public static final String PARSE_TITLE = "title";
	public static final String PARSE_HTML_URL = "htmlUrl";
	public static final String PARSE_XML_URL = "xmlUrl";
	public static final String PARSE_RSS = "rss";
	public static final String PARSE_OUTLINE = "outline";

	public RssFeedSelectTask(RssFeedSelectActivity activity) {
		mActivity = activity;
	}

	@Override
	protected void onPreExecute() {
		mDialog = new ProgressDialog(mActivity);
		mDialog.setMessage("Now Loading...");
		mDialog.show();
	}

	@Override
	protected SimpleExpandableListAdapter doInBackground(String... params) {

		return parseXml();
	}

	@Override
	protected void onPostExecute(SimpleExpandableListAdapter result) {
		mDialog.dismiss();
		mActivity.setAdapter(result);
	}

	public SimpleExpandableListAdapter parseXml() {
		XmlPullParser parser = Xml.newPullParser();
		SimpleExpandableListAdapter mAdapter = null;
		try {
			AssetManager asset = mActivity.getResources().getAssets();
			InputStream in = asset.open("subscriptions.opml");
			parser.setInput(in, "UTF-8");

			List<Map<String, String>> category = new ArrayList<Map<String, String>>();
			List<List<Map<String, String>>> childlen = new ArrayList<List<Map<String, String>>>();
			List<Map<String, String>> childItem = null;
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;

				switch (eventType) {

				case XmlPullParser.START_TAG:

					tag = parser.getName();
					if (PARSE_OUTLINE.equals(tag)) {
						String type = parser.getAttributeValue(null, PARSE_TYPE);
						if (!PARSE_RSS.equals(type)) {//カテゴリのアウトラインだった場合
							//カテゴリ名の登録
							Map<String, String> ct = new HashMap<String, String>();
							ct.put(PARSE_TITLE, parser.getAttributeValue(null, PARSE_TITLE));
							category.add(ct);

							childItem = new ArrayList<Map<String, String>>();
						}else {//rssのアウトラインだった場合
							Map<String, String> ctItem = new HashMap<String, String>();
							ctItem = new HashMap<String, String>();
							ctItem.put(PARSE_TITLE, parser.getAttributeValue(null, PARSE_TITLE));
							ctItem.put(PARSE_HTML_URL, parser.getAttributeValue(null, PARSE_HTML_URL));
							ctItem.put(PARSE_XML_URL, parser.getAttributeValue(null, PARSE_XML_URL));
							childItem.add(ctItem);
						}
					}
					break;

				case XmlPullParser.END_TAG:
					if (PARSE_OUTLINE.equals(parser.getName()) &&
							!PARSE_RSS.equals(parser.getAttributeValue(null, PARSE_TYPE))) {
						childlen.add(childItem);
					}
					break;
				}
				eventType = parser.next();
			}
			
			//デバッグ
//			int i = 0;
//			for (List<Map<String, String>> list : childlen) {
//				int j = 0;
//				for (Map<String, String> map : list) {
//					Log.e(i+"-"+j, map.get("title"));
//					j++;
//				}
//				i++;
//			}

			mAdapter = new SimpleExpandableListAdapter(
					mActivity, category,
					android.R.layout.simple_expandable_list_item_1,
					new String[] { PARSE_TITLE }, new int[] { android.R.id.text1 },
					childlen, android.R.layout.simple_expandable_list_item_2,
					new String[] { PARSE_TITLE, PARSE_HTML_URL, PARSE_XML_URL}, new int[] {
							android.R.id.text1, android.R.id.text2 , android.R.id.text2});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mAdapter;
	}
}
