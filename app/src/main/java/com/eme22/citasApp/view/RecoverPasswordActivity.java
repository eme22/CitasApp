package com.eme22.citasApp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityMainBinding;
import com.eme22.citasApp.databinding.ActivityRecoverPasswordBinding;
import com.eme22.citasApp.viewmodel.LoginViewModel;
import com.eme22.citasApp.viewmodel.MainViewModel;
import com.eme22.citasApp.viewmodel.RecoverPasswordViewModel;

public class RecoverPasswordActivity extends AppCompatActivity {

    RecoverPasswordViewModel recoverPasswordViewModel;

    private ActivityRecoverPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecoverPasswordBinding.inflate(LayoutInflater.from(this));

        recoverPasswordViewModel = new ViewModelProvider(this).get(RecoverPasswordViewModel.class);

        setContentView(binding.getRoot());
    }
}