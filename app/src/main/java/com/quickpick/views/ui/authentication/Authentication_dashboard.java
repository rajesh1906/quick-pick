package com.quickpick.views.ui.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.quickpick.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 13-03-2018.
 */

public class Authentication_dashboard extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_qp_login)
    Button btn_qp_login;
    @Bind(R.id.txt_register)
    TextView txt_register;


    public static Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_dashboard);
        ButterKnife.bind(this);
        btn_qp_login.setOnClickListener(this);
        txt_register.setOnClickListener(this);
        activity=this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_qp_login:
                startActivity(new Intent(this,SignIn.class));
                break;
            case R.id.txt_register:
                startActivity(new Intent(this,SignUp.class));
                break;
        }


    }
}
