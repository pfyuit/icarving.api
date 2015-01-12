package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.common.ApiMessage;
import cn.icarving.api.pinche.dao.MessageDao;
import cn.icarving.api.pinche.domain.Message;

@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService {

	@Autowired
	private MessageDao userMessageDao;

	public void createUserMessage(int fromUid, int toUid, String content) {
		Message msg = new Message();
		msg.setFromUid(fromUid);
		msg.setToUid(toUid);
		msg.setContent(content);
		msg.setStatus(ApiMessage.MESSAGE_STATUS_UNREAD);
		msg.setCreateTime(new Timestamp(new Date().getTime()));
		msg.setLastModify(new Timestamp(new Date().getTime()));

		userMessageDao.save(msg);
	}

	public List<Message> findAllMessagesByUser(int uid) {
		return userMessageDao.findAllMessagesByUser(uid);
	}

	public void readUserMessage(int userMessageId) {
		Message msg = userMessageDao.find(userMessageId);
		if (msg == null) {
			throw new ApiException(ApiEnum.MESSAGE_CANNOT_FIND.getCode(), ApiEnum.MESSAGE_CANNOT_FIND.getMessage());
		}
		if (msg.getStatus() == ApiMessage.MESSAGE_STATUS_READ) {
			throw new ApiException(ApiEnum.MESSAGE_ALREADY_READ.getCode(), ApiEnum.MESSAGE_ALREADY_READ.getMessage());
		}
		msg.setStatus(ApiMessage.MESSAGE_STATUS_READ);
		userMessageDao.update(msg);
	}

}
