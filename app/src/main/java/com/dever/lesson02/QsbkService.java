package com.dever.lesson02;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by admin on 2015/12/29.
 */
public interface QsbkService {
    @GET("article/list/{type}")
    //@Query("page")int page GET请求     @Field("page")int page
    Call<List<Item>> getList(@Path("type")String type,@Query("page")int page);
}
