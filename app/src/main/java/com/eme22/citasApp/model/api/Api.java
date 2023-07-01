package com.eme22.citasApp.model.api;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;
import com.eme22.citasApp.model.pojo.holiday.Holidays;
import com.eme22.citasApp.model.pojo.holiday.HolidaysResponse;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.medics.MedicsResponse;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.model.pojo.patients.PatientsResponse;
import com.eme22.citasApp.model.pojo.prescriptions.Prescription;
import com.eme22.citasApp.model.pojo.specialities.SpecialitiesResponse;
import com.eme22.citasApp.model.pojo.specialities.Speciality;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/patient")
    Call<List<Patient>> getPatients();
    @GET("/patient/{id}")
    Call<List<Patient>> getPatientById(@Path("id") Long id);
    @GET("/patient/search/findByDni")
    Call<Patient> getPatientByDNI(@Query("dni") Integer dni);

    @GET("/appointment/search/findByPatientByPatientId_DniAndFinished")
    Call<List<Appointment>> getHistory(@Query("dni") Integer dni);

    @GET("/appointment/search/findByPatientByPatientId_DniAndNotFinished")
    Call<List<Appointment>> getActive(@Query("dni") Integer dni);

    @GET("/appointment/search/findByMedicByMedicId_Id")
    Call<List<Appointment>> getTodayAppointments(@Query("id") Integer id);

    @GET("/speciality")
    Call<List<Speciality>> getSpecialities();

    @GET("/medic/search/findBySpecialityBySpecId_Id")
    Call<List<Medic>> getMedicsBySpeciality(@Query("id") int id);

    @GET("/medic")
    Call<List<Medic>> getMedics();

    @GET("/medic/search/findByHolidays_DateIsNotIn")
    Call<List<Medic>> getMedicsByDate(@Query("date") LocalDate date);

    @GET("/holiday/search/findByMedic_Id")
    Call<List<Holidays>> getMedicHolidays(@Query("id") int id, @Query("page") Integer page, @Query("size") Integer size, @Query("sort")String sort);

    @POST("/appointment/")
    Call<Appointment> appointment(@Body Appointment appointment);

    @GET("/prescription/findByPrescrition_AppointmentId")
    Call<List<Prescription>> getAllPrescriptionByAppointment(@Query("date") int appointmentId);

}
