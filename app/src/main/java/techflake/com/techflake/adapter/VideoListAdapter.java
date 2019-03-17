package techflake.com.techflake.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import techflake.com.techflake.R;
import techflake.com.techflake.Utils.Common;
import techflake.com.techflake.databinding.VideoRowItemBinding;
import techflake.com.techflake.models.video.Datum;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder>
        implements View.OnClickListener {

    VideoListClickListner videoListClickListner;
    Activity activity;
    ArrayList<Datum> datumArrayList;
    public VideoListAdapter(Activity activity,ArrayList<Datum> datumArrayList){
        this.activity = activity;
        this.datumArrayList = datumArrayList;
    }
    @NonNull
    @Override
    public VideoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        VideoRowItemBinding videoRowItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.video_row_item,parent,false);
        VideoListViewHolder videoListViewHolder = new VideoListViewHolder(videoRowItemBinding);
        videoRowItemBinding.layoutMailVideo.setOnClickListener(this);
        videoRowItemBinding.layoutUpVote.setOnClickListener(this);
        videoRowItemBinding.layoutDownVote.setOnClickListener(this);
        return videoListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewHolder videoListViewHolder, int position) {
        Datum datum = datumArrayList.get(position);
        videoListViewHolder.bind(datum);
        videoListViewHolder.videoRowItemBinding.layoutMailVideo.getLayoutParams().height = Common.getDeviceWidth(activity)/2;
        videoListViewHolder.videoRowItemBinding.layoutMailVideo.setTag(position);
        videoListViewHolder.videoRowItemBinding.layoutUpVote.setTag(position);
        videoListViewHolder.videoRowItemBinding.layoutDownVote.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        int position = (int) v.getTag();
        if(viewId == R.id.layoutMailVideo){
            if(videoListClickListner != null)
                videoListClickListner.playVideo(position);
        }else if(viewId == R.id.layoutUpVote){
            if(videoListClickListner != null)
                videoListClickListner.videoUpVote(position);
        }else if(viewId == R.id.layoutDownVote){
            if(videoListClickListner != null)
                videoListClickListner.videoDownVote(position);
        }
    }

    public class VideoListViewHolder extends RecyclerView.ViewHolder{
        VideoRowItemBinding videoRowItemBinding;
        public VideoListViewHolder(VideoRowItemBinding videoRowItemBinding) {
            super(videoRowItemBinding.getRoot());
            this.videoRowItemBinding = videoRowItemBinding;
        }
        public void bind(Datum datum){
            videoRowItemBinding.setDatum(datum);
            videoRowItemBinding.executePendingBindings();
        }
    }

    public void setVideoListClickListner(VideoListClickListner videoListClickListner){
        this.videoListClickListner = videoListClickListner;
    }

    public interface VideoListClickListner{
        void playVideo(int position);
        void videoUpVote(int position);
        void videoDownVote(int position);
    }
}
