package com.example.signupapp.authenticate.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.signupapp.R;
import com.example.signupapp.authenticate.ClickHandler;
import com.example.signupapp.authenticate.login.LoignActivity;
import com.example.signupapp.databinding.LayoutRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity {
    LayoutRegisterBinding registerBinding;

RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
System.out.println("layout register");
registerBinding = DataBindingUtil.setContentView(this , R.layout.layout_register);
registerViewModel  = new RegisterViewModel();
registerBinding.setRegisterViewModel(registerViewModel);
registerBinding.setClickHandler(new ClickHandler(this));
//FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            if (user != null) {
//                // Check if the user is email verified
//                if (user.isEmailVerified()) {
//                    System.out.println("verfied");
//                    // User is email verified, perform desired actions
//                } else {
//                    System.out.println("not verifiaed");
//                    // User is not email verified, handle accordingly
//                    user.reload();
//                }
//            }
//        };
//        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
       registerViewModel.getIsRegistered().observe(this, new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean aBoolean) {
               System.out.println("outside on change");
               if(aBoolean){
                   System.out.println("inside on change");
                   Toast.makeText(getBaseContext(), "Verification Mail Sent Successfully" , Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(SignUpActivity.this , LoignActivity.class);
                   startActivity(intent);
               }
               else{

               }

           }
       });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
private void load(){

}




}
