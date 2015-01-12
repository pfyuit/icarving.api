package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.Apply;

@Repository
public class ApplyDao extends BaseDao<Apply> {

	@Autowired
	private SessionFactory sessionFactory;

	public ApplyDao() {
		super(Apply.class);
	}

	public List<Apply> findApplyByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Apply where ownerId = :ownerId");
		query.setParameter("ownerId", uid);
		@SuppressWarnings("unchecked")
		List<Apply> result = query.list();
		return result;
	}

	public List<Apply> findApplyByActivity(int activityId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Apply where activityId = :activityId");
		query.setParameter("activityId", activityId);
		@SuppressWarnings("unchecked")
		List<Apply> result = query.list();
		return result;
	}
}
