package tarento.in.newsreaderapp.parser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import tarento.in.newsreaderapp.model.PostData;

/**
 * Created by Randhir on 7/9/2015.
 */
public class HandleXml {
    ArrayList<PostData> mPostDatas = new ArrayList<PostData>();
    //    boolean[] valueIsSet = new boolean[6];
    boolean mIsItemStarted = false;

    public ArrayList<PostData> parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            event = myParser.getEventType();
            PostData postData = null;

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("item")) {
                            postData = new PostData();
                            mIsItemStarted = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (mIsItemStarted) {
                            if (name.equals("title")) {
                                postData.setTitle(text);
//                            valueIsSet[0] = true;
                            } else if (name.equals("link")) {
                                postData.setUrl(text);
//                            valueIsSet[1] = true;
                            } else if (name.equals("description")) {
                                postData.setContent(text);
//                            valueIsSet[2] = true;
                            } else if (name.equals("pubDate")) {
                                postData.setDate(text);
//                            valueIsSet[3] = true;
                            } else if (name.equals("guid")) {
                                postData.setId(text);
//                            valueIsSet[4] = true;
                            } else if (name.equals("media:thumbnail") || name.equals("image") || name.equals("media:content") || name.equals("url")) {
                                postData.setImageUrl(text);
//                            valueIsSet[5] = true;
                            } else if (name.equals("item")) {
                                mPostDatas.add(postData);

                                mIsItemStarted = false;

//                            clearCheckRssFeedItem();
                            }
                        }

//                        else if(name.equals("image")){
//                            postData.setmImg(text);
//                            valueIsSet[6] = true;
//                        }

                        break;
                }
//                if (checkRssFeedItem()) {
//                    Log.e("MM", "--------");
//                    Log.e("MM", "Title : " + postData.getTitle());
//                    Log.e("MM", "getDescription : " + postData.getContent());
//                    Log.e("MM", "getUrl : " + postData.getUrl());
//                    mPostDatas.add(postData);
//                    postData = new PostData();
//                    clearCheckRssFeedItem();
//                }

                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mPostDatas;
    }

//    public void clearCheckRssFeedItem() {
//        for (int i = 0; i < valueIsSet.length; i++) {
//            valueIsSet[i] = false;
//        }
//    }
//
//    public boolean checkRssFeedItem() {
//        int count = 0;
//        for (boolean b : valueIsSet) {
//            if (b == true)
//                count++;
//        }
//        if (count == valueIsSet.length) {
//            return true;
//        }
//        else {
//            if ((count == valueIsSet.length - 1) && (valueIsSet[valueIsSet.length - 1] == false)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}
