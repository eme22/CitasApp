package com.eme22.citasApp.model.api;

import com.eme22.citasApp.model.pojo.appointments.AppointmentsResponse;
import com.eme22.citasApp.model.pojo.holiday.Holidays;
import com.eme22.citasApp.model.pojo.holiday.HolidaysResponse;
import com.eme22.citasApp.model.pojo.medics.MedicsResponse;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.model.pojo.patients.PatientsResponse;
import com.eme22.citasApp.model.pojo.specialities.SpecialitiesResponse;

import java.time.LocalDate;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/patient")
    Call<PatientsResponse> getPatients(@Query("page") Integer page, @Query("size") Integer size, @Query("sort")String sort);
    @GET("/patient/{id}")
    Call<PatientsResponse> getPatientById(@Path("id") Long id);
    @GET("/patient/search/findByDni")
    Call<Patient> getPatientByDNI(@Query("dni") Integer dni);

    @GET("/appointment/search/findByPatientByPatientId_DniAndFinished?finished=true")
    Call<AppointmentsResponse> getHistory(@Query("dni") Integer dni);

    @GET("/appointment/search/findByMedicByMedicId_Id")
    Call<AppointmentsResponse> getTodayAppointments(@Query("id") Integer id);

    @GET("/speciality")
    Call<SpecialitiesResponse> getSpecialities(@Query("page") Integer page, @Query("size") Integer size, @Query("sort")String sort);

    @GET("/medic/search/findBySpecialityBySpecId_Id")
    Call<MedicsResponse> getMedicsBySpeciality(@Query("id") int id);

    @GET("/medic")
    Call<MedicsResponse> getMedics(@Query("page") Integer page, @Query("size") Integer size, @Query("sort")String sort);

    @GET("/medic/search/findByHolidays_DateIsNotIn")
    Call<MedicsResponse> getMedicsByDate(@Query("date") LocalDate date, @Query("page") Integer page, @Query("size") Integer size, @Query("sort")String sort);

    @GET("/holiday/search/findByMedic_Id")
    Call<HolidaysResponse> getMedicHolidays(@Query("id") int id, @Query("page") Integer page, @Query("size") Integer size, @Query("sort")String sort);

}
