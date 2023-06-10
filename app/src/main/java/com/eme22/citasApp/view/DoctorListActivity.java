package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_MEDIC;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eme22.citasApp.adapter.MedicRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityDoctorListBinding;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.DoctorListViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {

    DoctorListViewModel doctorListViewModel;

    private ActivityDoctorListBinding binding;

    private MedicRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorListBinding.inflate(LayoutInflater.from(this));

        doctorListViewModel = new ViewModelProvider(this).get(DoctorListViewModel.class);

        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        setContentView(binding.getRoot());

        recyclerViewAdapter = new MedicRecyclerViewAdapter(cartItem -> {
            Intent intent = new Intent(DoctorListActivity.this, BookAppointmentActivity.class);
            sendUser(user, intent);
            sendMedic(cartItem, intent);
            startActivity(intent);
        });
        binding.drCards.setAdapter(recyclerViewAdapter);

        doctorListViewModel.getUserMutableLiveData().observe(this, userArrayList -> recyclerViewAdapter.updateAppointmentList(userArrayList));

        doctorListViewModel.getListEmptyMutableLiveData().observe(this, aBoolean -> binding.emptyText.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        doctorListViewModel.getLoadingMutableLivedata().observe(this, aBoolean -> binding.searchDateProgressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE));

        doctorListViewModel.init(null);

    }
    static void sendUser(Patient patient, Intent intent) {
        intent.putExtra(EXTRA_USER, patient);
    }

    static void sendMedic(Medic patient, Intent intent) {
        intent.putExtra(EXTRA_MEDIC, patient);
    }

    static void sendAppointmentDate(LocalDateTime patient, Intent intent) {
        intent.putExtra("aaaAP", patient);
    }
}