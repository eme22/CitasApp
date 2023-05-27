package com.eme22.citasApp.model.pojo.specialities;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Speciality implements Serializable {
	@Expose
	private List<Medic> medicsById;
	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private String image;
	@Expose
	private Links links;
	@Expose
	private int id;
}