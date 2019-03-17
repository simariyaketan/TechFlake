package techflake.com.techflake.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import java.util.ArrayList;

import techflake.com.techflake.R;
import techflake.com.techflake.Utils.ObjectBox;
import techflake.com.techflake.adapter.VideoListAdapter;
import techflake.com.techflake.controller.HomeController;
import techflake.com.techflake.databinding.ActivityMainBinding;
import techflake.com.techflake.models.video.Datum;
import techflake.com.techflake.presenter.VideoPresenter;

public class MainActivity extends AppCompatActivity {

    VideoListAdapter videoListAdapter;
    ArrayList<Datum> datumArrayList;
    ActivityMainBinding activityMainBinding;
    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ObjectBox.init(this);

        router = Conductor.attachRouter(this, activityMainBinding.controllerContainer, savedInstanceState);

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new HomeController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
