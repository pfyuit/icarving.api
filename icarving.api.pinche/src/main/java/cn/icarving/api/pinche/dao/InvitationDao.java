package cn.icarving.api.pinche.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.Invitation;

@Repository
public class InvitationDao extends BaseDao<Invitation> {

	@Autowired
	private SessionFactory sessionFactory;

	public InvitationDao() {
		super(Invitation.class);
	}

	public List<Invitation> findByInvitationCode(int invitationCode) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Invitation where invitationCode = :invitationCode");
		query.setParameter("invitationCode", invitationCode);
		@SuppressWarnings("unchecked")
		List<Invitation> result = query.list();
		return result;
	}
}
