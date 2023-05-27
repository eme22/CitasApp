package com.eme22.citasApp.model.pojo;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.commons.Links;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class User implements Serializable {

    @Expose
    private String image;
    @Expose
    private String address;
    @Expose
    private String passwordHash;
    @Expose
    private List<Appointment> appointmentsById;
    @Expose
    private String phone;
    @Expose
    private String lastname2;
    @Expose
    private Links links;
    @Expose
    private int id;
    @Expose
    private String lastname1;
    @Expose
    private String name2;
    @Expose
    private String name1;
    @Expose
    private int age;
    @Expose
    private int dni;

}
