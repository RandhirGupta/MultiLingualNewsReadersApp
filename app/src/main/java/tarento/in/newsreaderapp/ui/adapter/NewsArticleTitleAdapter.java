package tarento.in.newsreaderapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tarento.in.newsreaderapp.ui.fragment.Fragment_Main;
import tarento.in.newsreaderapp.model.Article;

/**
 * Created by Randhir on 7/14/2015.
 */
public class NewsArticleTitleAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Article> mArticles = new ArrayList<>();
    public NewsArticleTitleAdapter(FragmentManager fm, List<Article> articles) {
        super(fm);

        if (articles != null) {
            mArticles.addAll(articles);
            Log.d("Articles", String.valueOf(articles));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Article article = mArticles.get(position);

        if(article == null) {
            return null;
        }

        return article.getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        Article article = mArticles.get(position);

        if(article == null) {
            return null;
        }

        Fragment f = Fragment_Main.getFragment(article.getTitle(), article.getUrl());
        System.out.println(article.getUrl());

//        switch (position) {
//            case 0:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/homepage");
//                break;
//            case 1:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/companies");
//                break;
//            case 2:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/consumer");
//                break;
//            case 3:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/opinion");
//                break;
//            case 4:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/money");
//                break;
//            case 5:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/industry");
//                break;
//            case 6:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/economy_politics");
//                break;
//            case 7:
//                f = Fragment_Main.getFragment("http://www.livemint.com/rss/lounge");
//                break;
//        }
        return f;
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }
}
