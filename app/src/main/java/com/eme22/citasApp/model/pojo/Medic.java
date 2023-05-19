package com.eme22.citasApp.model.pojo;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Medic extends User {

    private String area;
    private String speciality;
    private List<Appointment> appointments;

}
