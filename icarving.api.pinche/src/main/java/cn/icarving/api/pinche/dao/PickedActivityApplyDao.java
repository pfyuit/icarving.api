package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.PickedActivityApply;

@Repository
public class PickedActivityApplyDao extends BaseDao<PickedActivityApply> {

	@Autowired
	private SessionFactory sessionFactory;

	public PickedActivityApplyDao() {
		super(PickedActivityApply.class);
	}

	public List<PickedActivityApply> findAll() {
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		Query query = session.createQuery("from PickedActivityApply");
		@SuppressWarnings("unchecked")
		List<PickedActivityApply> result = query.list();
		session.getTransaction().commit();
		session.close();
		return result;
	}
}
