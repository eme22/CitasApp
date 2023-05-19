package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityRecoverPasswordBinding;
import com.eme22.citasApp.databinding.ActivitySpecialityBinding;
import com.eme22.citasApp.viewmodel.LoginViewModel;
import com.eme22.citasApp.viewmodel.RecoverPasswordViewModel;
import com.eme22.citasApp.viewmodel.SpecialityViewModel;

public class SpecialityActivity extends AppCompatActivity {

    SpecialityViewModel specialityViewModel;

    private ActivitySpecialityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpecialityBinding.inflate(LayoutInflater.from(this));

        specialityViewModel = new ViewModelProvider(this).get(SpecialityViewModel.class);

        setContentView(binding.getRoot());
    }
}