package com.mycompany.discount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author kkshi
 *
 */
@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
@NoArgsConstructor
public class UserInfoDTO {
	private String userId;
	private String userType;
	private int activeDuration;

}
