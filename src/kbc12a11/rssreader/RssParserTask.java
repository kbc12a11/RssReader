package kbc12a11.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

public class RssParserTask extends AsyncTask<String, Integer, RSSListAdapter>{

	private RSSReaderActivity mActivity;
	private RSSListAdapter mAdapter;
	private ProgressDialog mDialog;

	public RssParserTask(RSSReaderActivity activity, RSSListAdapter adapter) {
		mActivity = activity;
		mAdapter = adapter;
	}

	@Override
	protected void onPreExecute() {
		mDialog = new ProgressDialog(mActivity);
		mDialog.setMessage("Now Loading...");
		mDialog.show();
	}


	@Override
	protected RSSListAdapter doInBackground(String... arg0) {
		RSSListAdapter result = null;

		try {
			URL url = new URL(arg0[0]);
			InputStream in = url.openConnection().getInputStream();
			result = perseXml(in);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(RSSListAdapter result) {
		mDialog.dismiss();
		mActivity.setListAdapter(result);
	}

	public RSSListAdapter perseXml(InputStream in) throws IOException, XmlPullParserException {
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "UTF-8");
			int eventType = parser.getEventType();
			Item currentItem = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;
				switch (eventType) {
					case XmlPullParser.START_TAG:
						tag = parser.getName();
						if (tag.equals("item")) {
							currentItem = new Item();
						} else if (currentItem != null) {
							if (tag.equals("title")) {
								currentItem.setTitle(parser.nextText());
							} else if (tag.equals("description")) {
								currentItem.setDescription(parser.nextText());
							} else if (tag.equals("link")) {
								currentItem.setLink(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						tag = parser.getName();
						if (tag.equals("item")) {
							mAdapter.add(currentItem);
						}
						break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mAdapter;
	}
}
