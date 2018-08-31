package com.gazorpazorp.common.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserPrincipal {

	private Long userId;
	private Long customerId;
	private Long driverId;
	private String email;
}
