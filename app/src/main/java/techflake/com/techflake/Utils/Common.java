package techflake.com.techflake.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class Common {

    public static String API_KEY = "YPjf3OyTs6eJZF9AKoTe3Q09S7bur81n";
    public static String RECORD_LIMIT = "10";

    public static void ShowToast(Activity activity, String Msg) {
        Toast.makeText(activity, Msg, Toast.LENGTH_LONG).show();
    }

    public static int getDeviceWidth(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static boolean isNetworkAvailable(Activity activity) {

        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting())
            result = true;
        else
            result = false;
        return result;
    }
}
