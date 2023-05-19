package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityDoctorListBinding;
import com.eme22.citasApp.databinding.ActivityHistoryBinding;
import com.eme22.citasApp.viewmodel.DoctorListViewModel;
import com.eme22.citasApp.viewmodel.HistoryViewModel;

public class HistoryActivity extends AppCompatActivity {

    HistoryViewModel doctorListViewModel;

    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryBinding.inflate(LayoutInflater.from(this));

        doctorListViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        setContentView(binding.getRoot());
    }
}