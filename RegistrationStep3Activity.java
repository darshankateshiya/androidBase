package com.dc_app.dcapp_driver.Driver.Sign_Up;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dc_app.dcapp_driver.Api.ApplicationClient;
import com.dc_app.dcapp_driver.R;
import com.dc_app.dcapp_driver.model.city.Cities;
import com.dc_app.dcapp_driver.model.state.States;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import jrizani.jrspinner.JRSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationStep3Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private JRSpinner JPSelectState;
    private JRSpinner JPSelectCity;
    private EditText evLoginMobile;
    private EditText evBirthDate;
    private ImageView imgNext;

    States states;
    ArrayList<HashMap<String,String>> stateList;
    String[] stateName;
    String stateId;


    Cities cities;
    ArrayList<HashMap<String,String>> cityList;
    String[] cityName;
    String cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step3);

        findViews();
    }


    private void findViews() {
        imgBack = (ImageView)findViewById( R.id.imgBack );
        JPSelectState = (JRSpinner)findViewById( R.id.JPSelectState );
        JPSelectCity = (JRSpinner)findViewById( R.id.JPSelectCity );
        evLoginMobile = (EditText)findViewById( R.id.ev_login_mobile );
        evBirthDate = (EditText)findViewById( R.id.evBirthDate );
        imgNext = (ImageView)findViewById( R.id.imgNext );

        imgNext.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        evBirthDate.setOnClickListener(this);


        setStates();
    }

    private void setStates() {


        Call<States> call = ApplicationClient.getInstance().getApi().getStates();
        call.enqueue(new Callback<States>() {
            @Override
            public void onResponse(Call<States> call, Response<States> response) {
                if (response.isSuccessful()) {
                    states = response.body();
                    stateList = new ArrayList<>();
                    stateName = new String[states.getData().size()];
                    for (int i = 0; i < states.getData().size(); i++) {
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("name",states.getData().get(i).getName());
                        hashMap.put("id",String.valueOf(states.getData().get(i).getId()));
                        stateList.add(hashMap);

                        stateName[i] = states.getData().get(i).getName();
                    }
                    JPSelectCity.clear();
                    JPSelectState.setItems(stateName);
                    JPSelectState.setTitle("Select state");
                    JPSelectState.setExpandTint(R.color.grey_cool_grey);
                }
            }

            @Override
            public void onFailure(Call<States> call, Throwable t) {
                t.printStackTrace();
            }
        });

        JPSelectState.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                stateId = stateList.get(position).get("id");
                cityId = "";
                JPSelectCity.clear();
                setCity(stateId);

                Toast.makeText(RegistrationStep3Activity.this,stateId , Toast.LENGTH_LONG ).show();
            }
        });
    }

    private void setCity(String id) {

        Call<Cities> call = ApplicationClient.getInstance().getApi().getCities(id);
        call.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.isSuccessful()) {
                    cities = response.body();
                    cityList = new ArrayList<>();

                    cityName = new String[cities.getData().size()];
                    for (int i = 0; i < cities.getData().size(); i++) {
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("name",cities.getData().get(i).getCityName());
                        hashMap.put("id",String.valueOf(cities.getData().get(i).getId()));
                        cityList.add(hashMap);

                        cityName[i] = cities.getData().get(i).getCityName();
                    }
                    JPSelectCity.setItems(cityName);
                    JPSelectCity.setTitle("Select cite");
                    JPSelectCity.setExpandTint(R.color.grey_cool_grey);
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                t.printStackTrace();
            }
        });

        JPSelectCity.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                cityId = cityList.get(position).get("id");
                Toast.makeText(RegistrationStep3Activity.this,cityId , Toast.LENGTH_LONG ).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(imgBack == v){
            onBackPressed();
        }else if(imgNext == v){

            if(checkValid()){
                setSharedPreferences();

                Intent intent = new Intent(this,RegistrationStep4Activity.class);
                startActivity(intent);
            }

        } else if (evBirthDate == v) {
            selectDate();
        }
    }

    private void setSharedPreferences() {

        SharedPreferences sp = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("city", cityId);
        editor.putString("dob", evBirthDate.getText().toString());
        editor.putString("state", stateId);
        editor.putString("mobile", evLoginMobile.getText().toString());
        editor.apply();

    }

    private void selectDate() {

        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final String[] data = new String[1];


        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                if (month + 1 < 10) {
                    data[0] = String.valueOf(dayOfMonth) + "/0" + String.valueOf(month + 1)
                            + "/" + String.valueOf(year);

                } else {
                    data[0] = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1)
                            + "/" + String.valueOf(year);
                }
                evBirthDate.setText(data[0]);
            }
        }, yy, mm, dd);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }


    private boolean checkValid() {

        if (stateId.isEmpty()) {
            Toast.makeText(this, "Please enter a state", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (cityId.isEmpty()) {
            Toast.makeText(this, "Please enter a city", Toast.LENGTH_SHORT).show();;
            return false;
        }
        if (evLoginMobile.getText().toString().isEmpty()) {
            evLoginMobile.setError("Please enter a mobile no.");
            return false;
        }
        if (evLoginMobile.getText().toString().matches("^/d{10}/")) {
            evLoginMobile.setError("Please enter a valid number");
            return false;
        }

        if (evBirthDate.getText().toString().matches(getResources().getString(R.string.select_birth_date))) {
            evBirthDate.setError("Please select birth date");
            return false;
        }
        return true;
    }
}
