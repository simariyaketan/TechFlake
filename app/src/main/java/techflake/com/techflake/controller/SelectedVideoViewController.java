package techflake.com.techflake.controller;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import java.util.List;

import androidx.annotation.NonNull;
import io.objectbox.Box;
import techflake.com.techflake.R;
import techflake.com.techflake.Utils.ObjectBox;
import techflake.com.techflake.adapter.FavoriteVideoListAdapter;
import techflake.com.techflake.adapter.VideoListAdapter;
import techflake.com.techflake.databinding.SelectedVideoControllerBinding;
import techflake.com.techflake.models.video.Original;

public class SelectedVideoViewController  extends Controller implements FavoriteVideoListAdapter.FavoriteVideoListClickListner {

    FavoriteVideoListAdapter favoriteVideoListAdapter;
    SelectedVideoControllerBinding selectedVideoControllerBinding;
    List<Original> originalList;
    /*ObjectBox variable*/
    private Box<Original> originalBox;

    public SelectedVideoViewController() {
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        selectedVideoControllerBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                R.layout.selected_video_controller,container,false);

        originalBox = ObjectBox.get().boxFor(Original.class);
        if(originalBox == null)
            ObjectBox.init(getActivity());

        FavoriteVideoListView();

        return selectedVideoControllerBinding.getRoot();
    }

    private void FavoriteVideoListView() {
        originalList = originalBox.getAll();
        if(originalList.size() > 0) {
            selectedVideoControllerBinding.recyclerViewFavoriteVideoList.setVisibility(View.VISIBLE);
            selectedVideoControllerBinding.txtNoAnyFavorute.setVisibility(View.GONE);
            favoriteVideoListAdapter = new FavoriteVideoListAdapter(getActivity(), originalList);
            favoriteVideoListAdapter.setFavoriteVideoListClickListner(this);
            selectedVideoControllerBinding.recyclerViewFavoriteVideoList.setAdapter(favoriteVideoListAdapter);
            favoriteVideoListAdapter.notifyDataSetChanged();
        }else{
            selectedVideoControllerBinding.recyclerViewFavoriteVideoList.setVisibility(View.GONE);
            selectedVideoControllerBinding.txtNoAnyFavorute.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void playVideo(int position) {
        Original original = originalList.get(position);

        getRouter().pushController(RouterTransaction
                .with(new VideoViewController(original))
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }
}
