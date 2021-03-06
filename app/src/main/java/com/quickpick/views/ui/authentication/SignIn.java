package com.quickpick.views.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.views.ui.dashboard.DashBoardActivityNew;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 13-03-2018.
 */

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.txt_user_error)
    TextView txt_user_error;
    @Bind(R.id.txt_error_pwd)
    TextView txt_error_pwd;
    @Bind(R.id.scroll_view)
    ScrollView scroll_view;
    @Bind(R.id.txt_register)
    TextView txt_register;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        ButterKnife.bind(this);
        fetchListeners();
        btn_login.setOnClickListener(this);
        txt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if(checkvalidation()) {
                    startActivity(new Intent(this, DashBoardActivityNew.class));
                    finish();
                }
                break;
            case R.id.txt_register:
                startActivity(new Intent( this,SignUp.class));
                break;

        }

    }


    private void fetchListeners(){
        et_username.addTextChangedListener(new CustomWatcher(et_username));
        et_pwd.addTextChangedListener(new CustomWatcher(et_pwd));

        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new Common_methods(SignIn.this).ScreenLift(scroll_view,et_username,btn_login.getBottom()+300);
            }
        });
    }


    private class CustomWatcher implements TextWatcher {

        private View view;

        public CustomWatcher(View view) {
            this.view = view;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            switch (view.getId()) {

                case R.id.et_username:
                    if (et_username.getText().length() >= 3) {
                        txt_user_error.setVisibility(View.INVISIBLE);

                    }
                    break;
                case R.id.et_pwd:
                    if (et_pwd.getText().length() >= 3) {
                        txt_error_pwd.setVisibility(View.INVISIBLE);

                    }
                    break;


            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    private boolean checkvalidation(){
        boolean validation=true;
        if(!Common_methods.validateUsername(et_username)){
            et_username.requestFocus();
            et_username.setError("please enter valid username");
//            txt_user_error.setVisibility(View.VISIBLE);
            validation=false;
        }else if(et_pwd.getText().toString().isEmpty()||et_pwd.getText().toString().length()<3){
            et_pwd.requestFocus();
            et_pwd.setError("please enter valid password");
//            txt_error_pwd.setVisibility(View.VISIBLE);
            validation=false;
        }
        return validation;
    }
}
