package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;
import com.eme22.citasApp.model.pojo.specialities.SpecialitiesResponse;
import com.eme22.citasApp.model.pojo.specialities.Speciality;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialityViewModel extends RecyclerViewViewModel<Speciality> {

    @Override
    public void init() {
        api.getSpecialities().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Speciality>> call, Response<List<Speciality>> response) {

                if (response.isSuccessful()) {

                    List<Speciality> appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse);

                }

                listMutableLiveData.setValue(itemsArrayList);

            }

            @Override
            public void onFailure(Call<List<Speciality>> call, Throwable t) {

            }
        });
    }
}