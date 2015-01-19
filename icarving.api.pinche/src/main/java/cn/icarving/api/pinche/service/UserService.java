package cn.icarving.api.pinche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.dao.UserDao;
import cn.icarving.api.pinche.domain.User;
import cn.icarving.api.pinche.dto.UserUpdateForm;

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

	public User updateUser(UserUpdateForm form) {
		User user = findUserByUid(form.getUid());
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}
		if (!user.getPassword().equals(form.getPassword())) {
			throw new ApiException(ApiEnum.USER_PASSWORD_NOT_MATCH.getCode(), ApiEnum.USER_PASSWORD_NOT_MATCH.getMessage());
		}
		if (!Strings.isNullOrEmpty(form.getName())) {
			user.setName(form.getName());
		}
		if (!Strings.isNullOrEmpty(form.getCountry())) {
			user.setCountry(form.getCountry());
		}
		if (!Strings.isNullOrEmpty(form.getProvince())) {
			user.setProvince(form.getProvince());
		}
		if (!Strings.isNullOrEmpty(form.getCity())) {
			user.setCity(form.getCity());
		}
		if (!Strings.isNullOrEmpty(form.getPhone())) {
			user.setPhone(form.getPhone());
		}
		userDao.update(user);
		return user;
	}

	public User findUserByUid(int uid) {
		return userDao.find(uid);
	}

	public User findUserByUserName(String username) {
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
