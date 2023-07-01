package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_APPOINTMENT;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.eme22.citasApp.R;
import com.eme22.citasApp.adapter.HistoryInfoMedicationReciclerViewAdapter;
import com.eme22.citasApp.adapter.HistoryRecyclerViewAdapter;
import com.eme22.citasApp.adapter.MedicRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityHistoryBinding;
import com.eme22.citasApp.databinding.ActivityHistoryInfoBinding;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.medications.Medication;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.HistoryInfoViewModel;
import com.eme22.citasApp.viewmodel.HistoryViewModel;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryInfoActivity extends AppCompatActivity {

    HistoryInfoViewModel doctorListViewModel;

    private ActivityHistoryInfoBinding binding;

    private HistoryInfoMedicationReciclerViewAdapter historyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryInfoBinding.inflate(LayoutInflater.from(this));

        doctorListViewModel = new ViewModelProvider(this).get(HistoryInfoViewModel.class);

        setContentView(binding.getRoot());

        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        Appointment appointment = (Appointment) getIntent().getSerializableExtra(EXTRA_APPOINTMENT);

        System.out.println(appointment.getPrescriptionsById());

        doctorListViewModel.init(appointment.getId());

//        historyRecyclerViewAdapter.setAppointmentArrayList(new ArrayList<>(appointment.getPrescriptionsById()));

        binding.drAppName2.setText(user.getName1() + " " + user.getLastname1());

        binding.drAppName0.setText(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(appointment.getDate()));

        binding.drAppName6.setText(appointment.getDescription());

        binding.drAppName9.setText(appointment.getResult());

        historyRecyclerViewAdapter = new HistoryInfoMedicationReciclerViewAdapter();

        binding.medicationList.setAdapter(historyRecyclerViewAdapter);

        doctorListViewModel.getUserMutableLiveData().observe(this, medications -> historyRecyclerViewAdapter.setAppointmentArrayList(medications));

        Picasso.get().load(user.getImage()).into(binding.drAppImg);

    }
}