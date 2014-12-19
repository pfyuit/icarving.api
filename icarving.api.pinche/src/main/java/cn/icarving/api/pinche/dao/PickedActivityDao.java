package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.PickedActivity;

@Repository
public class PickedActivityDao extends BaseDao<PickedActivity> {

	@Autowired
	private SessionFactory sessionFactory;

	public PickedActivityDao() {
		super(PickedActivity.class);
	}

	public List<PickedActivity> findPickedActivityByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PickedActivity where ownerId = :owerId");
		query.setParameter("owerId", uid);
		@SuppressWarnings("unchecked")
		List<PickedActivity> result = query.list();
		return result;
	}
	
	public List<PickedActivity> findPickedActivityAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PickedActivity");
		@SuppressWarnings("unchecked")
		List<PickedActivity> result = query.list();
		return result;
	}
}
