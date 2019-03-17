package techflake.com.techflake.presenter;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import techflake.com.techflake.Interface.VideoListPresenterInterface;
import techflake.com.techflake.Interface.VideoViewInterface;
import techflake.com.techflake.Utils.Common;
import techflake.com.techflake.models.video.Meta;
import techflake.com.techflake.models.video.VideoClass;
import techflake.com.techflake.network.RetrofitClass;
import techflake.com.techflake.network.RetrofitInterface;

public class VideoPresenter implements VideoListPresenterInterface {

    VideoViewInterface videoViewInterface;
    private String TAG = "VideoList";

    public VideoPresenter(VideoViewInterface videoViewInterface){
        this.videoViewInterface = videoViewInterface;
    }
    @Override
    public void getVideoList() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<VideoClass> getObservable(){
        return RetrofitClass.getClient().create(RetrofitInterface.class)
                .getVideoList(Common.API_KEY,Common.RECORD_LIMIT,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<VideoClass> getObserver(){
        return new DisposableObserver<VideoClass>(){


            @Override
            public void onNext(VideoClass videoClass) {
                videoViewInterface.displayClientOrder(videoClass);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                videoViewInterface.displayError(e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }
}
