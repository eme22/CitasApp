package com.eme22.citasApp.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.holiday.Holidays;
import com.eme22.citasApp.model.pojo.medics.Medic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentViewModel extends RecyclerViewViewModel<Appointment> {

    private final MutableLiveData<Calendar> date = new MutableLiveData<>();

    private final MutableLiveData<Boolean> ready = new MutableLiveData<>(false);

    private final MutableLiveData<ArrayList<LocalDate>> holidays = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<LocalDateTime>> avaibleDates = new MutableLiveData<>();

    private final MutableLiveData<LocalDateTime> selectedDate = new MutableLiveData<>();

    public MutableLiveData<Boolean> getReady() {
        return ready;
    }

    public MutableLiveData<ArrayList<LocalDate>> getHolidays() {
        return holidays;
    }

    public MutableLiveData<ArrayList<LocalDateTime>> getAvaibleDates() {
        return avaibleDates;
    }

    public MutableLiveData<LocalDateTime> getSelectedDate() {
        return selectedDate;
    }

    public MutableLiveData<Calendar> getDate() { return date;}

    public void init(Medic medic) {

        holidays.setValue(medic.getHolidays().stream().map(Holidays::getDate).collect(Collectors.toCollection(ArrayList::new)));
        ready.setValue(true);

        /**
        api.getMedicHolidays(id, null, null, null).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Holidays>> call, Response<List<Holidays>> response) {
                if (response.isSuccessful()) {

                    List<Holidays> appointmentsResponse = response.body();

                    ArrayList<LocalDate> date;

                    date = appointmentsResponse.stream().map(Holidays::getDate).collect(Collectors.toCollection(ArrayList::new));

                    holidays.setValue(date);

                } else {

                    System.out.println(response);

                }

                ready.setValue(true);

            }

            @Override
            public void onFailure(Call<List<Holidays>> call, Throwable t) {
                t.printStackTrace();
                ready.setValue(true);
            }
        });
         **/
    }


    public void loadAppointment(int id) {

        api.getTodayAppointments(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                loadingMutableLivedata.setValue(false);
                if (response.isSuccessful()) {

                    List<Appointment> appointmentsResponse = response.body();

                    ArrayList<LocalDateTime> a = appointmentsResponse.stream().map(appointment -> appointment.getDate()).collect(Collectors.toCollection(ArrayList::new));

                    System.out.println("Data collected: ");

                    System.out.println(Arrays.toString(a.toArray()));

                    ArrayList<LocalDateTime> s = parseDates(a);

                    System.out.println(Arrays.toString(s.toArray()));

                    avaibleDates.setValue( s );

                    listEmptyMutableLiveData.setValue(itemsArrayList.isEmpty());

                }

                listMutableLiveData.setValue(itemsArrayList);
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });

    }

    private ArrayList<LocalDateTime> parseDates(ArrayList<LocalDateTime> collect) {
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

    private ArrayList<LocalDateTime> getRealSlots(LocalDate date, ArrayList<LocalDateTime> occupedDates) {

        return getTodaySlots(date).stream().filter( localDateTime -> !occupedDates.contains(localDateTime)).collect(Collectors.toCollection(ArrayList::new));

    }

}