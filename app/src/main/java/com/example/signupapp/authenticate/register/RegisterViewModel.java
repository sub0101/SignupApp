package com.example.signupapp.authenticate.register;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;


import com.example.signupapp.authenticate.validators.InputValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterViewModel extends BaseObservable {

    String name="", contact="" , email="" , password="" ,password2="";
    Boolean registerEnable=false;

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
        setRegisterEnable( isInputValid());
    }

    MutableLiveData<Boolean> isRegistered;
FirebaseAuth auth;

@Bindable
    public Boolean getRegisterEnable() {
        return registerEnable;

    }

    public void setRegisterEnable(Boolean registerEnable) {
        this.registerEnable = registerEnable;
        notifyChange();
    }

    public RegisterViewModel()
{
    auth = FirebaseAuth.getInstance();
    isRegistered =  new MutableLiveData<>(false);
}
@Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(name);
        setRegisterEnable( isInputValid());

    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;

        setRegisterEnable( isInputValid());

    }
@Bindable
    public MutableLiveData<Boolean> getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(MutableLiveData<Boolean> isRegistered) {
//        this.isRegistered = isRegistered;
        isRegistered.setValue(isRegistered.getValue());
        notifyChange();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setRegisterEnable( isInputValid());

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
       setRegisterEnable( isInputValid());

    }

    public void onSignUpClicked()
    {
        System.out.println(email + "" +password +"4");
        System.out.println("clicked");

        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//                    System.out.println(task.getException().getMessage());
verifyemail();
isRegistered.setValue(true);
                }
            }
        }). addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            System.out.println(e.getMessage());
        }
    });


    }

    private void verifyemail()
    {

//                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        isRegistered.setValue(true);
//                    }
//
//                });

                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
//        });
//
//        }

public boolean isInputValid()
{
    System.out.println(name + " "+email+  " "+contact + " " +password);
    Boolean bool = InputValidator.isContactValid(contact) &&
            InputValidator.isEmailValid(email) &&
            !name.equals("")&&
            InputValidator.isPasswordValid(password) && password.equals(password2);
    System.out.println(bool);
    return bool;

}



}
