package prowebtech.com.discounter.api;


import okhttp3.ResponseBody;
import prowebtech.com.discounter.Favorite.pojo.FavCoupons;
import prowebtech.com.discounter.Home.Popular.pojoPopularOffers.PopularOffer;
import prowebtech.com.discounter.Home.Popular.pojoPopularVendor.ExploreByVendor;
import prowebtech.com.discounter.Home.TopDeals.pojoTopDeals.TopDeals;
import prowebtech.com.discounter.Home.category.particular.nearMeCat.Pojo.NearMeCoupon;
import prowebtech.com.discounter.Home.category.particular.pojoGeneralCoupons.Coupon;
import prowebtech.com.discounter.Home.category.pojoCategory.Category;
import prowebtech.com.discounter.Settings.legalPojo.Legal;
import prowebtech.com.discounter.Settings.preferences.Pojo.PreferencesAndActivities;
import prowebtech.com.discounter.loginSignUpForgotPassword.forgotpassword.ForgotPassword;
import prowebtech.com.discounter.loginSignUpForgotPassword.login.SignIn;
import prowebtech.com.discounter.loginSignUpForgotPassword.signUp.Country;
import prowebtech.com.discounter.loginSignUpForgotPassword.signUp.SignUp;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("countries")
    Call<Country> getCountry();

    @POST("user/signin")
    @FormUrlEncoded
    Call<SignIn> loginUser(@Field("email") String email, @Field("pin") String pin);

    @POST("user/signup")
    @FormUrlEncoded
    Call<SignUp> registerUser(@Field("name") String name, @Field("email") String email, @Field("pin") String pin, @Field("address") String address, @Field("mobile_number") String contact, @Field("date_of_birth") String DOB, @Field("country_id") String country_id);

    @GET("preferences-activities")
    Call<PreferencesAndActivities> getPreferencesActivities();

    @POST("user-preferences")
    @FormUrlEncoded
    Call<ResponseBody> sendActivityPreferences(@Field("activity_ids[]") int[] activityIds, @Field("preference_ids[]") int[] preferenceIds);

    @GET("coupons")
    Call<PopularOffer> getPopularOffers(@Query("offset") int offSet);

    @GET("coupons/categories")
    Call<Category> getCategoryCoupons();

    @POST("user/forgotPassword")
    @FormUrlEncoded
    Call<ForgotPassword> sendEmail(@Field("email") String email);

    @GET("coupons/vendors")
    Call<ExploreByVendor> getVendor();

    @GET("coupons/topCoupon")
    Call<TopDeals> getTopOffers(@Query("offset") int offSet);

    // popular coupons in category api
    @GET("categories/coupons/popular")
    Call<Coupon> getPopularCoupons(@Query("activity_id") int id, @Query("offset") int offset);

    @GET("categories/coupons/new")
    Call<Coupon> getNewCoupons(@Query("activity_id") int id, @Query("offset") int offset);

    @GET("categories/coupons/nearMe")
    Call<NearMeCoupon> getNearMeCouponsWise(@Query("activity_id") String id,
                                            @Query("offset") String offset,
                                            @Query("latitude") String latitude,
                                            @Query("longitude") String longitude);


    @POST("user/redeemCoupon")
    @FormUrlEncoded
    Call<ResponseBody> redmeenCoupon(@Field("coupon_id") String coupon_id,
                                     @Field("vendorPin") String vendorPin,
                                     @Field("customerPin") String customerPin);

    @GET("masters")
    Call<Legal> getLegalDetails();

    @POST("user/feedback")
    @FormUrlEncoded
    Call<ResponseBody> postFeedback(@Field("feedback_type") int feedback_type,
                                    @Field("feedback_description") String feedback_description);

    @POST("user/report-offer")
    @FormUrlEncoded
    Call<ResponseBody> postReport(@Field("outlet_name") String outlet_name,
                                  @Field("contact_person") String contact_person,@Field("offer_name") String offer_name,
                                  @Field("location") String location, @Field("issue") String issue);

    @POST("user/change-pin")
    @FormUrlEncoded
    Call<ResponseBody> changePin(@Field("old_pin") int old_pin,@Field("new_pin") int new_pin,
                                 @Field("new_pin_confirmation") int new_pin_confirmation);

    @GET("user-points")
    Call<ResponseBody> getPoints();

    @GET("user-referral-code")
    Call<ResponseBody> getReferralCode();

    @POST("coupons/fav")
    @FormUrlEncoded
    Call<ResponseBody> changeFavoriteCall(@Field("coupon_id") int coupon_id);

    @GET("coupons/fav")
    Call<FavCoupons> getFavoriteCoupons();
}

