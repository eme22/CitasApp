package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.medics.MedicsResponse;
import com.eme22.citasApp.model.pojo.specialities.SpecialitiesResponse;
import com.eme22.citasApp.model.pojo.specialities.Speciality;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListViewModel extends RecyclerViewViewModel<Medic> {

    public void init(Speciality speciality) {
        Callback<MedicsResponse> callback = new Callback<>() {
            @Override
            public void onResponse(Call<MedicsResponse> call, Response<MedicsResponse> response) {

                if (response.isSuccessful()) {

                    MedicsResponse appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse.getEmbedded().getMedics());

                }

                listMutableLiveData.setValue(itemsArrayList);

            }

            @Override
            public void onFailure(Call<MedicsResponse> call, Throwable t) {

            }
        };

        if (speciality != null) {

            api.getMedicsBySpeciality(speciality.getId()).enqueue(callback);

        }
        else {

            api.getMedics(1, 100, null).enqueue(callback);

        }
    }

}