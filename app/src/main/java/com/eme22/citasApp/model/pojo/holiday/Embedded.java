package com.eme22.citasApp.model.pojo.holiday;

import com.eme22.citasApp.model.pojo.medics.Medic;
import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Data;

@Data
public class Embedded {
	@Expose
	private List<Holidays> holidays;
}