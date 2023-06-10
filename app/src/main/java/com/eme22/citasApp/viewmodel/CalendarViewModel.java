package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.medics.MedicsResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarViewModel extends RecyclerViewViewModel<Medic> {
    public void selectedDateChanged(int year, int month, int dayOfMonth) {
        api.getMedicsByDate(LocalDate.of(year, month, dayOfMonth),null, null, null).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MedicsResponse> call, Response<MedicsResponse> response) {
                loadingMutableLivedata.setValue(false);
                if (response.isSuccessful()) {

                    MedicsResponse appointmentsResponse = response.body();

                    itemsArrayList = new ArrayList<>(appointmentsResponse.getEmbedded().getMedics());

                    listEmptyMutableLiveData.setValue(itemsArrayList.isEmpty());

                }

                listMutableLiveData.setValue(itemsArrayList);
            }

            @Override
            public void onFailure(Call<MedicsResponse> call, Throwable t) {
                loadingMutableLivedata.setValue(false);
                listEmptyMutableLiveData.setValue(true);
                t.printStackTrace();
            }
        });

        listEmptyMutableLiveData.setValue(false);
        loadingMutableLivedata.setValue(true);

    }
}