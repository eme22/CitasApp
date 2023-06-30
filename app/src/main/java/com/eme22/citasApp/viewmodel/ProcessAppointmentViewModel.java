package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessAppointmentViewModel extends RecyclerViewViewModel<Appointment> {

    private final MutableLiveData<Boolean> sended = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getSended() {
        return sended;
    }

    public void submitData(Appointment appointment) {

        api.appointment(appointment).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    sended.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {

            }
        });
    }
}
