package com.eme22.citasApp.model.pojo.appointments;

import com.eme22.citasApp.model.pojo.commons.Links;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AppointmentsResponse{
	@Expose
	@SerializedName("_embedded")
	private Embedded embedded;
	@Expose
	@SerializedName("_links")
	private Links links;
}