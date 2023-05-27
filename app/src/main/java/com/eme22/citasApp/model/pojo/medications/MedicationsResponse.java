package com.eme22.citasApp.model.pojo.medications;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MedicationsResponse{
	@Expose
	@SerializedName("_embedded")
	private Embedded embedded;
	@Expose
	@SerializedName("_links")
	private Links links;
}