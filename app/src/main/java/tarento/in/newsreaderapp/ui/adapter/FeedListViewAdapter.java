package tarento.in.newsreaderapp.ui.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;

import tarento.in.newsreaderapp.R;
import tarento.in.newsreaderapp.model.PostData;
import tarento.in.newsreaderapp.ui.activity.WebPageActivity;
import tarento.in.newsreaderapp.util.ImageDownloaderTask;

/**
 * Created by Randhir on 7/13/2015.
 */
public class FeedListViewAdapter extends RecyclerView.Adapter<FeedListViewAdapter.ViewHolder> {

    private ArrayList<PostData> listData = null;
    private Context mContext;
    private ProgressBar progressBar;

    public FeedListViewAdapter(Context context, int resourceId, ArrayList<PostData> items) {
        mContext = context;
        listData = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PostData postData = listData.get(viewType);
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, null);
        ViewHolder holder = new ViewHolder(convertView);
//        progressBar = (ProgressBar)parent.findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);


//        if (convertView == null || convertView.getTag() == null) {
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        return holder;
    }

    private class OnViewClickListener implements View.OnClickListener {
        private PostData mPostData;
        private int mPosition;

        public OnViewClickListener(PostData postData, int position) {
            this.mPostData = postData;

            mPosition = position;
        }

        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();
            String url = mPostData.getUrl();
            String title = mPostData.getTitle();
            bundle.putString("Url", url);
            bundle.putString("Title", title);
            Intent webIntent = new Intent(mContext, WebPageActivity.class);
            webIntent.putExtras(bundle);
            webIntent.putExtra("list", listData);
            webIntent.putExtra("mPosition", mPosition);
            mContext.startActivity(webIntent);

        }
    }

//    OnItemClickListener onItemClickListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            PostData postData = listData.get(position);
//            Bundle bundle = new Bundle();
//            String url = bundle.getString(postData.getUrl());
//            bundle.putInt("Url", Integer.parseInt(url));
//            webIntent = new Intent(mContext, WebPageActivity.class);
//            mContext.startActivity(webIntent);
//        }
//    };

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PostData postData = listData.get(position);

        holder.titleTextView.setText(postData.getTitle());
        if (holder.thumbImageView != null) {
            new ImageDownloaderTask(holder.thumbImageView).execute(postData.getImageUrl());
        }
        holder.dateTextView.setText(postData.getDate());
        holder.descTextView.setText(postData.getContent());
        holder.titleTextView.setOnClickListener(new OnViewClickListener(postData, position));
//        holder.titleTextView.setClickable(true);
//        holder.titleTextView.setMovementMethod(LinkMovementMethod.getInstance());

//        holder.txtUrlView.setText(postData.getId());
//        if(holder.imgView != null){
//            new ImageDownloaderTask(holder.imgView).execute(postData.getmImg());
//        }
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView titleTextView;
        ImageView thumbImageView;
        TextView descTextView;
        TextView txtUrlView;
        ImageView imgView;
        Application application;
//
//        public ImageView getImgView() {
//            imgView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            return imgView;
//        }
//
//                public void viewFullScreen(){
//            thumbImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   getImgView();
//                }
//            });
//        }

        public ViewHolder(View convertView) {
            super(convertView);
            titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);

            dateTextView = (TextView) convertView.findViewById(R.id.date_text_view);
            thumbImageView = (ImageView) convertView.findViewById(R.id.feedImage1);
            descTextView = (TextView) convertView.findViewById(R.id.desc_text_view);

//            txtUrlView = (TextView) convertView.findViewById(R.id.txtUrl);
//            imgView = (ImageView) convertView.findViewById(R.id.feedImage1);

        }
    }
}
