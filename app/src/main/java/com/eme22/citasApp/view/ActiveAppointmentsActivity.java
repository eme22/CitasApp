package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityActiveAppointmentsBinding;
import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.viewmodel.ActiveAppointmentsViewModel;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;

public class ActiveAppointmentsActivity extends AppCompatActivity {

    ActiveAppointmentsViewModel activeAppointmentsViewModel;

    private ActivityActiveAppointmentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityActiveAppointmentsBinding.inflate(LayoutInflater.from(this));

        activeAppointmentsViewModel = new ViewModelProvider(this).get(ActiveAppointmentsViewModel.class);

        setContentView(binding.getRoot());
    }
}