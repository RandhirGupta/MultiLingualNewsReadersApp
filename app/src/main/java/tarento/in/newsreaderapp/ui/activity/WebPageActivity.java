package tarento.in.newsreaderapp.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import tarento.in.newsreaderapp.R;
import tarento.in.newsreaderapp.model.PostData;
import tarento.in.newsreaderapp.ui.adapter.WebViewAdapter;

public class WebPageActivity extends ActionBarActivity {

//    private WebView webView;
    private PostData postData;
//    private ProgressBar progressBar;
    private Button backButton;
    private ViewPager viewPager;
    private int setCurrentItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_page);
//        webView = (WebView) findViewById(R.id.webView);
        final ArrayList<PostData> datas = (ArrayList<PostData>) getIntent().getSerializableExtra("list");

        setCurrentItem = getIntent().getIntExtra("mPosition", 0);

        if (datas != null) {
            for(int i = 0; i < datas.size(); i++) {
                PostData postData = datas.get(i);
                Log.d("MMM", "-------");
                Log.d("MMM", postData.getTitle());
                Log.d("MMM", postData.getUrl());
            }
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        viewPager = (ViewPager) findViewById(R.id.pagerView);

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
        Bundle bundle = this.getIntent().getExtras();
//        String url = bundle.getString("Url");
        String title = bundle.getString("Title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00695C")));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        webView.loadUrl(url);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                viewPager.setCurrentItem(position);

                PostData postData = datas.get(position);

                getSupportActionBar().setTitle(postData.getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new WebViewAdapter(getSupportFragmentManager(), datas));
        viewPager.setCurrentItem(setCurrentItem);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_page, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.share) {
//            shareNews();
//        }
//        if (id == android.R.id.home) {
////            onBackPressed();
//            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    Intent homeIntent = new Intent(WebPageActivity.this, MainActivity.class);
//                    homeIntent.addFlags(homeIntent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(homeIntent);
//                    return true;
//                }
//            });
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    public void shareNews() {
//
//        Intent shareNews = new Intent(Intent.ACTION_SEND);
//        shareNews.setType("text/plain");
//        shareNews.addFlags(shareNews.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        shareNews.putExtra(Intent.EXTRA_SUBJECT, getSupportActionBar().getTitle());
//        Log.d("Title Retrieved", (String) getSupportActionBar().getTitle());
//        shareNews.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
//        startActivity(Intent.createChooser(shareNews, "Share Via"));
//
//    }

//    public class MyWebView extends WebViewClient {
//
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
////            progressBar.setVisibility(View.VISIBLE);
//            view.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            progressBar.setVisibility(View.GONE);
//        }
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
