package cn.icarving.api.pinche.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.dao.InvitationDao;
import cn.icarving.api.pinche.domain.Invitation;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvitationService {

	@Autowired
	private InvitationDao invitationDao;

	public Invitation verifyInvitationCode(int invitationCode) {
		List<Invitation> invitations = invitationDao.findByInvitationCode(invitationCode);
		if (invitations == null || invitations.isEmpty()) {
			throw new ApiException(ApiEnum.INVITATION_CANNOT_FIND_INVITATION.getCode(), ApiEnum.INVITATION_CANNOT_FIND_INVITATION.getMessage());
		}
		Invitation invitation = invitations.get(0);
		if (invitation.getIsUsed() == 1) {
			throw new ApiException(ApiEnum.INVITATION_ALREADY_USED.getCode(), ApiEnum.INVITATION_ALREADY_USED.getMessage());
		}

		invitation.setIsUsed(1);
		invitation.setLastModify(new Timestamp(new Date().getTime()));
		invitationDao.update(invitation);
		return invitation;
	}

}
