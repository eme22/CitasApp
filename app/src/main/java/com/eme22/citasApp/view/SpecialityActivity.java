package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_SPECIALITY;
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
import com.eme22.citasApp.adapter.SpecialityRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivitySpecialityBinding;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.model.pojo.specialities.Speciality;
import com.eme22.citasApp.viewmodel.SpecialityViewModel;

import java.util.ArrayList;

public class SpecialityActivity extends AppCompatActivity {

    SpecialityViewModel specialityViewModel;

    private ActivitySpecialityBinding binding;

    private SpecialityRecyclerViewAdapter specialityRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpecialityBinding.inflate(LayoutInflater.from(this));

        specialityViewModel = new ViewModelProvider(this).get(SpecialityViewModel.class);

        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        setContentView(binding.getRoot());

        binding.docCards.setLayoutManager(new LinearLayoutManager(this));

        specialityRecyclerViewAdapter = new SpecialityRecyclerViewAdapter(cartItem -> {
            Intent intent = new Intent(SpecialityActivity.this, DoctorListActivity.class);
            sendUser(user, intent);
            sendSpeciality(cartItem, intent);
            startActivity(intent);
        });
        binding.docCards.setAdapter(specialityRecyclerViewAdapter);

        specialityViewModel.getUserMutableLiveData().observe(this, specialityLivedataObserver);
    }

    private static void sendUser(Patient patient, Intent intent) {
        intent.putExtra(EXTRA_USER, patient);
    }

    private static void sendSpeciality(Speciality patient, Intent intent) {
        intent.putExtra(EXTRA_SPECIALITY, patient);
    }

    Observer<ArrayList<Speciality>> specialityLivedataObserver = userArrayList -> specialityRecyclerViewAdapter.updateAppointmentList(userArrayList);
}