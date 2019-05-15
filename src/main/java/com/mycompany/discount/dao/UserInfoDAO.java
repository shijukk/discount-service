package com.mycompany.discount.dao;

import org.springframework.stereotype.Repository;

import com.mycompany.discount.dto.UserInfoDTO;

/**
 * 
 * @author kkshi
 *
 */
@Repository
public interface UserInfoDAO {
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	 UserInfoDTO getUserInfo(String userId);
}
