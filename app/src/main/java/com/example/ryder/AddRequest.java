package com.example.ryder;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddRequest extends Fragment {

    private AddRequestViewModel mViewModel;
    Button button;
    RadioButton typeButton;
    RadioGroup radioGroup;

    public static AddRequest newInstance() {
        return new AddRequest();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textViewNAME);
        EditText editText = view.findViewById(R.id.editTextNAME);
        TextView textView1 = view.findViewById(R.id.textViewPickup);
        EditText editText1 = view.findViewById(R.id.editViewPickup);
        TextView textView2 = view.findViewById(R.id.textViewDestination);
        EditText editText2 = view.findViewById(R.id.editTextDestination);
        TextView textView3 = view.findViewById(R.id.textViewDateTime);
        EditText editText3 = view.findViewById(R.id.editViewDateTime);
        TextView textView4 = view.findViewById(R.id.chooseType);
        radioGroup = view.findViewById(R.id.radioGroup);

    }
    

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddRequestViewModel.class);
        // TODO: Use the ViewModel
    }

}