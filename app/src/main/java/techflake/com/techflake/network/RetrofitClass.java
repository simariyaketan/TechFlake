package techflake.com.techflake.network;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClass {
    public static final String BASE_URL = "https://api.giphy.com/v1/";

    private static Retrofit adapter = null;

    public static OkHttpClient okClient = new OkHttpClient.Builder()
            .authenticator(new Authenticator()
            {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic("admin", "1234");
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .header("Connection", "close")
                            .build();
                }
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

    public static Retrofit getClient()
    {
        if (adapter == null)
        {
            Log.d("BASE_URL","BASE_URL = "+BASE_URL);
            adapter = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okClient)
                    .build();
        }
        return adapter;
    }
}
