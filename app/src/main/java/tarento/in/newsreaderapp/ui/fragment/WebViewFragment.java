package tarento.in.newsreaderapp.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import tarento.in.newsreaderapp.R;

/**
 * Created by Randhir on 7/29/2015.
 */
public class WebViewFragment extends Fragment {

    private WebView webView;
    private static final String BUNDLE_URL_KEY = "url";
    private static final String BUNDLE_TITLE_KEY = "title";
    private String url = null;
    private String title = null;
    private ProgressBar progressBar;


    public static Bundle getBundle(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE_KEY, title);
        bundle.putString(BUNDLE_URL_KEY, url);
        return bundle;
    }

    public static Fragment getFragment(String title, String url) {
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(getBundle(title, url));
        return webViewFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString(BUNDLE_URL_KEY);
            title = bundle.getString(BUNDLE_TITLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_fragment, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView.setWebViewClient(new MyWebView());
        webView.loadUrl(url);
    }

    public class MyWebView extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
//            progressBar.setProgress(100);
        }
    }

}
