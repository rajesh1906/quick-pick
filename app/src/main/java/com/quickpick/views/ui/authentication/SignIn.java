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
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.views.ui.dashboard.DashBoardActivityNew;

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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        ButterKnife.bind(this);
        fetchListeners();
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(checkvalidation()) {
            startActivity(new Intent(this, DashBoardActivityNew.class));
            Authentication_dashboard.activity.finish();
            finish();
        }
    }


    private void fetchListeners(){
        et_username.addTextChangedListener(new CustomWatcher(et_username));
        et_pwd.addTextChangedListener(new CustomWatcher(et_pwd));
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
        if(et_username.getText().toString().isEmpty()||et_username.getText().toString().length()<3){
            txt_user_error.setVisibility(View.VISIBLE);
            validation=false;
        }else if(et_pwd.getText().toString().isEmpty()||et_pwd.getText().toString().length()<3){
            txt_error_pwd.setVisibility(View.VISIBLE);
            validation=false;
        }
        return validation;
    }
}
