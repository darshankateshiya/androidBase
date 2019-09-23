package prowebtech.com.discounter.loginSignUpForgotPassword.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import prowebtech.com.discounter.BottomNavigationActivity;
import prowebtech.com.discounter.R;
import prowebtech.com.discounter.api.ApplicationClient;
import prowebtech.com.discounter.loginSignUpForgotPassword.forgotpassword.ForgotPinActivity;
import prowebtech.com.discounter.loginSignUpForgotPassword.signUp.SignUpActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp,btnLogin,btnGoogleSignIn;
    TextView txtForgotPin;
    AppCompatEditText edtEmail,edtPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        InterNetConnction connction = new InterNetConnction(this);
//        connction.interNet();

        interNet();
        findViews();
        btnSignUp.setOnClickListener(this);
        txtForgotPin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    public void interNet(){
        if (isOnline()) {
            //do whatever you want to do
        } else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        finishAffinity();
                    }
                });

                alertDialog.show();
            } catch (Exception e) {
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
//            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v== btnSignUp)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                }
            },500);
        }
        if (v== txtForgotPin)
        {
            startActivity(new Intent(this, ForgotPinActivity.class));
        }
        if (v==btnLogin){

            if (Objects.requireNonNull(edtEmail.getText()).toString().isEmpty()){
                edtEmail.setError("Please Enter Email Address");
            }
            else if (!(edtEmail.getText().toString()).matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                edtEmail.setError("Please Enter Valid Email Address");
            }
            else if (edtPin.getText().toString().isEmpty()){
                edtEmail.setError(null);
                edtEmail.clearFocus();
                edtPin.setError("Please Enter pin");
            }
            else {
                edtEmail.setError(null);
                edtEmail.clearFocus();
                edtPin.setError(null);
                edtPin.clearFocus();
                login();
            }
        }
    }

    public void findViews()
    {
        btnSignUp = findViewById(R.id.btnSignUp);
        txtForgotPin = findViewById(R.id.txtForgotPin);
        edtEmail=findViewById(R.id.edtEmail);
        edtPin=findViewById(R.id.edtPin);
        btnLogin=findViewById(R.id.btnLogin);
    }

    private void login(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        btnLogin.setEnabled(false);
        Call<SignIn> call= ApplicationClient.getInstance().getApi().loginUser(Objects.requireNonNull(edtEmail.getText()).toString(), Objects.requireNonNull(edtPin.getText()).toString());
        call.enqueue(new Callback<SignIn>() {
            @Override
            public void onResponse(@NonNull Call<SignIn> call, @NonNull Response<SignIn> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    SignIn result=response.body();
                    assert result != null;
                    String token=result.getToken();
                    UserSignIn user=result.getUser();

                    SharedPreferences preferences=getSharedPreferences("Login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putInt("status",1);
                    editor.apply();

                    SharedPreferences preferences1=getSharedPreferences("User_Details",MODE_PRIVATE);
                    SharedPreferences.Editor editor1=preferences1.edit();
                    editor1.putString("name",user.getName());
                    editor1.putString("DOB",user.getDateOfBirth());
                    editor1.putString("mobile_number",user.getMobileNumber());
                    editor1.putString("email",user.getEmail());
                    editor1.putString("address",user.getAddress());
                    editor1.putInt("country_id",user.getCountryId());
                    editor1.apply();

                    SharedPreferences sp = getSharedPreferences("token",MODE_PRIVATE);
                    sp.edit().putString("auth_token", token).apply();
                    ApplicationClient.reSetClient(LoginActivity.this);

                    Intent intent=new Intent(LoginActivity.this, BottomNavigationActivity.class);
                    startActivity(intent);
                    finish();
                    finishAffinity();
                    Toast.makeText(LoginActivity.this, "Login Successfully" , Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    btnLogin.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<SignIn> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Poor Internet Connection. \nPlease Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
