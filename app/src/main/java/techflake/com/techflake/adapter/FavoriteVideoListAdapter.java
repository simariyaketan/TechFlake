package techflake.com.techflake.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import techflake.com.techflake.R;
import techflake.com.techflake.databinding.FavoriteVideoRowItemBinding;
import techflake.com.techflake.models.video.Original;

public class FavoriteVideoListAdapter  extends RecyclerView.Adapter<FavoriteVideoListAdapter.FavoriteVideoListViewHolder>
        implements View.OnClickListener {

    Activity activity;
    List<Original> originalArrayList;
    FavoriteVideoListClickListner favoriteVideoListClickListner;
    public FavoriteVideoListAdapter(Activity activity,List<Original> originalArrayList){
        this.activity = activity;
        this.originalArrayList = originalArrayList;
    }

    @NonNull
    @Override
    public FavoriteVideoListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        FavoriteVideoRowItemBinding favoriteVideoRowItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.favorite_video_row_item,viewGroup,false);
        FavoriteVideoListViewHolder favoriteVideoListViewHolder = new FavoriteVideoListViewHolder(favoriteVideoRowItemBinding);
        favoriteVideoRowItemBinding.layoutMailFavoriteVideo.setOnClickListener(this);
        return favoriteVideoListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVideoListViewHolder favoriteVideoListViewHolder, int position) {
        Original original = originalArrayList.get(position);
        favoriteVideoListViewHolder.bind(original);
        favoriteVideoListViewHolder.favoriteVideoRowItemBinding.layoutMailFavoriteVideo.setTag(position);
    }

    @Override
    public int getItemCount() {
        return originalArrayList.size();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        int position = (int) v.getTag();
        if(viewId == R.id.layoutMailFavoriteVideo){
            if(favoriteVideoListClickListner != null)
                favoriteVideoListClickListner.playVideo(position);
        }
    }

    public class FavoriteVideoListViewHolder extends RecyclerView.ViewHolder{
        FavoriteVideoRowItemBinding favoriteVideoRowItemBinding;
        public FavoriteVideoListViewHolder(FavoriteVideoRowItemBinding favoriteVideoRowItemBinding) {
            super(favoriteVideoRowItemBinding.getRoot());
            this.favoriteVideoRowItemBinding = favoriteVideoRowItemBinding;
        }

        public void bind(Original original){
            favoriteVideoRowItemBinding.setOriginal(original);
            favoriteVideoRowItemBinding.executePendingBindings();
        }
    }

    public void setFavoriteVideoListClickListner(FavoriteVideoListClickListner favoriteVideoListClickListner){
        this.favoriteVideoListClickListner = favoriteVideoListClickListner;
    }

    public interface FavoriteVideoListClickListner {
        void playVideo(int position);
    }
}
