package com.eme22.citasApp.model.pojo.areas;

import com.google.gson.annotations.Expose;

import java.util.List;
import lombok.Data;

@Data
public class Embedded{
	@Expose
	private List<Area> areas;
}