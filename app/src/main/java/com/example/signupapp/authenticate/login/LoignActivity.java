package com.example.signupapp.authenticate.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.example.signupapp.LoginPhone;
import com.example.signupapp.MainActivity;
import com.example.signupapp.R;
import com.example.signupapp.authenticate.ClickHandler;
import com.example.signupapp.authenticate.ForgetPassword;
import com.example.signupapp.databinding.LayoutLoginBinding;
import com.example.signupapp.databinding.LayoutRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class LoignActivity extends AppCompatActivity {

    LayoutLoginBinding loginBinding;
    FragmentManager fragmentManager;
    LoginViewModel mainViewModel;
    LayoutRegisterBinding registerBinding;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.layout_login);
        mainViewModel = new LoginViewModel();
        loginBinding = DataBindingUtil.setContentView(this, R.layout.layout_login);
        loginBinding.setLoginViewModel(mainViewModel);
        loginBinding.setClickHandler(new ClickHandler(this));
        if(auth.getCurrentUser()!=null) load();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.getIsLogIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
System.out.println(bool + "hai");
                if (bool) {
                    Intent intent = new Intent(LoignActivity.this , MainActivity.class);
                    startActivity(intent);
                }
                else{
//                    load();
                }
            }
        });

    }
    private void load() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Verifying :Check Your Mail");

        if(!auth.getCurrentUser().isEmailVerified()) {
            progressDialog.show();

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //your method
                    System.out.println("checking");
                    auth.getCurrentUser().reload();
                    if (auth.getCurrentUser().isEmailVerified()) {
                        progressDialog.cancel();
                        mainViewModel.isLogIn.postValue(true);
                        cancel();
                    }

                }
            }, 0, 5000);

        }
        }
        public void forget(View view){
            ForgetPassword dialogFragment=new ForgetPassword();
            dialogFragment.show(getSupportFragmentManager(),"My  Fragment");
        }
        public void loginphone(View view){
        Intent intent  = new Intent(LoignActivity.this , LoginPhone.class);
        startActivity(intent);
        }




}



