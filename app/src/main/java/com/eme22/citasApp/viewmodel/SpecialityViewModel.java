package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;
import com.eme22.citasApp.model.pojo.specialities.SpecialitiesResponse;
import com.eme22.citasApp.model.pojo.specialities.Speciality;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialityViewModel extends RecyclerViewViewModel<Speciality> {

    @Override
    public void init() {
        api.getSpecialities(null, null, null).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SpecialitiesResponse> call, Response<SpecialitiesResponse> response) {

                if (response.isSuccessful()) {

                    SpecialitiesResponse appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse.getEmbedded().getSpecialities());

                }

                listMutableLiveData.setValue(itemsArrayList);

            }

            @Override
            public void onFailure(Call<SpecialitiesResponse> call, Throwable t) {

            }
        });
    }
}