package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_USER;
import static com.eme22.citasApp.view.DoctorListActivity.sendUser;
import static com.eme22.citasApp.view.HistoryActivity.sendAppointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.eme22.citasApp.R;
import com.eme22.citasApp.adapter.HistoryRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityActiveAppointmentsBinding;
import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.ActiveAppointmentsViewModel;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;

import java.util.ArrayList;

public class ActiveAppointmentsActivity extends AppCompatActivity {

    ActiveAppointmentsViewModel activeAppointmentsViewModel;

    private ActivityActiveAppointmentsBinding binding;

    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityActiveAppointmentsBinding.inflate(LayoutInflater.from(this));

        activeAppointmentsViewModel = new ViewModelProvider(this).get(ActiveAppointmentsViewModel.class);

        setContentView(binding.getRoot());

        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        activeAppointmentsViewModel.init(user.getDni());

        binding.appointmentsRecycler.setLayoutManager(new LinearLayoutManager(this));

        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(cartItem -> {
            Intent intent = new Intent(ActiveAppointmentsActivity.this, HistoryInfoActivity.class);
            sendAppointment(cartItem, intent);
            sendUser(user, intent);
            startActivity(intent);
        });
        binding.appointmentsRecycler.setAdapter(historyRecyclerViewAdapter);

        activeAppointmentsViewModel.getListEmptyMutableLiveData().observe(this, aBoolean -> binding.emptyView2.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        activeAppointmentsViewModel.getLoadingMutableLivedata().observe(this, aBoolean -> binding.searchDateProgressBar3.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        activeAppointmentsViewModel.getUserMutableLiveData().observe(this, userListUpdateObserver);


    }

    Observer<ArrayList<Appointment>> userListUpdateObserver = userArrayList -> {

        if (userArrayList.isEmpty()) {

            this.runOnUiThread(() -> binding.getRoot().post(() -> {
                binding.emptyView2.setVisibility(View.VISIBLE);
                binding.appointmentsRecycler.setVisibility(View.GONE);
            }));


        } else {

            historyRecyclerViewAdapter.updateAppointmentList(userArrayList);

        }
    };
}