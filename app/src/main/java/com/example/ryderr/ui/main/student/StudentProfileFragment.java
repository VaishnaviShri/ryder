package com.example.ryderr.ui.main.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryderr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class StudentProfileFragment extends Fragment {


    FirebaseAuth mAuth;
    public StudentProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Button logoutBtn = view.findViewById(R.id.student_logout_button);
        TextView name = view.findViewById(R.id.profile_name);
        TextView email = view.findViewById(R.id.profile_email);
        FirebaseUser user = mAuth.getCurrentUser();
        String nameText = "Name: " + user.getDisplayName();
        String emailText = "Email: " + user.getEmail();
        name.setText(nameText);
        email.setText(emailText);
        logoutBtn.setOnClickListener(view1 -> {
            mAuth.signOut();
            Navigation.findNavController(view).navigate(R.id.action_studentProfile_to_splashScreen);

        });
    }
}