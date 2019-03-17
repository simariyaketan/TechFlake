package techflake.com.techflake.network;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import techflake.com.techflake.models.video.VideoClass;

public interface RetrofitInterface {

    @GET("gifs/trending")
    Observable<VideoClass> getVideoList(@Query("api_key") String api_key,
                                        @Query("limit") String limit,
                                        @Query("rating") String rating);


}
