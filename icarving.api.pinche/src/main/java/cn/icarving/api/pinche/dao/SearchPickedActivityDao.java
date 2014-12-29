package cn.icarving.api.pinche.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.icarving.api.pinche.domain.PickedActivity;

import com.google.common.base.Strings;

@Repository
public class SearchPickedActivityDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<PickedActivity> searchPickedActivity(Timestamp startTime, Timestamp returnTime, String sourceAddress, String destAddress) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sb = new StringBuilder("SELECT * FROM icarving_pinche.picked_activity WHERE ");
		if (startTime != null) {
			sb.append(" start_time LIKE ").append("'").append(new SimpleDateFormat("yyyy-MM-dd").format(startTime)).append("%' ");
		}
		if (returnTime != null) {
			sb.append(" AND return_time LIKE ").append("'").append(new SimpleDateFormat("yyyy-MM-dd").format(returnTime)).append("' ");
		}
		if (!Strings.isNullOrEmpty(sourceAddress)) {
			sb.append(" AND source_address LIKE ").append("'%").append(sourceAddress.trim()).append("%' ");
		}
		if (!Strings.isNullOrEmpty(destAddress)) {
			sb.append(" AND dest_address LIKE ").append("'%").append(destAddress.trim()).append("%' ");
		}
		sb.append(";");

		Query query = session.createSQLQuery(sb.toString()).addEntity(PickedActivity.class);
		List<PickedActivity> result = query.list();
		return result;
	}

}
