package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;

public interface IUserService {
	List<User> getAllUsers();
	User getUserById(Integer id);
	User addUser(User user);
	User updateUser(Integer id, User user);
	void deleteUser(Integer id);
}
