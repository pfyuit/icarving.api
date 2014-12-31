package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.UserMessage;

@Repository
public class UserMessageDao extends BaseDao<UserMessage> {

	@Autowired
	private SessionFactory sessionFactory;

	public UserMessageDao() {
		super(UserMessage.class);
	}

	public List<UserMessage> findAllMessagesByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserMessage WHERE toUid = :toUid");
		query.setParameter("toUid", uid);
		@SuppressWarnings("unchecked")
		List<UserMessage> result = query.list();
		return result;
	}

	public List<UserMessage> findUnreadMessagesByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserMessage WHERE toUid = :toUid AND status = 0");
		query.setParameter("toUid", uid);
		@SuppressWarnings("unchecked")
		List<UserMessage> result = query.list();
		return result;
	}

	public List<UserMessage> findReadMessagesByUser(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserMessage WHERE toUid = :toUid AND status = 1");
		query.setParameter("toUid", uid);
		@SuppressWarnings("unchecked")
		List<UserMessage> result = query.list();
		return result;
	}
}
