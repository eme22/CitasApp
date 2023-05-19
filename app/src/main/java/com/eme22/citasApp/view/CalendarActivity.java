package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.databinding.ActivityCalendarBinding;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;
import com.eme22.citasApp.viewmodel.CalendarViewModel;

public class CalendarActivity extends AppCompatActivity {

    CalendarViewModel calendarViewModel;

    private ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCalendarBinding.inflate(LayoutInflater.from(this));

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        setContentView(binding.getRoot());
    }
}