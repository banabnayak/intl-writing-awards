package com.scholastic.intl.writingawards.service;

import com.scholastic.intl.writingawards.entity.User;
import com.scholastic.intl.writingawards.model.AuthUserVO;

public interface UserService {

	public void addUser(User user);

	public User getUser(Long id);

	public void removeUser(User user);

	public AuthUserVO authenticate(AuthUserVO authUserVO);
	public Boolean checkRegistartionDate();

	public User resetPassword(String id, String email);
	public void storyActivities(AuthUserVO authUserVO);

}
