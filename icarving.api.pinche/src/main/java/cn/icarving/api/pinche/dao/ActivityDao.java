package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.Activity;

@Repository
public class ActivityDao extends BaseDao<Activity> {

	@Autowired
	private SessionFactory sessionFactory;

	public ActivityDao() {
		super(Activity.class);
	}

	public List<Activity> findActivityByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Activity where ownerId = :owerId");
		query.setParameter("owerId", uid);
		@SuppressWarnings("unchecked")
		List<Activity> result = query.list();
		return result;
	}
	
	public List<Activity> findActivityAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Activity");
		@SuppressWarnings("unchecked")
		List<Activity> result = query.list();
		return result;
	}
}
