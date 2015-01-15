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
import cn.icarving.api.pinche.domain.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService {

	@Autowired
	private MessageDao userMessageDao;

	@Autowired
	private UserService userService;

	public Message createUserMessage(int messageType, int activityId, String activitySourceAddress, String activityDestAddress, int applyId, int fromUid, int toUid, String toName,
			String content, int isReply) {
		User user = userService.findUserByUid(fromUid);
		Message msg = new Message();
		msg.setMessageType(messageType);
		msg.setActivityId(activityId);
		msg.setActivitySourceAddress(activitySourceAddress);
		msg.setActivityDestAddress(activityDestAddress);
		msg.setApplyId(applyId);
		msg.setFromUid(fromUid);
		msg.setFromName(user == null ? "" : user.getName());
		msg.setToUid(toUid);
		msg.setToName(toName);
		msg.setContent(content);
		msg.setStatus(ApiMessage.MESSAGE_STATUS_UNREAD);
		msg.setIsReply(isReply);
		msg.setCreateTime(new Timestamp(new Date().getTime()));
		msg.setLastModify(new Timestamp(new Date().getTime()));

		userMessageDao.save(msg);
		return msg;
	}

	public List<Message> findAllMessagesByUser(int uid) {
		return userMessageDao.findAllMessagesByUser(uid);
	}

	public List<Message> findAllMessagesByActivity(int activityId) {
		return userMessageDao.findAllMessagesByActivity(activityId);
	}

	public Message readUserMessage(int userMessageId) {
		Message msg = userMessageDao.find(userMessageId);
		if (msg == null) {
			throw new ApiException(ApiEnum.MESSAGE_CANNOT_FIND.getCode(), ApiEnum.MESSAGE_CANNOT_FIND.getMessage());
		}
		if (msg.getStatus() == ApiMessage.MESSAGE_STATUS_READ) {
			throw new ApiException(ApiEnum.MESSAGE_ALREADY_READ.getCode(), ApiEnum.MESSAGE_ALREADY_READ.getMessage());
		}
		msg.setStatus(ApiMessage.MESSAGE_STATUS_READ);
		userMessageDao.update(msg);
		return msg;
	}

}
