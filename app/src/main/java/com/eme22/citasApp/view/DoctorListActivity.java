package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_USER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eme22.citasApp.adapter.MedicRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityDoctorListBinding;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.DoctorListViewModel;

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

        binding.drCards.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new MedicRecyclerViewAdapter(cartItem -> {
            Intent intent = new Intent(DoctorListActivity.this, AppointMentPickActivity.class);
            sendUser(user, intent);
            startActivity(intent);
        });
        binding.drCards.setAdapter(recyclerViewAdapter);

        doctorListViewModel.getUserMutableLiveData().observe(this, specialityLivedataObserver);
    }

    Observer<ArrayList<Medic>> specialityLivedataObserver = (Observer<ArrayList<Medic>>) userArrayList -> {

        recyclerViewAdapter.updateAppointmentList(userArrayList);

    };

    private static void sendUser(Patient patient, Intent intent) {
        intent.putExtra(EXTRA_USER, patient);
    }
}