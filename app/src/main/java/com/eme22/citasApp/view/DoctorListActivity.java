package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityDoctorListBinding;
import com.eme22.citasApp.viewmodel.DoctorListViewModel;
import com.eme22.citasApp.viewmodel.LoginViewModel;
import com.eme22.citasApp.viewmodel.MainViewModel;

public class DoctorListActivity extends AppCompatActivity {

    DoctorListViewModel doctorListViewModel;

    private ActivityDoctorListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorListBinding.inflate(LayoutInflater.from(this));

        doctorListViewModel = new ViewModelProvider(this).get(DoctorListViewModel.class);

        setContentView(binding.getRoot());
    }
}