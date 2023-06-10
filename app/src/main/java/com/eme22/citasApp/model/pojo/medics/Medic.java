package com.eme22.citasApp.model.pojo.medics;

import com.eme22.citasApp.model.pojo.User;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.areas.Area;
import com.eme22.citasApp.model.pojo.commons.Links;
import com.eme22.citasApp.model.pojo.holiday.Holidays;
import com.eme22.citasApp.model.pojo.specialities.Speciality;
import com.google.gson.annotations.Expose;

import java.util.List;
import lombok.Data;

@Data
public class Medic extends User {
	@Expose
	private Area areaByAreaId;
	@Expose
	private Speciality specialityBySpecId;
	@Expose
	private List<Holidays> holidays;
}