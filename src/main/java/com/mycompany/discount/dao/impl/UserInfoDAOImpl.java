package com.mycompany.discount.dao.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.discount.dao.UserInfoDAO;
import com.mycompany.discount.dto.UserInfoDTO;
import com.mycompany.discount.dto.UserListDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author kkshi
 *
 */
@Repository
@Slf4j
public class UserInfoDAOImpl implements UserInfoDAO {

	private static List<UserInfoDTO> users;

	public UserInfoDAOImpl() {
		if (null == users || users.isEmpty()) {
			initialiseRepo();
		}
	}

	private static void initialiseRepo() {
		try {
			try (InputStream is = UserListDTO.class.getResourceAsStream("/userDetails.json")) {
				UserListDTO userlist = new ObjectMapper().readValue(is, UserListDTO.class);
				log.info(userlist.getUsers().toString());
				users = userlist.getUsers();
			}
		} catch (Exception ex) {
			log.error("error while reading user list file", ex);
		}
	}

	/**
	 * 
	 */
	@Override
	public UserInfoDTO getUserInfo(final String userId) {
		if (null == users || users.isEmpty()) {
			initialiseRepo();
		}
		return users.stream().filter(user -> userId.equalsIgnoreCase(user.getUserId())).findFirst().orElse(null);
	}

}
