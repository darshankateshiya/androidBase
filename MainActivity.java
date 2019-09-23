package com.darshankateshiya.interviewdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.darshankateshiya.interviewdemo.Adapter.CategoryAdapter;
import com.darshankateshiya.interviewdemo.Adapter.VideoAdpater;
import com.darshankateshiya.interviewdemo.api.ApplicationClient;
import com.darshankateshiya.interviewdemo.model.VIdeoListPojo.Category;
import com.darshankateshiya.interviewdemo.model.VIdeoListPojo.Data;
import com.darshankateshiya.interviewdemo.model.VIdeoListPojo.Video;
import com.darshankateshiya.interviewdemo.model.VIdeoListPojo.VideoListPOJO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcyCategories;
    private RecyclerView rcyVideo;


    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }


    private void findViews() {
        rcyCategories = (RecyclerView)findViewById( R.id.rcyCategories );
        rcyVideo = (RecyclerView)findViewById( R.id.rcyVideo );

        apiCall();
    }


    private void apiCall() {

        Call<VideoListPOJO> call = ApplicationClient.getInstance().getApi().getVideosPagination("11", "", "random");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.show();

        call.enqueue(new Callback<VideoListPOJO>() {
            @Override
            public void onResponse(Call<VideoListPOJO> call, Response<VideoListPOJO> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    VideoListPOJO pojo = new VideoListPOJO();
                    pojo = response.body();
//                    Toast.makeText(MainActivity.this, String.valueOf(pojo.getStatus()),Toast.LENGTH_LONG).show();

                    List<Category> categories = pojo.getData().getCategories();
                    List<Video> videos = pojo.getData().getVideos();
                    setRcyView(categories,videos);
                }
            }

            @Override
            public void onFailure(Call<VideoListPOJO> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

    }

    private void setRcyView(List<Category> category, List<Video> videos) {

        CategoryAdapter adapter =  new CategoryAdapter(category, this);
        rcyCategories.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rcyCategories.setAdapter(adapter);

        VideoAdpater videoAdapter =  new VideoAdpater(videos, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rcyVideo.setLayoutManager(gridLayoutManager);
        rcyVideo.setAdapter(videoAdapter);
    }

}
