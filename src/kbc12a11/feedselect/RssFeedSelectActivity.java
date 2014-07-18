package kbc12a11.feedselect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kbc12a11.rssreader.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;
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
	                        "child clicked " + item.get("TITLE") + " "
	                                + item.get("SUMMARY"), Toast.LENGTH_LONG)
	                        .show();
	                return false;
	            }
	        });
	}
}
