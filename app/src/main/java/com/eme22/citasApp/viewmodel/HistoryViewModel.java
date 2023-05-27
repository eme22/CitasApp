package com.eme22.citasApp.viewmodel;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;

import java.util.ArrayList;

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
            public void onResponse(Call<AppointmentsResponse> call, Response<AppointmentsResponse> response) {

                if (response.isSuccessful()) {

                    AppointmentsResponse appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse.getEmbedded().getAppointments());

                }

                listMutableLiveData.setValue(itemsArrayList);

            }

            @Override
            public void onFailure(Call<AppointmentsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}