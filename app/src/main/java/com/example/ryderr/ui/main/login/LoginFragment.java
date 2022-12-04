package com.example.ryderr.ui.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ryderr.R;
import com.example.ryderr.models.Student;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "login fragment";
    private LoginViewModel mViewModel;
    private FirebaseAuth mAuth;
    private static final int SIGN_IN_REQUEST_CODE = 0;
    private GoogleSignInClient googleSignInClient;
    private FirebaseFirestore db;
    View mView;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mViewModel = new LoginViewModel();
        db = FirebaseFirestore.getInstance();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        FirebaseApp.initializeApp(getContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        mAuth = FirebaseAuth.getInstance();

        Button studentLogin = view.findViewById(R.id.student_login_button);
        Button driverlogin =view.findViewById(R.id.driver_login_btn);
        studentLogin.setOnClickListener(view1 -> {
            try{
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }catch (Exception e){

                e.printStackTrace();
            }
        });
        driverlogin.setOnClickListener(view1 -> {
            Navigation.findNavController(mView).navigate(R.id.action_loginFragment_to_driverLogin);
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{


                //GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                String tokenId = accountTask.getResult().getIdToken();
                firebaseAuthWithGoogle(tokenId);

               // signIn(account);
            }catch(Exception e){
                Toast.makeText(getContext(), "it's me. hi. I'm the problem. It's me!", Toast.LENGTH_SHORT).show();
                Log.e("login fail", e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mViewModel.addUser(user);
                            updateUI(user);
                            } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "failed!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }
    private void updateUI(FirebaseUser user){
        String email = user.getEmail();
        if(email.endsWith("hyderabad.bits-pilani.ac.in")){
            String name = user.getDisplayName();
            Student student = new Student(user.getUid(), name, user.getEmail());
            db.collection("students").document(student.getUid()).set(student);
            Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(mView).navigate(R.id.action_loginFragment_to_cabsFragment);
        }else{
            Toast.makeText(getContext(), "Please use BITS mail!", Toast.LENGTH_SHORT).show();
        }

    }
    private void signIn(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("login debug", "YAY LOGGED IN SUCCESSFULLY");
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String name = firebaseUser.getDisplayName();
                        String email = firebaseUser.getEmail();
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                                Toast.makeText(getContext(), "account created for: "+ email, Toast.LENGTH_SHORT).show();
                        }else{
                                Toast.makeText(getContext(), "welcome "+name, Toast.LENGTH_SHORT).show();
                        }
                        if(email.endsWith("hyderabad.bits-pilani.ac.in")){
                            Navigation.findNavController(mView).navigate(R.id.action_loginFragment_to_liveCabs);
                        }else{
                            Toast.makeText(getContext(), "Please use BITS mail!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }
}