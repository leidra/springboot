package net.leidra.restservices.dtos;

import java.io.Serializable;

public class CustomerDto implements Serializable {
	private static final long serialVersionUID = -7724986820895780831L;
	
	private Long id;
	private String name;

	public CustomerDto() {
		super();
	}

	public CustomerDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}