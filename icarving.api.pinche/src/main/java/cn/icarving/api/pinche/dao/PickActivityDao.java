package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.PickActivity;

@Repository
public class PickActivityDao extends BaseDao<PickActivity> {

	@Autowired
	private SessionFactory sessionFactory;

	public PickActivityDao() {
		super(PickActivity.class);
	}

	public List<PickActivity> findPickActivityByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PickActivity where ownerId = :owerId");
		query.setParameter("owerId", uid);
		@SuppressWarnings("unchecked")
		List<PickActivity> result = query.list();
		return result;
	}
	
	public List<PickActivity> findPickActivityAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PickActivity");
		@SuppressWarnings("unchecked")
		List<PickActivity> result = query.list();
		return result;
	}
}
