package service.impl;

import java.util.List;

import com.google.inject.Inject;

import dao.UserDAO;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {

	@Inject
	UserDAO userDAO;

	@Override
	public void registerUser(User user) throws Exception {
		List<User> existingUser = userDAO.getUsers(user.getUserName());
		if (existingUser != null && existingUser.size()>0) {
			throw new Exception("UserName Already exists");

		} else {
			userDAO.create(user);
		}

	}

}
