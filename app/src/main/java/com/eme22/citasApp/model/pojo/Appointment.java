package com.eme22.citasApp.model.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class Appointment {

    private Date date;
    private Patient patient;
}
