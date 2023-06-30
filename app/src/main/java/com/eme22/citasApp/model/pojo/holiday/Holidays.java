package com.eme22.citasApp.model.pojo.holiday;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Holidays implements Serializable {

    @Expose
    private LocalDate date;

}
