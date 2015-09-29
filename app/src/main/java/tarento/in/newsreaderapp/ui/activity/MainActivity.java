package tarento.in.newsreaderapp.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tarento.in.newsreaderapp.R;
import tarento.in.newsreaderapp.ui.adapter.NewsArticleTitleAdapter;
import tarento.in.newsreaderapp.model.Article;
import tarento.in.newsreaderapp.model.NewsPaper;
import tarento.in.newsreaderapp.ui.widget.CustomFontPagerTitleStrip;


public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    ViewPager viewPager;
    //    ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String[] drawerItems;
    private boolean isDrawerLocked = false;
    ArrayList<String> newPaperName = new ArrayList<String>();
    private NewsPaper mSelectedNewsPaper;
    private String mActivityTitle;
    private int mCurrentNews = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentNews = getIntent().getIntExtra("value", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager = (ViewPager) findViewById(R.id.pagerView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        setSupportActionBar(toolbar);
        mActivityTitle = getTitle().toString();
        LayoutInflater layoutInflater = getLayoutInflater();
//        View headerView = layoutInflater.inflate(R.layout.header,null,false);
//        drawerList.addHeaderView(headerView);

        if (mCurrentNews == 1) {
            CustomFontPagerTitleStrip customFontPagerTitleStrip = (CustomFontPagerTitleStrip) findViewById(R.id.pager_title_strip);
            customFontPagerTitleStrip.setCustomFont();
        }


        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        if (((ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams()).leftMargin == (int) getResources().getDimension(R.dimen.drawer_size)) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, drawerList);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            isDrawerLocked = true;
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Select A Newspaper");
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00695C")));

        if (!isDrawerLocked) {
            drawerLayout.setDrawerListener(actionBarDrawerToggle);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                getBaseContext(), R.layout.drawer_list_item, getResources().getStringArray(R.array.Activity)
        );

        drawerList.setAdapter(stringArrayAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ArrayList<NewsPaper> newsPapers = new ArrayList<>();
//        ArrayList<String> newPaperName = new ArrayList<String>();
        try {


            JSONArray baseArray = new JSONArray(loadJsonFromAsset());

            ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> stringStringHashMap;

            for (int j = 0; j < baseArray.length(); j++) {
                NewsPaper newsPaper = new NewsPaper();

                JSONObject jsonObject = baseArray.getJSONObject(j);
                String name = jsonObject.getString("name");
                newsPaper.setName(name);
                newPaperName.add(name);
                Log.d("Name", name);
                JSONArray jsonArray = jsonObject.getJSONArray("titles");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Article article = new Article();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Log.d("Details--->", jsonObject1.getString("tab_title"));
                    String tab_title = jsonObject1.getString("tab_title");
                    String url = jsonObject1.getString("url");

                    stringStringHashMap = new HashMap<String, String>();
                    stringStringHashMap.put("tab_title", tab_title);
                    article.setTitle(tab_title);
                    stringStringHashMap.put("url", url);
                    article.setUrl(url);
                    hashMapArrayList.add(stringStringHashMap);

                    newsPaper.getArticles().add(article);
                }

                newsPapers.add(newsPaper);
            }
            drawerList.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.drawer_list_item, newPaperName));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (newsPapers != null && newsPapers.size() > 0) {
            mSelectedNewsPaper = newsPapers.get(mCurrentNews);

            List<Article> articles = mSelectedNewsPaper.getArticles();

//            if (articles != null && articles.size() > 0) {
//
//                for (int i = 0; i < articles.size(); i++) {
//
//                    Article article = articles.get(i);
//                    if (article == null) {
//                        continue;
//                    }
//
//                    ActionBar.Tab tab = actionBar.newTab();
//                    tab.setText(article.getTitle());
//                    tab.setTabListener(this);
//                    actionBar.addTab(tab);
//                }
//            }

            viewPager.setAdapter(new NewsArticleTitleAdapter(getSupportFragmentManager(), articles));
        }


//        ActionBar.Tab tab1 = actionBar.newTab();
//
//        if(news == 1) {
//            tab1.setText("Top Stories New");
//        } else {
//            tab1.setText("Top Stories");
//        }
//
//        tab1.setTabListener(this);
//
//        ActionBar.Tab tab2 = actionBar.newTab();
//        tab2.setText("Companies");
//        tab2.setTabListener(this);
//
//        ActionBar.Tab tab3 = actionBar.newTab();
//        tab3.setText("Consumer");
//        tab3.setTabListener(this);
//
//        ActionBar.Tab tab4 = actionBar.newTab();
//        tab4.setText("Oponion");
//        tab4.setTabListener(this);
//
//        ActionBar.Tab tab5 = actionBar.newTab();
//        tab5.setText("Money");
//        tab5.setTabListener(this);
//
//        ActionBar.Tab tab6 = actionBar.newTab();
//        tab6.setText("Industry");
//        tab6.setTabListener(this);
//
//
//        ActionBar.Tab tab7 = actionBar.newTab();
//        tab7.setText("Economy Politics");
//        tab7.setTabListener(this);
//
//        ActionBar.Tab tab8 = actionBar.newTab();
//        tab8.setText("Lounge");
//        tab8.setTabListener(this);
//
//        actionBar.addTab(tab1);
//        actionBar.addTab(tab2);
//        actionBar.addTab(tab3);
//        actionBar.addTab(tab4);
//        actionBar.addTab(tab5);
//        actionBar.addTab(tab6);
//        actionBar.addTab(tab7);
//        actionBar.addTab(tab8);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.add(0, 0, 0, "Reload");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case 0:
//                viewPager.setAdapter(new NewsArticleTitleAdapter(getSupportFragmentManager()));
            case 1:
            default:
        }

        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(drawerList)) {
                drawerLayout.closeDrawer(drawerList);
            } else
                drawerLayout.openDrawer(drawerList);
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        for (int index = 0; index < menu.size(); index++) {
            MenuItem menuItem = menu.getItem(index);
            if (menuItem != null) {

            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            navigateTo(position);
        }
    }

    private void navigateTo(int position) {

        drawerItems = getResources().getStringArray(R.array.Activity);
        Intent intent;

        intent = new Intent(this, MainActivity.class);

        intent.putExtra("value", position);
        startActivity(intent);
        finish();

//        switch (position) {
//            case 0:
//
//                break;
//            case 2:
//                intent = new Intent(this, MainActivity.class);
//
//                intent.putExtra("value", 2);
//                startActivity(intent);
//                finish();
//                break;
//            default:
//                break;
//        }
        drawerLayout.closeDrawer(drawerList);

    }

    public String loadJsonFromAsset() {
        String json = null;

        try {

            InputStream inputStream = getApplication().getAssets().open("newspapers.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "utf-8");

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
        return json;
    }
}
