package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.PickActivityApply;

@Repository
public class PickActivityApplyDao extends BaseDao<PickActivityApply> {

	@Autowired
	private SessionFactory sessionFactory;

	public PickActivityApplyDao() {
		super(PickActivityApply.class);
	}

	public List<PickActivityApply> findAll() {
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		Query query = session.createQuery("from PickActivityApply");
		@SuppressWarnings("unchecked")
		List<PickActivityApply> result = query.list();
		session.getTransaction().commit();
		session.close();
		return result;
	}
}
