package com.eme22.citasApp.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;
import com.eme22.citasApp.model.pojo.holiday.HolidaysResponse;
import com.eme22.citasApp.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentViewModel extends RecyclerViewViewModel<Appointment> {

    private final MutableLiveData<Calendar> date = new MutableLiveData<>();

    private final MutableLiveData<Boolean> ready = new MutableLiveData<>(false);

    private final MutableLiveData<ArrayList<LocalDate>> holidays = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>>> avaibleDates = new MutableLiveData<>();

    private final MutableLiveData<LocalDateTime> selectedDate = new MutableLiveData<>();

    public MutableLiveData<Boolean> getReady() {
        return ready;
    }

    public MutableLiveData<ArrayList<LocalDate>> getHolidays() {
        return holidays;
    }

    public MutableLiveData<ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>>> getAvaibleDates() {
        return avaibleDates;
    }

    public MutableLiveData<LocalDateTime> getSelectedDate() {
        return selectedDate;
    }

    public MutableLiveData<Calendar> getDate() { return date;}

    public void init(int id) {
        api.getMedicHolidays(id, null, null, null).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<HolidaysResponse> call, Response<HolidaysResponse> response) {
                if (response.isSuccessful()) {

                    HolidaysResponse appointmentsResponse = response.body();

                    ArrayList<LocalDate> date;

                    date = appointmentsResponse.getEmbedded().getHolidays().stream().map(date1 -> LocalDate.parse(date1.getDate())).collect(Collectors.toCollection(ArrayList::new));

                    holidays.setValue(date);

                } else {

                    System.out.println(response);

                }

                ready.setValue(true);

            }

            @Override
            public void onFailure(Call<HolidaysResponse> call, Throwable t) {
                t.printStackTrace();
                ready.setValue(true);
            }
        });
    }


    public void loadAppointment(int id) {

        api.getTodayAppointments(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AppointmentsResponse> call, Response<AppointmentsResponse> response) {
                loadingMutableLivedata.setValue(false);
                if (response.isSuccessful()) {

                    AppointmentsResponse appointmentsResponse = response.body();

                    ArrayList<LocalDateTime> a = appointmentsResponse.getEmbedded().getAppointments().stream().map(appointment -> appointment.getDate()).collect(Collectors.toCollection(ArrayList::new));

                    System.out.println("Data collected: ");

                    System.out.println(Arrays.toString(a.toArray()));

                    ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> s = parseDates(a);

                    System.out.println(Arrays.toString(s.toArray()));

                    avaibleDates.setValue( s );

                    listEmptyMutableLiveData.setValue(itemsArrayList.isEmpty());

                }

                listMutableLiveData.setValue(itemsArrayList);
            }

            @Override
            public void onFailure(Call<AppointmentsResponse> call, Throwable t) {

            }
        });

    }

    private ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> parseDates(ArrayList<LocalDateTime> collect) {
        return getRealSlots(date.getValue().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), collect);
    }

    private ArrayList<LocalDateTime> getTodaySlots(LocalDate date) {

        ArrayList<LocalDateTime> horas = new ArrayList<>();

        LocalTime horaInicial = LocalTime.of(7, 0); // Hora inicial: 00:00
        LocalTime horaFinal = LocalTime.of(18, 0); // Hora final: 23:59
        LocalDateTime dateTime = date.atTime(horaInicial);

        while (dateTime.toLocalTime().isBefore(horaFinal)) {
            horas.add(dateTime);
            dateTime = dateTime.plusMinutes(15);
        }

        return horas;

    }

    private ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> getRealSlots(LocalDate date, ArrayList<LocalDateTime> occupedDates) {

        return getTodaySlots(date).stream().map(localDateTime -> new Pair<>(localDateTime, new Pair<>(!occupedDates.contains(localDateTime), false))).collect(Collectors.toCollection(ArrayList::new));

    }

}