package com.example.ryderr.ui.main.login.driverLogin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ryderr.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class DriverLoginFragment extends Fragment {
    View driverLoginFragObj;
    private EditText driverEmail;
    private EditText driverPassword;
    private FirebaseAuth mAuth;
    private MaterialButton loginBtn;

    public DriverLoginFragment(){}

    private void attachToXML(){
        try{
            driverEmail = driverLoginFragObj.findViewById(R.id.driver_email);
            driverPassword = driverLoginFragObj.findViewById(R.id.driver_password);
            mAuth = FirebaseAuth.getInstance();
            loginBtn = driverLoginFragObj.findViewById(R.id.loginBtn);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginDriver();
                }
            });
        }
        catch (Exception e){
            Log.e("driver login", e.getMessage());
          //  Toast.makeText(getContext(), "1:"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginDriver(){
        try{
            if(!driverEmail.getText().toString().isEmpty() && !driverPassword.getText().toString().isEmpty()){
                if(mAuth!=null){
                    loginBtn.setEnabled(false);
                    mAuth.signInWithEmailAndPassword(driverEmail.getText().toString().trim(), driverPassword.getText().toString().trim())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(driverLoginFragObj).navigate(R.id.action_driverLogin_to_driverFragment);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();}
                            });
                }

            }
            else{
                Toast.makeText(getContext(), "Please fill both fields", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       driverLoginFragObj = inflater.inflate(R.layout.fragment_driver_login, container, false);
       attachToXML();
       return driverLoginFragObj;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}