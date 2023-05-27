package com.eme22.citasApp.model.pojo.medics;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MedicsResponse{
	@Expose
	@SerializedName("_embedded")
	private Embedded embedded;
	@Expose
	@SerializedName("_links")
	private Links links;
}