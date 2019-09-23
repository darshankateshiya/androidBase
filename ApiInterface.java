package com.dc_app.dcapp_driver.Api;

import com.dc_app.dcapp_driver.Driver.APIForDriver.DriverProfileAPIJSON.DriverProfileJSON;
import com.dc_app.dcapp_driver.Driver.APIForDriver.GETCustomerJobApi.GetDriverJobsAPI;
import com.dc_app.dcapp_driver.HomePage.Home.Driver.driverAdapter.DriverGETBidJobAPI.DriverGetJobsBidAPI;
import com.dc_app.dcapp_driver.HomePage.Record.Driver.BidDriverListJSON.BidDriverListJSON;
import com.dc_app.dcapp_driver.model.Constitutions.Constitutions;
import com.dc_app.dcapp_driver.Driver.Login.LoginGson;
import com.dc_app.dcapp_driver.model.VehicleData.VehicleData;
import com.dc_app.dcapp_driver.model.VehicleInfo.VehicleInfo;
import com.dc_app.dcapp_driver.model.city.Cities;
import com.dc_app.dcapp_driver.model.state.States;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Multipart                                                              //1
    @POST("driver/registration")
    Call<ResponseBody> setProfile(@Part("firstname") RequestBody firstname,
                                  @Part("lastname") RequestBody lastname,
                                  @Part("dob") RequestBody dob,
                                  @Part("state") RequestBody state,
                                  @Part("city") RequestBody city,
                                  @Part("gst_number") RequestBody gst_number,
                                  @Part("mobile_number") RequestBody mobile_number,
                                  @Part("password") RequestBody password,
                                  @Part("adhaar_number") RequestBody adhaar_number,
                                  @Part MultipartBody.Part profile_picture);

    @Multipart                                                              //2
    @POST("customer/registration")
    Call<ResponseBody> customerSignUp(@Part("firstname") RequestBody firstname,
                                      @Part("lastname") RequestBody lastname,
                                      @Part("dob") RequestBody dob,
                                      @Part("state") RequestBody state,
                                      @Part("city") RequestBody city,
                                      @Part("gst_number") RequestBody gst_number,
                                      @Part("mobile_number") RequestBody mobile_number,
                                      @Part("password") RequestBody password,
                                      @Part("adhaar_number") RequestBody adhaar_number,
                                      @Part MultipartBody.Part profile_picture,
                                      @Part("company_name") RequestBody company_name,
                                      @Part("address") RequestBody address,
                                      @Part("occupation") RequestBody occupation);

    @FormUrlEncoded                                                         //3
    @POST("driver/login")
    Call<LoginGson> driverLogin(@Field("mobile_number") String mobile_number,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("driver/detail_reg")
    Call<ResponseBody> setVehicleDetails(@Field("driverid") String driverid,
                                         @Field("vehicle_company") String vehicle_company,
                                         @Field("vehicle_model") String vehicle_model,
                                         @Field("vehicle_weight") String vehicle_weight,
                                         @Field("weight_id") String weight_id,
                                         @Field("truck_type_id") String truck_type_id,
                                         @Field("truck_length_id") String truck_length_id);

    @GET("getstates")
        //5
    Call<States> getStates();

    @GET("getcities/{id}")
        //6
    Call<Cities> getCities(@Path("id") String id);

    @GET("driver/vehicle_info")
        //7
    Call<VehicleInfo> getVehicleInfo();



    @GET("customer/constitutions")
        //9
    Call<Constitutions> getConstitutions();

    @Multipart                                                              //10
    @POST("customer/company_detail")
    Call<ResponseBody> setCompanyDetail(@Part("customerid") RequestBody customerid,
                                        @Part("company_constitution") RequestBody company_constitution,
                                        @Part("company_pan_no") RequestBody company_pan_no,
                                        @Part("company_doi") RequestBody company_doi,
                                        @Part MultipartBody.Part company_profile_picture,
                                        @Part("company_occupation") RequestBody company_occupation);


    @Multipart                                                           //15
    @POST("driver/driver_vehicle_images")
    Call<ResponseBody> setIndividualVehicleImage(@Part("driverid") RequestBody driverid,
                                                 @Part MultipartBody.Part[] vehicle_picture);



    @FormUrlEncoded                                                         //11
    @POST("customer/authorised_detail")
    Call<ResponseBody> setAuthorisedDetail(@Field("customerid") String customerid,
                                           @Field("auth_firstname") String auth_firstname,
                                           @Field("auth_lastname") String auth_lastname,
                                           @Field("auth_dob") String auth_dob,
                                           @Field("auth_mobile") String auth_mobile);

    @FormUrlEncoded                                                         //12
    @POST("driver/frequent_route")
    Call<ResponseBody> setDriverFrequentRoute(@Field("from_city") String from_city,
                                              @Field("to_city") String to_city,
                                              @Field("driverid") String driverid);

//    @FormUrlEncoded                                                         //13
//    @POST("customer/job_post")
//    Call<ResponseBody> setCustomerJobPost(@Field("origin") String origin,
//                                          @Field("destination") String destination,
//                                          @Field("customerid") String customerid,
//                                          @Field("weight") String weight,
//                                          @Field("pickup_date") String pickup_date,
//                                          @Field("pickup_time") String pickup_time,
//                                          @Field("eta_date") String eta_date,
//                                          @Field("eta_time") String eta_time,
//                                          @Field("priority") String priority,
//                                          @Field("vehicle_model") String vehicle_model,
//                                          @Field("req_date") String req_date,
//                                          @Field("status") String status,
//                                          @Field("description") String description,
//                                          @Field("amount") String amount,
//                                          @Field("weight_unit") String weight_unit);
//
//

    @FormUrlEncoded                                                         //13
    @POST("customer/job_post")
    Call<ResponseBody> setCustomerJobPost(@Field("customerid") String customerid,
                                          @Field("origin") String origin,
                                          @Field("destination") String destination,
                                          @Field("weight") String weight,
                                          @Field("weight_unit") String weight_unit,
                                          @Field("pickup_date") String pickup_date,
                                          @Field("pickup_time") String pickup_time,
                                          @Field("eta_date") String eta_date,
                                          @Field("eta_time") String eta_time,
                                          @Field("priority") String priority,
                                          @Field("vehicle_model") String vehicle_model,
                                          @Field("req_date") String req_date,
                                          @Field("status") String status,
                                          @Field("description") String description,
                                          @Field("amount") String amount,
                                          @Field("lat") String lat,
                                          @Field("long") String longt,
                                          @Field("dlat") String dlat,
                                          @Field("dlong") String dlong,
                                          @Field("pickup_address") String pickup_address,
                                          @Field("drop_address") String drop_address,
                                          @Field("weight_id") String weight_id,
                                          @Field("truck_type_id") String truck_type_id,
                                          @Field("truck_length_id") String truck_length_id
    );

    @FormUrlEncoded                                                         //14
    @POST("driver/get_customer_jobs")
    Call<DriverGetJobsBidAPI> getCustomerJobs(@Field("driver_id") String driverid);


    @FormUrlEncoded                                                         //16
    @POST("customer/mobileno_validate")
    Call<ResponseBody> checkMobileNoValidate(@Field("mobile_number") String mobile_number);

    @FormUrlEncoded                                                         //17
    @POST("driver/add_job_bid")
    Call<ResponseBody> addDriverJobBid(@Field("driver_id") String driver_id,
                                       @Field("job_id") String job_id,
                                       @Field("amount") String amount,
                                       @Field("pickup_time") String pickup_time,
                                       @Field("estimate_time") String estimate_time,
                                       @Field("comments") String comments);


    @FormUrlEncoded                                                         //19
    @POST("driver/remove_job_bid")
    Call<ResponseBody> removeDriverJob(@Field("bid_id") String bid_id);

    @FormUrlEncoded                                                         //20
    @POST("driver/get_customer_job_bid")
    Call<GetDriverJobsAPI> getCustomerJobBid(@Field("driver_id") String driver_id);

    @FormUrlEncoded
    @POST("customer/update_customer_job_post")
        //22
    Call<ResponseBody> updateCustomerJob(@Field("customer_job_id") String customer_job_id,
                                         @Field("customerid") String customerid,
                                         @Field("origin") String origin,
                                         @Field("destination") String destination,
                                         @Field("weight") String weight,
                                         @Field("weight_unit") String weight_unit,
                                         @Field("pickup_date") String pickup_date,
                                         @Field("pickup_time") String pickup_time,
                                         @Field("eta_date") String eta_date,
                                         @Field("eta_time") String eta_time,
                                         @Field("priority") String priority,
                                         @Field("vehicle_model") String vehicle_model,
                                         @Field("req_date") String req_date,
                                         @Field("status") String status,
                                         @Field("description") String description,
                                         @Field("amount") String amount,
                                         @Field("lat") String lat,
                                         @Field("long") String longs,
                                         @Field("dlat") String dlat,
                                         @Field("dlong") String dlong,
                                         @Field("pickup_address") String pickup_address,
                                         @Field("drop_address") String drop_address,
                                         @Field("weight_id") String weight_id,
                                         @Field("truck_type_id") String truck_type_id,
                                         @Field("truck_length_id") String truck_length_id);


    @GET("driver/profile/{id}")
        //23
    Call<DriverProfileJSON> getDriverProfile(@Path("id") String id);



    @FormUrlEncoded
    @POST("driver/get_bid_jobs")
        //25
    Call<BidDriverListJSON> getDriverBidList(@Field("driver_id") String driver_id);


    @FormUrlEncoded
    @POST("driver/update_job_bid")
        //26
    Call<ResponseBody> setDriverBidUpdate(@Field("driver_id") String driver_id,
                                          @Field("job_id") String job_id,
                                          @Field("amount") String amount,
                                          @Field("pickup_time") String pickup_time,
                                          @Field("estimate_time") String estimate_time,
                                          @Field("comments") String comments,
                                          @Field("id") String id);

    @FormUrlEncoded
    @POST("driver/remove_job_bid")
    Call<ResponseBody> removeDriverBid(@Field("bid_id") String bid_id);

    @GET("send_otp")
    Call<ResponseBody> sendOTP(@Query("mobile_number") String mobile_number,
                               @Query("user_type") String user_type);

    @GET("verify_otp")
    Call<ResponseBody> verifyOTP(@Query("mobile_number") String mobile_number,
                                 @Query("user_type") String user_type,
                                 @Query("otp") String otp);

    @GET("new_password")
    Call<ResponseBody> newPassword(@Query("id") String id,
                                   @Query("user_type") String user_type,
                                   @Query("password") String password);


    @FormUrlEncoded
    @POST("customer/customer_award_job")
    Call<ResponseBody> customerAwardJob(@Field("bid_id") String bid_id,
                                        @Field("job_post_id") String job_post_id);

    @GET("driver/all_weight_truck")
    Call<VehicleData> getVehicleData();

    @FormUrlEncoded
    @POST("customer/delete_customer_job_post")
    Call<ResponseBody> deleteCustomerJob(@Field("id") String id);
}
