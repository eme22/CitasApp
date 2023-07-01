package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.medications.Medication;
import com.eme22.citasApp.model.pojo.prescriptions.Prescription;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryInfoViewModel extends RecyclerViewViewModel<Prescription> {

    public void init(int id){

        api.getAllPrescriptionByAppointment(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Prescription>> call, Response<List<Prescription>> response) {

                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(new ArrayList<>(response.body()));
                }

            }

            @Override
            public void onFailure(Call<List<Prescription>> call, Throwable t) {

            }
        });


    }



}