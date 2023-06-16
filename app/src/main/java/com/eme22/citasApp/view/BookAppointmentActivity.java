package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_MEDIC;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;
import static com.eme22.citasApp.view.DoctorListActivity.sendAppointmentDate;
import static com.eme22.citasApp.view.DoctorListActivity.sendMedic;
import static com.eme22.citasApp.view.DoctorListActivity.sendUser;
import static com.eme22.citasApp.view.HistoryActivity.sendAppointment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eme22.citasApp.adapter.AppointmentsRecyclerViewAdapter;
import com.eme22.citasApp.adapter.MedicRecyclerViewAdapter;
import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.util.CustomDatePickerDialog;
import com.eme22.citasApp.util.Pair;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BookAppointmentActivity extends AppCompatActivity {

    BookAppointmentViewModel bookAppointmentViewModel;

    private ActivityBookAppointmentBinding binding;

    private AppointmentsRecyclerViewAdapter recyclerViewAdapter;

    private Integer oldposition;
    private Patient user;
    private Medic medic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookAppointmentBinding.inflate(LayoutInflater.from(this));

        bookAppointmentViewModel = new ViewModelProvider(this).get(BookAppointmentViewModel.class);

        setContentView(binding.getRoot());

        recyclerViewAdapter = new AppointmentsRecyclerViewAdapter(onItemClicked);

        binding.drAppPlace2.setAdapter(recyclerViewAdapter);

        user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        medic = (Medic) getIntent().getSerializableExtra(EXTRA_MEDIC);

        bookAppointmentViewModel.init(medic.getId());

        bookAppointmentViewModel.getReady().observe(this, aBoolean -> {
            if (aBoolean) {
                initData();
            }
        });

    }

    private void initData() {

        binding.drAppDate.setOnClickListener(view -> {

            MaterialDatePicker<Long> picker = MaterialDatePicker
                    .Builder
                    .datePicker()
                    .setCalendarConstraints(disableExcluded(bookAppointmentViewModel.getHolidays().getValue()).build())
                    .setTitleText("Seleccione la fecha")
                    .build();

            picker.addOnPositiveButtonClickListener(selection -> {

                if (bookAppointmentViewModel.getDate().getValue() == null || !bookAppointmentViewModel.getDate().isInitialized())
                    bookAppointmentViewModel.getDate().setValue(Calendar.getInstance());

                bookAppointmentViewModel.getDate().getValue().setTimeInMillis(selection);

                updateLabel();
                loadData();

            });
            picker.show(getSupportFragmentManager(), picker.toString());
        });

        binding.drAppDate.addTextChangedListener(afterTextChangedListener);

        binding.drAppDate.setFocusable(false);
        binding.drAppDate.setClickable(true);

    }

    private void loadData() {

        Medic medic = (Medic) getIntent().getSerializableExtra(EXTRA_MEDIC);

        bookAppointmentViewModel.loadAppointment(medic.getId());


        binding.drAppBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProcessAppointmentActivity.class);
            Appointment appointment = generateAppointment();
            sendAppointment(appointment, intent);
            startActivity(intent);
        });


        bookAppointmentViewModel.getAvaibleDates().observe(this, localDateTimes -> {

            if (!localDateTimes.isEmpty()) {
                System.out.println("cambiando dataset a"+ Arrays.toString(localDateTimes.toArray()));

                recyclerViewAdapter.updateAppointmentList(localDateTimes);

                runOnUiThread(() ->  recyclerViewAdapter.notifyDataSetChanged());
            }

        });

        bookAppointmentViewModel.getSelectedDate().observe(this, localDateTime -> binding.drAppBtn.setEnabled(localDateTime != null));

    }

    private Appointment generateAppointment() {

        Appointment appointment = new Appointment();

        appointment.setDate(bookAppointmentViewModel.getSelectedDate().getValue());

        appointment.setDescription(binding.remarks.getText().toString());

        appointment.setMedicByMedicId(medic);

        appointment.setPatientByPatientId(user);

        appointment.setFinished(false);

        return appointment;
    }

    TextWatcher afterTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // ignore
        }

        @Override
        public void afterTextChanged(Editable s) {
            //bookAppointmentViewModel.datePickerDateChanged(binding.drAppDate.getText().toString());
        }
    };

    AppointmentsRecyclerViewAdapter.OnItemClicked onItemClicked = new AppointmentsRecyclerViewAdapter.OnItemClicked() {
        @Override
        public void onItemClick(int position, LocalDateTime cartItem, View current) {

            if (oldposition != null) {
                recyclerViewAdapter.getAppointmentArrayList().get(oldposition).second.second = false;

            }

            oldposition = position;

            recyclerViewAdapter.getAppointmentArrayList().get(position).second.second = true;


            bookAppointmentViewModel.getSelectedDate().setValue(cartItem);

            recyclerViewAdapter.updateAppointmentList(recyclerViewAdapter.getAppointmentArrayList());

            runOnUiThread(() ->  recyclerViewAdapter.notifyDataSetChanged());


        }
    };

    /**
    DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        updateLabel();
    };
    **/

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        binding.drAppDate.setText(dateFormat.format(bookAppointmentViewModel.getDate().getValue().getTime()));
    }

    private void setHorary(boolean enabled){
        runOnUiThread(() -> {
            binding.drAppHourPickerLabel.setVisibility(enabled ? View.VISIBLE : View.GONE);
            binding.drAppPlace2.setVisibility(enabled ? View.VISIBLE : View.GONE);
        });
    }

    private CalendarConstraints.Builder disableExcluded(ArrayList<LocalDate> arrayList) {
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        if (bookAppointmentViewModel.getDate().getValue() == null || !bookAppointmentViewModel.getDate().isInitialized())
            bookAppointmentViewModel.getDate().setValue(Calendar.getInstance());

        constraintsBuilderRange.setValidator(new CustomDatePickerDialog(bookAppointmentViewModel.getDate().getValue().get(Calendar.YEAR),bookAppointmentViewModel.getDate().getValue().get(Calendar.MONTH),bookAppointmentViewModel.getDate().getValue().get(Calendar.DAY_OF_MONTH), arrayList));
        return constraintsBuilderRange;
    }

    private ArrayList<LocalDateTime> getTodaySlots(LocalDate date) {

        ArrayList<LocalDateTime> horas = new ArrayList<>();

        LocalTime horaInicial = LocalTime.of(0, 0); // Hora inicial: 00:00
        LocalTime horaFinal = LocalTime.of(18, 0); // Hora final: 23:59
        LocalDateTime dateTime = date.atTime(horaInicial);

        while (dateTime.toLocalTime().isBefore(horaFinal)) {
            horas.add(dateTime);
            dateTime = dateTime.plusMinutes(15);
        }

        return horas;

    }

    private ArrayList<Pair<LocalDateTime, Boolean>> getRealSlots(LocalDate date, ArrayList<LocalDateTime> occupedDates) {

        return getTodaySlots(date).stream().map(localDateTime -> new Pair<>(localDateTime, !occupedDates.contains(localDateTime))).collect(Collectors.toCollection(ArrayList::new));

    }
}