package cn.icarving.api.wechat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.icarving.api.wechat.message.user.CreateGroupRequest;
import cn.icarving.api.wechat.message.user.CreateGroupResponse;
import cn.icarving.api.wechat.message.user.FindGroupByUserRequest;
import cn.icarving.api.wechat.message.user.GetAuthUserInfoResponse;
import cn.icarving.api.wechat.message.user.GetGroupByUserResponse;
import cn.icarving.api.wechat.message.user.GetGroupResponse;
import cn.icarving.api.wechat.message.user.GetSubscribesResponse;
import cn.icarving.api.wechat.message.user.GetUserAccessTokenResponse;
import cn.icarving.api.wechat.message.user.GetUserInfoResponse;
import cn.icarving.api.wechat.message.user.RefreshUserAccessTokenResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupResponse;
import cn.icarving.api.wechat.message.user.UpdateNoteRequest;
import cn.icarving.api.wechat.message.user.UpdateNoteResponse;
import cn.icarving.api.wechat.message.user.ValidateUserAccessTokenResponse;
import cn.icarving.api.wechat.message.user.WechatRegisterOrLoginRequest;
import cn.icarving.api.wechat.message.user.WechatRegisterOrLoginResponse;

@Service
public class UserService {

	private static Logger LOGGER = Logger.getLogger(MessageService.class);

	@Autowired
	private NetworkService networkService;

	public CreateGroupResponse createGroup(CreateGroupRequest request) {
		CreateGroupResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request);
			LOGGER.info("JSON to create group: " + json);
			HttpResponse response = networkService.post(NetworkService.USER_GROUP_CREATE, json);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), CreateGroupResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetGroupResponse getGroup() {
		GetGroupResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpResponse response = networkService.get(NetworkService.USER_GROUP_GET, null);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), GetGroupResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetGroupByUserResponse getGroupByUser(FindGroupByUserRequest request) {
		GetGroupByUserResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request);
			LOGGER.info("JSON to find group by user: " + json);
			HttpResponse response = networkService.post(NetworkService.USER_GROUP_GET_BYUSER, json);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), GetGroupByUserResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public UpdateGroupResponse updateGroup(UpdateGroupRequest request) {
		UpdateGroupResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request);
			LOGGER.info("JSON to update group: " + json);
			HttpResponse response = networkService.post(NetworkService.USER_GROUP_UPDATE, json);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), UpdateGroupResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public UpdateGroupByUserResponse updateGroupByUser(UpdateGroupByUserRequest request) {
		UpdateGroupByUserResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request);
			LOGGER.info("JSON to update group by user: " + json);
			HttpResponse response = networkService.post(NetworkService.USER_GROUP_UPDATE_BYUSER, json);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), UpdateGroupByUserResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public UpdateNoteResponse updateNote(UpdateNoteRequest request) {
		UpdateNoteResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request);
			LOGGER.info("JSON to update note: " + json);
			HttpResponse response = networkService.post(NetworkService.USER_NOTE_UPDATE, json);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), UpdateNoteResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetUserInfoResponse getUserInfo(String openid, String lang) {
		GetUserInfoResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("openid", openid));
			params.add(new BasicNameValuePair("lang", lang));
			HttpResponse response = networkService.get(NetworkService.USER_INFO_GET, params);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), GetUserInfoResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetSubscribesResponse getSubscribes(String nextOpenid) {
		GetSubscribesResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("next_openid ", nextOpenid));
			HttpResponse response = networkService.get(NetworkService.USER_SUBSCRIBES_GET, params);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), GetSubscribesResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetUserAccessTokenResponse getUserAccessToken(String appid, String secret, String code, String grantType) {
		GetUserAccessTokenResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("appid", appid));
			params.add(new BasicNameValuePair("secret", secret));
			params.add(new BasicNameValuePair("code", code));
			params.add(new BasicNameValuePair("grant_type", grantType));
			HttpResponse response = networkService.get(NetworkService.AUTH_USER_ACCESS_TOKEN_GET, params);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), GetUserAccessTokenResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public RefreshUserAccessTokenResponse refreshUserAccessToken(String appid, String grantType, String refreshToken) {
		RefreshUserAccessTokenResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("appid ", appid));
			params.add(new BasicNameValuePair("grantType", grantType));
			params.add(new BasicNameValuePair("refresh_token", refreshToken));
			HttpResponse response = networkService.get(NetworkService.AUTH_USER_ACCESS_TOKEN_REFRESH, params);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), RefreshUserAccessTokenResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetAuthUserInfoResponse getAuthUserInfo(String accessToken, String openid, String lang) {
		GetAuthUserInfoResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			params.add(new BasicNameValuePair("openid", openid));
			params.add(new BasicNameValuePair("lang", lang));
			HttpResponse response = networkService.get(NetworkService.AUTH_USER_INFO_GET, params);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity, "UTF-8"), GetAuthUserInfoResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ValidateUserAccessTokenResponse validateUserAccessToken(String accessToken, String openid) {
		ValidateUserAccessTokenResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("access_token", accessToken));
			params.add(new BasicNameValuePair("openid", openid));
			HttpResponse response = networkService.get(NetworkService.AUTH_USER_ACCESS_TOKEN_VALIDATE, params);
			HttpEntity entity = response.getEntity();
			result = objectMapper.readValue(EntityUtils.toString(entity), ValidateUserAccessTokenResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public WechatRegisterOrLoginResponse registerOrLoginPincheUser(String unionid, String openid, String nickName) {
		WechatRegisterOrLoginResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			WechatRegisterOrLoginRequest request = new WechatRegisterOrLoginRequest();
			request.setUnionid(unionid);
			request.setOpenid(openid);
			request.setUsername(unionid);
			request.setPassword(unionid);
			request.setName(nickName);
			request.setPhone("");
			String payload = objectMapper.writeValueAsString(request);

			HttpResponse response = networkService.post(NetworkService.WECHAT_PINCHE_REGISTER_OR_LOGIN, payload);
			HttpEntity entity = response.getEntity();
			String res = EntityUtils.toString(entity, "UTF-8");
			LOGGER.info("GetResponseString: " + res);
			result = objectMapper.readValue(res, WechatRegisterOrLoginResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
}
