package cn.icarving.api.pinche.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.PickActivity;

import com.google.common.base.Strings;

@Repository
public class SearchPickActivityDao extends BaseDao<PickActivity> {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<PickActivity> searchPickActivity(Timestamp startTime, Timestamp returnTime, String sourceAddress, String destAddress) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuilder sb = new StringBuilder("SELECT * FROM icarving_pinche.pick_activity WHERE ");
		if (!Strings.isNullOrEmpty(sourceAddress)) {
			sb.append(" source_address LIKE ").append("'%").append(sourceAddress.trim()).append("%' ");
		}
		
		if (!Strings.isNullOrEmpty(destAddress)) {
			sb.append(" AND dest_address LIKE ").append("'%").append(destAddress.trim()).append("%' ");
		}
		if (startTime != null) {
			sb.append(" AND start_time LIKE ").append("'").append(new SimpleDateFormat("yyyy-MM-dd").format(startTime)).append("%' ");
		}
		if (returnTime != null) {
			sb.append(" AND return_time LIKE ").append("'").append(new SimpleDateFormat("yyyy-MM-dd").format(returnTime)).append("' ");
		}

		sb.append(";");

		Query query = session.createSQLQuery(sb.toString()).addEntity(PickActivity.class);
		List<PickActivity> result = query.list();
		return result;
	}
}
