package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.adapter.HistoryInfoMedicationReciclerViewAdapter;
import com.eme22.citasApp.adapter.HistoryRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityHistoryBinding;
import com.eme22.citasApp.databinding.ActivityHistoryInfoBinding;
import com.eme22.citasApp.viewmodel.HistoryInfoViewModel;
import com.eme22.citasApp.viewmodel.HistoryViewModel;

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
    }
}