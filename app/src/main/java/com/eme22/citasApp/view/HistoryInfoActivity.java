package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eme22.citasApp.R;
import com.eme22.citasApp.adapter.HistoryInfoMedicationReciclerViewAdapter;
import com.eme22.citasApp.adapter.HistoryRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityHistoryBinding;
import com.eme22.citasApp.viewmodel.HistoryViewModel;

public class HistoryInfoActivity extends AppCompatActivity {

    HistoryViewModel doctorListViewModel;

    private ActivityHistoryBinding binding;

    private HistoryInfoMedicationReciclerViewAdapter historyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_info);
    }
}