package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.Message;

@Repository
public class MessageDao extends BaseDao<Message> {

	@Autowired
	private SessionFactory sessionFactory;

	public MessageDao() {
		super(Message.class);
	}

	public List<Message> findAllMessagesByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Message WHERE toUid = :toUid");
		query.setParameter("toUid", uid);
		@SuppressWarnings("unchecked")
		List<Message> result = query.list();
		return result;
	}
	
	public List<Message> findAllMessagesByActivity(int activityId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Message WHERE activityId = :activityId");
		query.setParameter("activityId", activityId);
		@SuppressWarnings("unchecked")
		List<Message> result = query.list();
		return result;
	}

	public List<Message> findUnreadMessagesByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Message WHERE toUid = :toUid AND status = 0");
		query.setParameter("toUid", uid);
		@SuppressWarnings("unchecked")
		List<Message> result = query.list();
		return result;
	}

	public List<Message> findReadMessagesByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Message WHERE toUid = :toUid AND status = 1");
		query.setParameter("toUid", uid);
		@SuppressWarnings("unchecked")
		List<Message> result = query.list();
		return result;
	}

}
