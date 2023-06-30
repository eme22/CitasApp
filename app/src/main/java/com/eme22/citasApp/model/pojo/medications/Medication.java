package com.eme22.citasApp.model.pojo.medications;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.eme22.citasApp.model.pojo.prescriptions.Prescription;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Medication implements Serializable {
	@Expose
	private int number;
	@Expose
	private List<Prescription> prescriptionsById;
	@Expose
	private String comments;
	@Expose
	private String name;
	@Expose
	private Links links;
	@Expose
	private int id;
}