package techflake.com.techflake.controller;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import techflake.com.techflake.Interface.VideoViewInterface;
import techflake.com.techflake.R;
import techflake.com.techflake.Utils.Common;
import techflake.com.techflake.adapter.VideoListAdapter;
import techflake.com.techflake.databinding.HomeControllerBinding;
import techflake.com.techflake.models.video.Datum;
import techflake.com.techflake.models.video.Meta;
import techflake.com.techflake.models.video.Original;
import techflake.com.techflake.models.video.VideoClass;
import techflake.com.techflake.presenter.VideoPresenter;
import static techflake.com.techflake.network.Constant.progressDialog;

public class HomeController extends Controller implements VideoViewInterface,VideoListAdapter.VideoListClickListner{

    VideoListAdapter videoListAdapter;
    ArrayList<Datum> datumArrayList;
    VideoPresenter videoPresenter;

    HomeControllerBinding homeControllerBinding;

    public HomeController() {
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

        homeControllerBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),R.layout.home_controller,
                container,false);

        VideoListView();
        setVideoMVP();
        getVideoList();

        return homeControllerBinding.getRoot();
    }

    private void VideoListView() {
        datumArrayList = new ArrayList<>();
        videoListAdapter = new VideoListAdapter(getActivity(),datumArrayList);
        videoListAdapter.setVideoListClickListner(this);
        homeControllerBinding.recyclerViewVideoList.setAdapter(videoListAdapter);

        homeControllerBinding.imgFavoriteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRouter().pushController(RouterTransaction
                        .with(new SelectedVideoViewController())
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler()));
            }
        });
    }

    private void setVideoMVP() {
        videoPresenter = new VideoPresenter(this);
    }

    private void getVideoList() {
        if(Common.isNetworkAvailable(getActivity())) {
            progressDialog = ProgressDialog.show(getActivity(), "", "Please Wait...");
            videoPresenter.getVideoList();
        }else{
            Common.ShowToast(getActivity(),"Opps! No Internet Connection.");
        }
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
    }

    @Override
    public void showToast(String str) {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();
        Common.ShowToast(getActivity(),str);
    }

    @Override
    public void displayClientOrder(VideoClass videoClass) {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();
        Meta meta = videoClass.getMeta();
        if(meta.getStatus() == 200) {
            datumArrayList.addAll(videoClass.getData());
            videoListAdapter.notifyDataSetChanged();
            Log.d("video List", "video List = " + datumArrayList.size());
        }else{
            displayError(meta.getMsg());
        }
    }

    @Override
    public void displayError(String s) {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();
        Common.ShowToast(getActivity(),s);
    }

    @Override
    public void playVideo(int position) {

        Datum datum = datumArrayList.get(position);
        Original original = datum.getImages().getOriginal();

        getRouter().pushController(RouterTransaction
                .with(new VideoViewController(original))
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }

    @Override
    public void videoUpVote(int position) {

        Datum datum = datumArrayList.get(position);

        int upVoteVideo = Integer.parseInt(datum.getUpVote()) + 1;
        datum.setUpVote(String.valueOf(upVoteVideo));
        videoListAdapter.notifyItemChanged(position);
    }

    @Override
    public void videoDownVote(int position) {
        Datum datum = datumArrayList.get(position);
        int downVoteVideo = Integer.parseInt(datum.getDownVote()) + 1;
        datum.setDownVote(String.valueOf(downVoteVideo));
        videoListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
