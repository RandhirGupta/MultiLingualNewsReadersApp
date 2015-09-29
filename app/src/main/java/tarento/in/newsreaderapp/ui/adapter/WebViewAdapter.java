package tarento.in.newsreaderapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import tarento.in.newsreaderapp.model.PostData;
import tarento.in.newsreaderapp.ui.fragment.WebViewFragment;

/**
 * Created by Randhir on 7/29/2015.
 */
public class WebViewAdapter extends FragmentStatePagerAdapter {
    ArrayList<PostData> mPostDatas = new ArrayList<>();

    public WebViewAdapter(FragmentManager fm, List<PostData> listPostData) {
        super(fm);

        if (listPostData != null) {
            mPostDatas.addAll(listPostData);
        }
    }

    @Override
    public Fragment getItem(int position) {
        PostData postData = mPostDatas.get(position);
        if (postData == null) {
            return null;
        }
        Fragment fragment = WebViewFragment.getFragment(postData.getTitle(),postData.getUrl());
        return fragment;
    }

    @Override
    public int getCount() {
        return mPostDatas.size();
    }
}
