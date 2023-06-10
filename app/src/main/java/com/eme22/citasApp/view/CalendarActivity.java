package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_USER;
import static com.eme22.citasApp.view.DoctorListActivity.sendMedic;
import static com.eme22.citasApp.view.DoctorListActivity.sendUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.adapter.MedicRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.databinding.ActivityCalendarBinding;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;
import com.eme22.citasApp.viewmodel.CalendarViewModel;
import com.eme22.citasApp.viewmodel.DoctorListViewModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    CalendarViewModel calendarViewModel;

    private MedicRecyclerViewAdapter recyclerViewAdapter;
    private ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCalendarBinding.inflate(LayoutInflater.from(this));

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        setContentView(binding.getRoot());

        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        recyclerViewAdapter = new MedicRecyclerViewAdapter(cartItem -> {
            Intent intent = new Intent(this, BookAppointmentActivity.class);
            sendUser(user, intent);
            sendMedic(cartItem, intent);
            startActivity(intent);
        });

        binding.drCards.setAdapter(recyclerViewAdapter);

        LocalDate date = LocalDate.now(ZoneId.systemDefault());

        binding.calendarView.setMinDate(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());

        calendarViewModel.getListEmptyMutableLiveData().observe(this, aBoolean -> binding.emptyText.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        calendarViewModel.getLoadingMutableLivedata().observe(this, aBoolean -> binding.searchDateProgressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        calendarViewModel.getUserMutableLiveData().observe(this, arrayLists -> recyclerViewAdapter.updateAppointmentList(arrayLists));

        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> calendarViewModel.selectedDateChanged(year, month+1, dayOfMonth));

        calendarViewModel.selectedDateChanged(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

    }
}