package com.eme22.citasApp.model.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User implements Serializable {

    private String dni;
    private String passwordHash;

    private String name1;
    private String name2;
    private String lastName1;
    private String lastName2;

    private String phone;
    private String address;
    private Integer age;
}
