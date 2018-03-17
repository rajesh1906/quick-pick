package com.quickpick.views.ui.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.presenter.utils.Common_methods;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()) {
                    startActivity(new Intent(SignUp.this, Reference.class));
                    finish();
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
            et_pwd.setError("password and confirm password must be same");
            et_confirm_pwd.setError("password and confirm password must be same");
        }


        return validation;
    }
}
