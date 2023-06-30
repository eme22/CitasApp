package com.eme22.citasApp.model.pojo.areas;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Area implements Serializable {
	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private int id;
}