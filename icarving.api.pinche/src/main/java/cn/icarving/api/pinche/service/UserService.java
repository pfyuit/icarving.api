package cn.icarving.api.pinche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;

	public void register(User user) {
		user.setLogin(true);
		userDao.save(user);
	}

	public void login(User user) {
		user.setLogin(true);
		userDao.update(user);
	}

	public void logoff(User user) {
		user.setLogin(false);
		userDao.update(user);
	}

	public void deleteUser(User user) {
		userDao.delete(user);
	}

	public void updateProfile(User user) {
		userDao.update(user);
	}

	public User findUser(String username) {
		User user = userDao.findByUserName(username);
		return user;
	}

}
