package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.databinding.ActivityDoctorListBinding;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;
import com.eme22.citasApp.viewmodel.DoctorListViewModel;

public class BookAppointmentActivity extends AppCompatActivity {

    BookAppointmentViewModel bookAppointmentViewModel;

    private ActivityBookAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookAppointmentBinding.inflate(LayoutInflater.from(this));

        bookAppointmentViewModel = new ViewModelProvider(this).get(BookAppointmentViewModel.class);

        setContentView(binding.getRoot());
    }
}