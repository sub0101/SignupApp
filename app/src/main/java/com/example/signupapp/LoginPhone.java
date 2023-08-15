package com.example.signupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.signupapp.authenticate.validators.InputValidator;
import com.example.signupapp.databinding.ActivityLoginPhoneBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.xml.validation.Validator;

public class LoginPhone extends AppCompatActivity {

    ActivityLoginPhoneBinding loginPhoneBinding;
    FirebaseAuth firebaseAuth;
    String verifyid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        firebaseAuth = FirebaseAuth.getInstance();
        loginPhoneBinding = DataBindingUtil.setContentView(LoginPhone.this , R.layout.activity_login_phone);
        loginPhoneBinding.getotp.setOnClickListener(new ClickListner());
        loginPhoneBinding.verifyotp.setOnClickListener(new ClickListner());
    }


    private void signInWithCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPhone.this,"Successfully Login", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(LoginPhone.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {

                            Toast.makeText(LoginPhone.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void authenticate () {

        String number  = loginPhoneBinding.contact.getText().toString();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks


            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verifyid = s;
            loginPhoneBinding.otpLayout.setVisibility(View.VISIBLE);
            loginPhoneBinding.getotp.setVisibility(View.GONE);
        }


        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();


            if (code != null) {

               loginPhoneBinding.otp.setText(code);
                verifycode(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifycode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyid, code);


        signInWithCredential(credential);
    }


    private class ClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            if(v==loginPhoneBinding.getotp){
                if(InputValidator.isContactValid(loginPhoneBinding.contact.getText().toString())){

                    authenticate();
                }
                else{
                    Toast.makeText(LoginPhone.this, "enter a valid contact", Toast.LENGTH_LONG).show();

                }
            }
        else if(v==loginPhoneBinding.verifyotp) verifycode(loginPhoneBinding.otp.getText().toString());
        }
    }
}