package com.eme22.citasApp.model.pojo.appointments;

import com.google.gson.annotations.Expose;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Embedded{
	@Expose
	private List<Appointment> appointments;
}