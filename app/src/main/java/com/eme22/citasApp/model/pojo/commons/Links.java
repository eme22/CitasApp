package com.eme22.citasApp.model.pojo.commons;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class Links{
	@Expose
	private AdditionalProp1 additionalProp1;
	@Expose
	private AdditionalProp3 additionalProp3;
	@Expose
	private AdditionalProp2 additionalProp2;
	@Expose
	private boolean empty;
}