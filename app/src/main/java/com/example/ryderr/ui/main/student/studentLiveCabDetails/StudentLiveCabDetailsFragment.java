package com.example.ryderr.ui.main.student.studentLiveCabDetails;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryderr.R;
import com.example.ryderr.models.LiveCab;
import com.example.ryderr.ui.main.student.studentHome.live.LiveCabsViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
//
//import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
//import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
//import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

public class StudentLiveCabDetailsFragment extends Fragment {
    private LiveCab liveCabOb;
    private TextView fare, from, to, vehicle, vehicleNo, time, capacity, driver;
    private ListView ridersList;
    private LiveCabsViewModel mViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public StudentLiveCabDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        liveCabOb = new LiveCab();
        mViewModel = new LiveCabsViewModel();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_live_cab_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fare = (TextView)view.findViewById(R.id.estimatedFare_livecabdetails);
        from = (TextView)view.findViewById(R.id.from_livecabdetails);
        to = (TextView)view.findViewById(R.id.to_livecabdetails);
        vehicle = (TextView)view.findViewById(R.id.vehicleType_livecabdetails);
        vehicleNo = (TextView)view.findViewById(R.id.vehicleNumber_livecabdetails);
        time = (TextView)view.findViewById(R.id.time_livecabdetails);
        capacity = (TextView)view.findViewById(R.id.capacity_livecabdetails);
        driver = (TextView)view.findViewById(R.id.driver_livecabdetails);

        String cabId = StudentLiveCabDetailsFragmentArgs.fromBundle(getArguments()).getCabId();

        Observer<LiveCab> observer = new Observer<LiveCab>() {
            @Override
            public void onChanged(LiveCab liveCab) {
                liveCabOb = liveCab;
                from.setText(liveCabOb.getFrom_location());
                fare.setText(liveCabOb.getFareText());
                to.setText(liveCabOb.getTo_location());
                vehicle.setText(liveCabOb.getVehicle_type());
                vehicleNo.setText(liveCabOb.getVehicle_number());
                time.setText(liveCabOb.getDeparture_time());
                capacity.setText(String.valueOf(liveCabOb.getCapacity()));
                driver.setText(liveCabOb.getDriver_name());
                ridersList = (ListView)view.findViewById(R.id.ridersList_livecabdetails);
                ArrayList<String> rider = liveCabOb.getRiders_names();
                double riderFare = liveCabOb.getFare()/ liveCabOb.getCount_riders();
//                ArrayList<String> display = new ArrayList<String>();
//                Iterator itr = rider.iterator();
//                while(itr.hasNext()){
//                    display.add(itr.next().toString()+"\t \t" + riderFare);
//                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, rider);
                ridersList.setAdapter(arrayAdapter);
            }
        };

        mViewModel.getLiveCabDetail(cabId).observe(getViewLifecycleOwner(), observer);

        Button studentJoinCabBtn = view.findViewById(R.id.studentLiveCabJoinBtn);
        studentJoinCabBtn.setOnClickListener(view1 -> {
            String uid = FirebaseAuth.getInstance().getUid();
            String ridername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            db.collection("cabs").document(liveCabOb.getLive_cab_id()).update(
                            "riders_names", FieldValue.arrayUnion(ridername))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Navigation.findNavController(view).navigate(R.id.action_studentCabDetailsFragment_to_cabsFragment);
                        }
                    });
            //

        });
        Button chatBtn = view.findViewById(R.id.studentLiveCabChatBtn);
        chatBtn.setOnClickListener(view1 -> {
            String requestId = liveCabOb.getLive_cab_id();
            StudentLiveCabDetailsFragmentDirections.ActionStudentLiveCabDetailsFragmentToChatFragment action = StudentLiveCabDetailsFragmentDirections.actionStudentLiveCabDetailsFragmentToChatFragment();
            action.setGroupId(requestId);
            Navigation.findNavController(view).navigate((NavDirections) action);

        });

        Button paymentBtn = view.findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(view1 -> {
            String upi_id = liveCabOb.getUpi_id();
            payUsingUpi(String.valueOf(liveCabOb.getFare()), liveCabOb.getUpi_id(), liveCabOb.getDriver_name(), liveCabOb.getTo_location());
        });

        super.onViewCreated(view, savedInstanceState);
    }
    public void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        try {
            startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            // Define what your app should do if no activity can handle the intent.
        }


    }





//
//        private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
//            // on below line we are calling an easy payment method and passing
//            // all parameters to it such as upi id,name, description and others.
//            final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
//                    .with(this)
//                    // on below line we are adding upi id.
//                    .setPayeeVpa(upi)
//                    // on below line we are setting name to which we are making payment.
//                    .setPayeeName(name)
//                    // on below line we are passing transaction id.
//                    .setTransactionId(transactionId)
//                    // on below line we are passing transaction ref id.
//                    .setTransactionRefId(transactionId)
//                    // on below line we are adding description to payment.
//                    .setDescription(desc)
//                    // on below line we are passing amount which is being paid.
//                    .setAmount(amount)
//                    // on below line we are calling a build method to build this ui.
//                    .build();
//            // on below line we are calling a start
//            // payment method to start a payment.
//            easyUpiPayment.startPayment();
//            // on below line we are calling a set payment
//            // status listener method to call other payment methods.
//            easyUpiPayment.setPaymentStatusListener(this);
//        }
//
//        @Override
//        public void onTransactionCompleted(TransactionDetails transactionDetails) {
//            // on below line we are getting details about transaction when completed.
//            String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();
//            transactionDetailsTV.setVisibility(View.VISIBLE);
//            // on below line we are setting details to our text view.
//            transactionDetailsTV.setText(transcDetails);
//        }
//
//        @Override
//        public void onTransactionSuccess() {
//            // this method is called when transaction is successful and we are displaying a toast message.
//            Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onTransactionSubmitted() {
//            // this method is called when transaction is done
//            // but it may be successful or failure.
//            Log.e("TAG", "TRANSACTION SUBMIT");
//        }
//
//        @Override
//        public void onTransactionFailed() {
//            // this method is called when transaction is failure.
//            Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onTransactionCancelled() {
//            // this method is called when transaction is cancelled.
//            Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onAppNotFound() {
//            // this method is called when the users device is not having any app installed for making payment.
//            Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();
//        }


}