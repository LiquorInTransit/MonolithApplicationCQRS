package com.gazorpazorp.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "USER_ENTITY")
public class UserEntity {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", length = 36)
	private String id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email")
	private String email;
	private String phone;
	@Column(name = "password", length = 60)
	private String password;
	@Column(name = "roles", length = 50)
	private String roles;

	//Revocation details
	private boolean enabled;
	@Column(name = "non_expired")
	private boolean accountNonExpired;
	@Column(name = "non_locked")
	private boolean accountNonLocked;
	
	public UserEntity(String email, String password, String firstName, String lastName, String phone) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}	

}
