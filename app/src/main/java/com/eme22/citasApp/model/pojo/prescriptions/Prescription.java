package com.eme22.citasApp.model.pojo.prescriptions;

import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.commons.Links;
import com.eme22.citasApp.model.pojo.medications.Medication;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

import lombok.Data;

@Data
public class Prescription implements Serializable {
	@Expose
	private Appointment appointmentByAppointmentId;
	@Expose
	private Links links;
	@Expose
	private int id;
	@Expose
	private Medication medicationByMedicationId;
}