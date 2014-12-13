package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.User;

@Repository
public class UserDao extends BaseDao<User> {

	@Autowired
	private SessionFactory sessionFactory;

	public UserDao() {
		super(User.class);
	}

	public User findByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM User WHERE username = :username");
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<User> result = query.list();
		return result.isEmpty()? null : result.get(0);
	}
}
