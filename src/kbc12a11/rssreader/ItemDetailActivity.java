package kbc12a11.rssreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {
	private WebView mWeb;

	private ProgressDialog mDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		mDialog = new ProgressDialog(this);
		mDialog.setMessage("Now Loading...");
		
		Intent intent = getIntent();

		String title = intent.getStringExtra("TITLE");
		setTitle(title);
		
		String link = intent.getStringExtra("LINK");
		mWeb = (WebView)findViewById(R.id.view_web);
		
		mWeb.getSettings().setAppCacheEnabled(true);
		mWeb.getSettings().setAppCacheMaxSize(8 * 1024 * 1024);
		mWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		mWeb.getSettings().setLoadWithOverviewMode(true);
		mWeb.getSettings().setUseWideViewPort(true);
		
		mWeb.setWebViewClient(new WebViewClient() {
            
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				mDialog.show();
			}

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mDialog.dismiss();
            }
        });
		
		mWeb.loadUrl(link);
	}
}