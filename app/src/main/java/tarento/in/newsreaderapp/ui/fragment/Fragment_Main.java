package tarento.in.newsreaderapp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import java.util.ArrayList;

import tarento.in.newsreaderapp.R;
import tarento.in.newsreaderapp.model.PostData;
import tarento.in.newsreaderapp.ui.activity.WebPageActivity;
import tarento.in.newsreaderapp.ui.adapter.FeedListViewAdapter;
import tarento.in.newsreaderapp.util.Connection;

/**
 * Created by Randhir on 7/14/2015.
 */
public class Fragment_Main extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private String mUrl = null;
    private ArrayList<PostData> listData;
    private Intent webIntent;
    private static final String BUNDLE_URL_KEY = "url";
    private static final String BUNDLE_TITLE_KEY = "title";


    private static Bundle getBundle(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_URL_KEY, url);
        bundle.putString(BUNDLE_TITLE_KEY, title);

        return bundle;
    }

    public static Fragment getFragment(String title, String url) {
        Fragment_Main fragment_main = new Fragment_Main();
        fragment_main.setArguments(getBundle(title, url));

        return fragment_main;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle bundle = getArguments();

        if (bundle != null) {
            mUrl = bundle.getString(BUNDLE_URL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylrView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(true);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        return view;

    }
//    OnItemClickListener onItemClickListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            PostData postData = listData.get(position);
//            Bundle bundle = new Bundle();
//            String url = bundle.getString(postData.getUrl());
//            bundle.putString("Url", url);
//            webIntent = new Intent(getActivity(), WebPageActivity.class);
//            startActivity(webIntent);
//        }
//    };

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new ReaderAsyncTask().execute();
    }


    public class ReaderAsyncTask extends AsyncTask<String, String, ArrayList<PostData>> {

        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Loading..");
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(true);
//            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//            progressDialog.show();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<PostData> doInBackground(String... params) {
            Connection connection = new Connection(mUrl);
            return connection.fetchXml();
        }

        @Override
        protected void onPostExecute(ArrayList<PostData> postDatas) {
            FeedListViewAdapter feedListViewAdapter = new FeedListViewAdapter(getActivity(), R.layout.feed_list_item, postDatas);
            recyclerView.setAdapter(feedListViewAdapter);
//            progressDialog.dismiss();
//            setListAdapter((ListAdapter) feedListViewAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }
}
