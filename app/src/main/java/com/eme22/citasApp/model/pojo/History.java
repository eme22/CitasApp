package com.eme22.citasApp.model.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class History {

    private Date date;
    private String diagnosis;
    private String treatment;

}
