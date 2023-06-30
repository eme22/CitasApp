package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.medics.MedicsResponse;
import com.eme22.citasApp.model.pojo.specialities.SpecialitiesResponse;
import com.eme22.citasApp.model.pojo.specialities.Speciality;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListViewModel extends RecyclerViewViewModel<Medic> {

    public void init(Speciality speciality) {
        Callback<List<Medic>> callback = new Callback<>() {
            @Override
            public void onResponse(Call<List<Medic>> call, Response<List<Medic>> response) {
                loadingMutableLivedata.setValue(false);
                if (response.isSuccessful()) {

                    List<Medic> appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse);

                    listEmptyMutableLiveData.setValue(itemsArrayList.isEmpty());

                }

                listMutableLiveData.setValue(itemsArrayList);
            }

            @Override
            public void onFailure(Call<List<Medic>> call, Throwable t) {
                loadingMutableLivedata.setValue(false);
                t.printStackTrace();
            }
        };

        if (speciality != null) {

            api.getMedicsBySpeciality(speciality.getId()).enqueue(callback);

        }
        else {

            api.getMedics().enqueue(callback);

        }
        loadingMutableLivedata.setValue(true);
    }

}