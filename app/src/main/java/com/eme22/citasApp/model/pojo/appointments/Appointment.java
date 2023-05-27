package com.eme22.citasApp.model.pojo.appointments;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.model.pojo.prescriptions.Prescription;
import com.google.gson.annotations.Expose;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Appointment{
	@Expose
	private String date;
	@Expose
	private String result;
	@Expose
	private List<Prescription> prescriptionsById;
	@Expose
	private String disease;
	@Expose
	private Medic medicByMedicId;
	@Expose
	private Patient patientByPatientId;
	@Expose
	private String description;
	@Expose
	private boolean finished;
	@Expose
	private Links links;
	@Expose
	private int id;
}