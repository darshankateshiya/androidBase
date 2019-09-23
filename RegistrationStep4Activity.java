package com.dc_app.dcapp_driver.Driver.Sign_Up;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dc_app.dcapp_driver.Api.ApplicationClient;
import com.dc_app.dcapp_driver.DataBase.DBHelper;
import com.dc_app.dcapp_driver.Driver.APIForDriver.DriverProfileAPIJSON.DriverProfileJSON;
import com.dc_app.dcapp_driver.Driver.Sign_Up.Profile_Picture.CircleTransform;
import com.dc_app.dcapp_driver.Driver.Vehicle.VehicleDetailsActivity;
import com.dc_app.dcapp_driver.R;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationStep4Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private ImageView imgProfileUpload;
    private ImageView imgNext;


    ArrayList<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step4);

        findViews();
    }

    private void findViews() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgProfileUpload = (ImageView) findViewById(R.id.imgProfileUpload);
        imgNext = (ImageView) findViewById(R.id.imgNext);

        imgBack.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgProfileUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgBack) {
            onBackPressed();
        } else if (v == imgNext) {
            if (verify()) {
                apiCall();
            }
        } else if (v == imgProfileUpload) {
            openGallery();
        }
    }

    private void openGallery() {

        ImagePicker.with(this)
                .setStatusBarColor("#545454")
                .setToolbarColor("#ffffff")
                .setCameraOnly(false)
                .setSelectedImages(images)
                .setMultipleMode(false)
                .setFolderTitle("Select Profile Picture")
                .setDoneTitle("Done")
                .setSavePath("DC_Profile")
                .setProgressBarColor("#0067fc")
                .setBackgroundColor("#e9ecf2")
                .setToolbarIconColor("#FFFFFF")
                .setToolbarIconColor("#9298a4")
                .setToolbarTextColor("#9298a4")
                .start();
    }

    private void apiCall() {

        Intent intent = new Intent(RegistrationStep4Activity.this, VehicleDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        SharedPreferences sp = getSharedPreferences("Profile", Context.MODE_PRIVATE);

        RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), sp.getString("first_name", "1"));
        RequestBody last_name = RequestBody.create(MediaType.parse("text/plain"), sp.getString("last_name", "2"));
        RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), "17/09/1997");
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), sp.getString("state", "3"));
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), sp.getString("city", "4"));
        RequestBody gst_number = RequestBody.create(MediaType.parse("text/plain"), sp.getString("gst_no", "4"));
        RequestBody mobile_number = RequestBody.create(MediaType.parse("text/plain"), sp.getString("mobile", "1"));
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), sp.getString("password", "44"));
        RequestBody aadhar = RequestBody.create(MediaType.parse("text/plain"), sp.getString("aadhar", "44"));
        RequestBody diagnosticReport = RequestBody.create(MediaType.parse("image/*"), new File(images.get(0).getPath()));
        MultipartBody.Part profile = MultipartBody.Part.createFormData("profile_picture", images.get(0).getName(), diagnosticReport);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = ApplicationClient.getInstance().getApi().setProfile(first_name,
                last_name,
                dob,
                state,
                city,
                gst_number,
                mobile_number,
                password,
                aadhar,
                profile);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String temp = response.body().string();
                        JSONObject jsonObject = new JSONObject(temp);

                        if (jsonObject.getBoolean("status")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            SharedPreferences sp = getSharedPreferences("DriverProfile", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("id", data.getString("id"));
                            editor.apply();
                            Toast.makeText(RegistrationStep4Activity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            driverProfileUpdate();
                        } else {
                            Toast.makeText(RegistrationStep4Activity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationStep4Activity.this, "Check Internet Cnnection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void driverProfileUpdate(){

        SharedPreferences sharedPreferences = getSharedPreferences("DriverProfile", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Call<DriverProfileJSON> call = ApplicationClient.getInstance().getApi().getDriverProfile(sharedPreferences.getString("id",""));
        call.enqueue(new Callback<DriverProfileJSON>() {
            @Override
            public void onResponse(Call<DriverProfileJSON> call, Response<DriverProfileJSON> response) {

                if(response.isSuccessful()){
                    DriverProfileJSON json=new DriverProfileJSON();
                    json=response.body();
                    if(json.getStatus()){


                        DBHelper helper = new DBHelper(RegistrationStep4Activity.this);
                        editor.putString("name", json.getData().getFirstname());
                        editor.apply();


                        ContentValues cv = new ContentValues();
                        cv.put("id",json.getData().getId());
                        cv.put("firstname",json.getData().getFirstname());
                        cv.put("lastname",json.getData().getLastname());
                        cv.put("state",json.getData().getState());
                        cv.put("city",json.getData().getCity());
                        cv.put("gst_number",json.getData().getGstNumber());
                        cv.put("mobile_number",json.getData().getMobileNumber());
                        cv.put("password",json.getData().getPassword());
                        cv.put("rpassword",json.getData().getRpassword());
                        cv.put("adhaar_number",json.getData().getAdhaarNumber());
                        cv.put("otp",json.getData().getOtp());
                        cv.put("otp_status",json.getData().getOtpStatus());
                        cv.put("profile_picture",json.getData().getProfilePicture());
                        cv.put("vehicle_compan", json.getData().getVehicleCompany());
                        cv.put("vehicle_model",json.getData().getVehicleModel());
                        cv.put("vehicle_weight",json.getData().getVehicleWeight());
                        cv.put("vehicle_picture1",json.getData().getVehiclePicture1());
                        cv.put("vehicle_picture2",json.getData().getVehiclePicture2());
                        cv.put("vehicle_picture3",json.getData().getVehiclePicture3());
                        cv.put("vehicle_picture4",json.getData().getVehiclePicture4());
                        cv.put("vehicle_picture5",json.getData().getVehiclePicture5());
                        cv.put("from_city",json.getData().getFromCity());
                        cv.put("to_ciity",json.getData().getToCity());
                        cv.put("lat",json.getData().getLat());
                        cv.put("long",json.getData().getLong());
                        helper.deleteTable("DriverProfile");
                        helper.insertItem(cv,"DriverProfile");

                        Intent intent = new Intent(RegistrationStep4Activity.this, VehicleDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<DriverProfileJSON> call, Throwable t) {

            }
        });
    }


    private boolean verify() {

        if (images == null || images.isEmpty()) {
            Toast.makeText(this, "Upload Profile Photo", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            Picasso.get().load(new File(images.get(0).getPath())).transform(new CircleTransform()).into(imgProfileUpload);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
