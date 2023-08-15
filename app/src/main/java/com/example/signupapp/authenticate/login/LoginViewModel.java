package com.example.signupapp.authenticate.login;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends BaseObservable {

String email,password;
MutableLiveData<Boolean> isLogIn = new MutableLiveData<>(false);
FirebaseAuth auth = FirebaseAuth.getInstance();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public MutableLiveData<Boolean> getIsLogIn() {
        return isLogIn;
    }

    public void setIsLogIn(MutableLiveData<Boolean> isLogIn) {
        this.isLogIn = isLogIn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onLoginClicked(View view)
    {

        auth.signInWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            if(!auth.getCurrentUser().isEmailVerified()){
              auth.getCurrentUser().sendEmailVerification();
                auth.signOut();
                Toast.makeText(view.getContext()  , "Email is not verified Please Verify" , Toast.LENGTH_SHORT).show();
            }
            else  {
                isLogIn.setValue(true);
                Toast.makeText(view.getContext()  , "Login Successfully " , Toast.LENGTH_SHORT).show();
            }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext() , e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }




}
