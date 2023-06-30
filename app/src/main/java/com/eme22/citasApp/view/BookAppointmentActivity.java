package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_MEDIC;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;
import static com.eme22.citasApp.view.DoctorListActivity.sendUser;
import static com.eme22.citasApp.view.HistoryActivity.sendAppointment;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.eme22.citasApp.databinding.ActivityBookAppointmentBinding;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.util.CustomDatePickerDialog;
import com.eme22.citasApp.util.Pair;
import com.eme22.citasApp.viewmodel.BookAppointmentViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

public class BookAppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    BookAppointmentViewModel bookAppointmentViewModel;

    DatePickerDialog datePickerDialog;

    private ActivityBookAppointmentBinding binding;

    private Patient user;
    private Medic medic;
    private ArrayAdapter<TimeWrapper> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookAppointmentBinding.inflate(LayoutInflater.from(this));

        bookAppointmentViewModel = new ViewModelProvider(this).get(BookAppointmentViewModel.class);

        setContentView(binding.getRoot());

        user = (Patient) getIntent().getSerializableExtra(EXTRA_USER);

        medic = (Medic) getIntent().getSerializableExtra(EXTRA_MEDIC);

        bookAppointmentViewModel.init(medic);

        bookAppointmentViewModel.getReady().observe(this, aBoolean -> {
            if (aBoolean) {
                initData();
            }
        });

        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bookAppointmentViewModel.getSelectedDate().setValue(adapter.getItem(position).getDate());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.drAppName.setText(medic.getName1()+" "+medic.getLastname1());
        binding.drAppExp.setText(String.valueOf(medic.getAge()));
        binding.drAppDegree.setText(medic.getSpecialityBySpecId().getName());
        if (medic.getImage() != null) {
            try {
                binding.drAppImg.setImageURI(Uri.parse(new URL(medic.getImage()).toURI().toString()));
            } catch (URISyntaxException | MalformedURLException ignored) { }
        }

    }

    private void initData() {

        binding.drAppDate.setOnClickListener(view -> {

            Calendar date = Calendar.getInstance();

            datePickerDialog = DatePickerDialog.newInstance(BookAppointmentActivity.this, date);
            datePickerDialog.setThemeDark(false);
            datePickerDialog.showYearPickerFirst(false);
            datePickerDialog.setTitle("Seleccione la fecha");


            // Setting Min Date to today date
            Calendar min_date_c = Calendar.getInstance();
            datePickerDialog.setMinDate(min_date_c);



            // Setting Max Date to next 2 years
            Calendar max_date_c = Calendar.getInstance();
            max_date_c.set(Calendar.YEAR, max_date_c.get(Calendar.YEAR)+2);
            datePickerDialog.setMaxDate(max_date_c);


            for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                LocalDate localDate = LocalDateTime.ofInstant(loopdate.toInstant(), loopdate.getTimeZone().toZoneId()).toLocalDate();

                if (medic.getHolidays().stream().anyMatch(date2 -> date2.getDate().equals(localDate))) {

                    Calendar[] disabledDays =  new Calendar[1];
                    disabledDays[0] = loopdate;
                    datePickerDialog.setDisabledDays(disabledDays);
                    continue;
                }

                int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SUNDAY) {
                    Calendar[] disabledDays =  new Calendar[1];
                    disabledDays[0] = loopdate;
                    datePickerDialog.setDisabledDays(disabledDays);
                }
            }


            datePickerDialog.setOnCancelListener(dialogInterface -> Toast.makeText(BookAppointmentActivity.this, "Cancelado", Toast.LENGTH_SHORT).show());

            datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
        });

        binding.drAppDate.addTextChangedListener(afterTextChangedListener);

        binding.drAppDate.setFocusable(false);
        binding.drAppDate.setClickable(true);

    }

    private void loadData() {

        bookAppointmentViewModel.loadAppointment(medic.getId());


        binding.drAppBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProcessAppointmentActivity.class);
            Appointment appointment = generateAppointment();

            System.out.println("Appointment: ["+ appointment +"]");

            sendAppointment(appointment, intent);
            sendUser(user, intent);
            startActivity(intent);
        });


        bookAppointmentViewModel.getAvaibleDates().observe(this, localDateTimes -> {

            if (!localDateTimes.isEmpty()) {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, localDateTimes.stream().map(TimeWrapper::new).collect(Collectors.toList()));
                binding.spinner1.setAdapter(adapter);
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

    private void updateLabel(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        binding.drAppDate.setText(dateFormat.format(bookAppointmentViewModel.getDate().getValue().getTime()));
    }

    private void setHorary(boolean enabled){
        runOnUiThread(() -> {
            binding.drAppHourPickerLabel.setVisibility(enabled ? View.VISIBLE : View.GONE);
            binding.spinner1.setVisibility(enabled ? View.VISIBLE : View.GONE);
        });
    }

    private CalendarConstraints.Builder disableExcluded(ArrayList<LocalDate> arrayList) {

        System.out.println("EXCLUDED DATES: "+ arrayList);

        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();

        if (bookAppointmentViewModel.getDate().getValue() == null || !bookAppointmentViewModel.getDate().isInitialized())
            bookAppointmentViewModel.getDate().setValue(Calendar.getInstance());

        constraintsBuilderRange.setValidator(
                new CustomDatePickerDialog(
                        bookAppointmentViewModel.getDate().getValue().get(Calendar.YEAR),
                        bookAppointmentViewModel.getDate().getValue().get(Calendar.MONTH),
                        bookAppointmentViewModel.getDate().getValue().get(Calendar.DAY_OF_MONTH),
                        arrayList));
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


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (bookAppointmentViewModel.getDate().getValue() == null || !bookAppointmentViewModel.getDate().isInitialized())
            bookAppointmentViewModel.getDate().setValue(Calendar.getInstance());

        bookAppointmentViewModel.getDate().getValue().set(year, monthOfYear, dayOfMonth);
        updateLabel();
        loadData();

    }

    @Getter
    @Setter
    private static class TimeWrapper {

        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        private LocalDateTime date;

        public TimeWrapper(LocalDateTime date) {
            this.date = date;
        }

        @NonNull
        @Override
        public String toString() {
            return formatter.format(date);
        }
    }
}