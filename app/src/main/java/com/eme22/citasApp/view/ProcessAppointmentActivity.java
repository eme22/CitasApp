package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_APPOINTMENT;
import static com.eme22.citasApp.util.Constants.EXTRA_MEDIC;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;
import static com.eme22.citasApp.view.DoctorListActivity.sendUser;

import android.content.Intent;
import android.os.Bundle;

import com.eme22.citasApp.model.pojo.User;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;
import com.eme22.citasApp.viewmodel.ProcessAppointmentViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.eme22.citasApp.databinding.ActivityProcessAppointmentBinding;

import com.eme22.citasApp.R;

import java.time.LocalDateTime;

public class ProcessAppointmentActivity extends AppCompatActivity {

    private ActivityProcessAppointmentBinding binding;
    private ProcessAppointmentViewModel bookAppointmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProcessAppointmentBinding.inflate(LayoutInflater.from(this));

        bookAppointmentViewModel = new ViewModelProvider(this).get(ProcessAppointmentViewModel.class);


        Patient user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        binding.fab.setOnClickListener(v -> {
            Intent i=new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            sendUser(user, i);
            startActivity(i);
        });


        Appointment time = (Appointment) getIntent().getSerializableExtra(EXTRA_APPOINTMENT);

        System.out.println("Appointment: ["+ time.toString() +"]");

        bookAppointmentViewModel.submitData(time);

        bookAppointmentViewModel.getSended().observe(this, aBoolean -> binding.fab.setVisibility(aBoolean ? View.VISIBLE : View.GONE));
        bookAppointmentViewModel.getSended().observe(this, aBoolean -> binding.inclueded.imageView.setVisibility(aBoolean ? View.VISIBLE : View.GONE));
        bookAppointmentViewModel.getSended().observe(this, aBoolean -> binding.inclueded.progressBar.setVisibility(!aBoolean ? View.VISIBLE : View.GONE));

        setContentView(binding.getRoot());
    }

}