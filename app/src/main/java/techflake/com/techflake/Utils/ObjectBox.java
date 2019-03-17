package techflake.com.techflake.Utils;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.android.BuildConfig;
import techflake.com.techflake.models.video.MyObjectBox;


public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        if(boxStore == null) {
            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();

            if (BuildConfig.DEBUG) {
                new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
            }
        }
    }

    public static BoxStore get() {
        return boxStore;
    }
}
