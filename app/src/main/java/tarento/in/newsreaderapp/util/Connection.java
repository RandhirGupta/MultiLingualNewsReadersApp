package tarento.in.newsreaderapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import tarento.in.newsreaderapp.model.PostData;
import tarento.in.newsreaderapp.parser.HandleXml;

/**
 * Created by Randhir on 7/9/2015.
 */
public class Connection {
    private String urlStr = null;

    public Connection(String url) {
        this.urlStr = url;
    }

    public ArrayList<PostData> fetchXml() {

        ArrayList<PostData> data = null;
        InputStream stream = null;
        try {
            stream = getResponse();

            if (stream == null) {
                return null;
            }

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlFactoryObject.newPullParser();

            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(stream, null);

            HandleXml handleXml = new HandleXml();
            data = handleXml.parseXMLAndStoreIt(xmlPullParser);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    private InputStream getResponse() {
        InputStream stream = null;
        try {
            URL url = new URL(urlStr);
            Log.e("MMMM", "urlStr : " + urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(40000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            Log.e("MMM", "getResponseCode : " + conn.getResponseCode());

            stream = conn.getInputStream();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return stream;
        }
    }

//    public static boolean isNetworkConnected(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null) {
//                if (networkInfo.isConnected()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
