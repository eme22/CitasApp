package com.eme22.citasApp.model.pojo.commons;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class AdditionalProp1{
	@Expose
	private String hreflang;
	@Expose
	private String profile;
	@Expose
	private String name;
	@Expose
	private Rel rel;
	@Expose
	private String href;
	@Expose
	private String media;
	@Expose
	private String deprecation;
	@Expose
	private String title;
	@Expose
	private String type;
}