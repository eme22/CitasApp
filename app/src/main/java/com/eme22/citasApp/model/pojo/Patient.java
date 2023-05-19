package com.eme22.citasApp.model.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Patient extends User{

    private List<History> historyList;

}
