package tarento.in.newsreaderapp.model;

import java.io.Serializable;

/**
 * Created by Randhir on 7/9/2015.
 */
public class PostData implements Serializable {
    private String mTitle;
    private String mDate;
    private String mImageUrl;
    private String mId;
    private String mContent;
    private String mUrl;
    private String mImg;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
        if(content.contains("<img")){
            String img = content.substring(content.indexOf("<img"));
            String cleanUp= img.substring(0,img.indexOf(">")+1);
            img = img.substring(img.indexOf("src=")+5);
            int indexOf=img.indexOf("");
            if(indexOf==-1){
                indexOf = img.indexOf("\"");
            }
            img = img.substring(0,indexOf);
            setImageUrl(img);
            this.mContent = this.mContent.replace(cleanUp,"");
        }
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }
}


