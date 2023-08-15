package com.example.signupapp.authenticate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.signupapp.R;
import com.example.signupapp.authenticate.validators.InputValidator;
import com.example.signupapp.databinding.ForgetPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import javax.xml.validation.Validator;


public class ForgetPassword extends DialogFragment {
    FirebaseAuth auth;
    ForgetPasswordBinding forgetPasswordBinding;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
                getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
forgetPasswordBinding = DataBindingUtil.inflate(
        inflater, R.layout.forget_password, container, false);
                return forgetPasswordBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();

        getDialog().getWindow().setLayout(700, 400);

forgetPasswordBinding.forget.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (InputValidator.isEmailValid(forgetPasswordBinding.email.getText().toString())) {


            auth.sendPasswordResetEmail(forgetPasswordBinding.email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(), "Mail Sent Successfully", Toast.LENGTH_LONG).show();
                    getDialog().cancel();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else Toast.makeText(getContext() , "Enter valid Email" ,Toast.LENGTH_SHORT).show();
    }
});

    }


}
