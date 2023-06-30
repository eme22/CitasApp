package com.eme22.citasApp.viewmodel;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;
import com.eme22.citasApp.model.pojo.medics.Medic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends RecyclerViewViewModel<Appointment> {

    public void init(Integer dni){
        populateList(dni);
    }

    public void populateList(Integer dni){

        api.getHistory(dni).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {

                if (response.isSuccessful()) {

                    List<Appointment> appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse);

                }

                listMutableLiveData.setValue(itemsArrayList);

            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}