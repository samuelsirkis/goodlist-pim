package com.goodlist.domain.models;

import javax.persistence.*;

import com.goodlist.domain.enums.Role;

@Entity
@Table(name = "roles")
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Role name;

	public UserRole() {

	}

	public UserRole(Role name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getName() {
		return name;
	}

	public void setName(Role name) {
		this.name = name;
	}
}