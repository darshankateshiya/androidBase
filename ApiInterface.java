package com.darshankateshiya.interviewdemo.api;

import com.darshankateshiya.interviewdemo.model.VIdeoListPojo.VideoListPOJO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("getVideosPagination")
    Call<VideoListPOJO> getVideosPagination(@Field("cat_id") String code,
                                  @Field("video_loaded_ids") String video_loaded_ids,
                                  @Field("sort_by") String sort_by);



}
