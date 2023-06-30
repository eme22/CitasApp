package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.medics.MedicsResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarViewModel extends RecyclerViewViewModel<Medic> {
    public void selectedDateChanged(int year, int month, int dayOfMonth) {
        api.getMedicsByDate(LocalDate.of(year, month, dayOfMonth)).enqueue(new Callback<>() {
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
                listEmptyMutableLiveData.setValue(true);
                t.printStackTrace();
            }
        });

        listEmptyMutableLiveData.setValue(false);
        loadingMutableLivedata.setValue(true);

    }
}