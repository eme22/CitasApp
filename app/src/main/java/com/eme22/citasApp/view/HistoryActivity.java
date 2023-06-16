package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_APPOINTMENT;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.eme22.citasApp.adapter.HistoryRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityHistoryBinding;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.HistoryViewModel;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    HistoryViewModel doctorListViewModel;

    private ActivityHistoryBinding binding;

    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryBinding.inflate(LayoutInflater.from(this));

        doctorListViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        setContentView(binding.getRoot());

        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        doctorListViewModel.init(user.getDni());

        binding.historyCards.setLayoutManager(new LinearLayoutManager(this));

        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(cartItem -> {
            Intent intent = new Intent(HistoryActivity.this, HistoryInfoActivity.class);
            sendAppointment(cartItem, intent);
            sendUser(user, intent);
            startActivity(intent);
        });
        binding.historyCards.setAdapter(historyRecyclerViewAdapter);

        doctorListViewModel.getListEmptyMutableLiveData().observe(this, aBoolean -> binding.emptyText2.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        doctorListViewModel.getLoadingMutableLivedata().observe(this, aBoolean -> binding.searchDateProgressBar2.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        doctorListViewModel.getUserMutableLiveData().observe(this, userListUpdateObserver);

    }

    private static void sendUser(Patient patient, Intent intent) {
        intent.putExtra(EXTRA_USER, patient);
    }

    static void sendAppointment(Appointment patient, Intent intent) {
        intent.putExtra(EXTRA_APPOINTMENT, patient);
    }

    Observer<ArrayList<Appointment>> userListUpdateObserver = userArrayList -> {

        if (userArrayList.isEmpty()) {

            this.runOnUiThread(() -> binding.getRoot().post(() -> {
                binding.emptyView.setVisibility(View.VISIBLE);
                binding.historyCards.setVisibility(View.GONE);
            }));


        } else {

            historyRecyclerViewAdapter.updateAppointmentList(userArrayList);

        }
    };

}