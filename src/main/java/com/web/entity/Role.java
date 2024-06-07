package com.web.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	
	@Id
	private String roleName;
	private String roleDiscription;

}
