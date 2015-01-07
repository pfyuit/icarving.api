package cn.icarving.api.pinche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

	@Autowired
	private UserDao userDao;

	public void register(User user) {
		user.setLogin(1);
		userDao.save(user);
	}

	public void login(User user) {
		user.setLogin(1);
		userDao.update(user);
	}

	public void logoff(User user) {
		user.setLogin(0);
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

	public User findUserByOpenid(String openid) {
		User user = userDao.findByOpenid(openid);
		return user;
	}

	public User findUserByUnionid(String unionid) {
		User user = userDao.findByUnionid(unionid);
		return user;
	}

}
