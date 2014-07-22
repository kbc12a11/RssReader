package kbc12a11.feedselect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kbc12a11.rssreader.R;
import kbc12a11.rssreader.RSSReaderActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

public class RssFeedSelectActivity extends Activity{

	int PARENT_DATA = 3;
	int CHILD_DATA = 3;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedselect);
		
		RssFeedSelectTask task = new RssFeedSelectTask(this);
		task.execute("");
	}

	public void setAdapter(SimpleExpandableListAdapter adapter) {
		ExpandableListView lv = (ExpandableListView) findViewById(R.id.feedselect_explistview);
		lv.setAdapter(adapter);
		
		 lv.setOnChildClickListener(new OnChildClickListener() {
	            @Override
	            public boolean onChildClick(ExpandableListView parent, View view,
	                    int groupPosition, int childPosition, long id) {
	                ExpandableListAdapter adapter = parent
	                        .getExpandableListAdapter();
	 
	                // クリックされた場所の内容情報を取得
	                Map<String, String> item = (Map<String, String>) adapter
	                        .getChild(groupPosition, childPosition);
	 
	                // トーストとして表示
	                Toast.makeText(
	                        getApplicationContext(),
	                        "child clicked " + item.get("title") + " "
	                                + item.get("htmlUrl") + " " + item.get("xmlUrl"), Toast.LENGTH_LONG)
	                        .show();
	                
	                Intent intent = new Intent(RssFeedSelectActivity.this,
	                		RSSReaderActivity.class);
	                
	                String title = item.get(RssFeedSelectTask.PARSE_TITLE);
	                intent.putExtra(RssFeedSelectTask.PARSE_TITLE, title);
	                
	                String htmlUrl = item.get(RssFeedSelectTask.PARSE_HTML_URL);
	                intent.putExtra(RssFeedSelectTask.PARSE_HTML_URL, htmlUrl);
	                
	                String xmlUrl = item.get(RssFeedSelectTask.PARSE_XML_URL);
	                intent.putExtra(RssFeedSelectTask.PARSE_XML_URL, xmlUrl);
	                
	                startActivity(intent);
	                return false;
	            }
	        });
	}
}
