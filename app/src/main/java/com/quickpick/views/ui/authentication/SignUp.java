package com.quickpick.views.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quickpick.R;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Common_methods;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 13-03-2018.
 */

public class SignUp extends AppCompatActivity {

    @Bind(R.id.btn_signup)
    Button btn_signup;

    @Bind(R.id.et_email)
    EditText et_email;
    @Bind(R.id.txt_verify_email)
    TextView txt_verify_email;
    @Bind(R.id.txt_email_error)
    TextView txt_email_error;

    @Bind(R.id.et_mobile)
    EditText et_mobile;
    @Bind(R.id.txt_verify_mobile)
    TextView txt_verify_mobile;
    @Bind(R.id.txt_mobile_error)
    TextView txt_mobile_error;

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.txt_user_error)
    TextView txt_user_error;

    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.txt_error_pwd)
    TextView txt_error_pwd;

    @Bind(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;
    @Bind(R.id.txt_error_confirm_pwd)
    TextView txt_error_confirm_pwd;

    @Bind(R.id.et_address)
    EditText et_address;
    @Bind(R.id.et_landmark)
    EditText et_landmark;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()) {
                    fetchData();

                }
            }
        });

    }


    private boolean checkValidation(){
        boolean validation = true;

        if(Common_methods.validateEmail(et_email)){
            et_email.setError("please enter valid email");
            et_email.requestFocus();
            validation = false;
        }else if(Common_methods.hasMobileNumber(et_mobile)){
            validation = false;
            et_mobile.requestFocus();
            et_mobile.setError("please enter valid mobile number");
        } else if(!Common_methods.validateUsername(et_username)){
            validation = false;
            et_username.requestFocus();
            et_username.setError("please enter valid username");
        }else if(et_pwd.getText().length()<4){
            validation = false;
            et_pwd.setError("plase enter valid password");
            et_pwd.requestFocus();
        }else if(et_confirm_pwd.getText().length()<4){
            validation = false;
            et_confirm_pwd.setError("please enter valid confirm password");
            et_confirm_pwd.requestFocus();
        }else if(!et_pwd.getText().toString().equals(et_confirm_pwd.getText().toString())){
            validation = false;
            et_pwd.requestFocus();
            et_pwd.setError("password and confirm password must be same");
            et_confirm_pwd.setError("password and confirm password must be same");
        }else if(et_address.getText().toString().length()<4){
            et_address.setError("please enter valid Address");
            et_address.requestFocus();
            validation = false;
        }else if(et_landmark.getText().toString().length()<3){
            et_landmark.setError("Please enter valid Landmark");
            et_landmark.requestFocus();
            validation = false;
        }


        return validation;
    }

    private void fetchData(){
        RetrofitClient.getInstance().getEndPoint(SignUp.this,"show prograssbar").getResult(getparams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is","<><>"+res);
                try{
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.getString("Status").equalsIgnoreCase("successfully")){
                        startActivity(new Intent(SignUp.this, Reference.class));
                        finish();
                        SignIn.activity.finish();
                    }else{
                        Toast.makeText(SignUp.this, "Somthing went wrong please try agin", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String res) {

            }
        });
    }






    private Map<String,String > getparams(){
        Map<String ,String > params = new HashMap<>();

        params.put("action", APIS.SIGNUP);
        params.put("username",et_username.getText().toString());
        params.put("password",et_pwd.getText().toString());
//        params.put("adress",et_);
        params.put("mobilenumber",et_mobile.getText().toString());
        params.put("landmark",et_landmark.getText().toString());
        params.put("adress","testing");
        params.put("Name","Rajesh");

        params.put("Type","ST");


        return params;

    }
}
