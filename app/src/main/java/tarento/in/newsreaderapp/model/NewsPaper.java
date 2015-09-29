package tarento.in.newsreaderapp.model;

import android.support.v7.app.ActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randhir on 7/21/2015.
 */
public class NewsPaper {
    private String name;
    private List<Article> articles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        if(articles == null) {
            articles = new ArrayList<Article>();
        }
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
