package techflake.com.techflake.network;

import android.app.ProgressDialog;

import retrofit2.Retrofit;

public class Constant {
    private static Retrofit retrofitClient = RetrofitClass.getClient();
    public static RetrofitInterface retrofitInterface = retrofitClient.create(RetrofitInterface.class);
    public static ProgressDialog progressDialog;
}
