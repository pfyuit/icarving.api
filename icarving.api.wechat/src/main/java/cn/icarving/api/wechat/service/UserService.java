package cn.icarving.api.wechat.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cn.icarving.api.wechat.message.user.CreateGroupRequest;
import cn.icarving.api.wechat.message.user.CreateGroupResponse;
import cn.icarving.api.wechat.message.user.FindGroupByUserRequest;
import cn.icarving.api.wechat.message.user.FindGroupByUserResponse;
import cn.icarving.api.wechat.message.user.FindGroupResponse;
import cn.icarving.api.wechat.message.user.FindUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupResponse;
import cn.icarving.api.wechat.message.user.UpdateNoteRequest;
import cn.icarving.api.wechat.message.user.UpdateNoteResponse;

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
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), CreateGroupResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public FindGroupResponse findGroup() {
		FindGroupResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			HttpResponse response = networkService.get(NetworkService.USER_GROUP_FIND, null);
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), FindGroupResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public FindGroupByUserResponse findGroupByUser(FindGroupByUserRequest request) {
		FindGroupByUserResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(request);
			LOGGER.info("JSON to find group by user: " + json);

			HttpResponse response = networkService.post(NetworkService.USER_GROUP_FIND_BYUSER, json);
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), FindGroupByUserResponse.class);
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
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), UpdateGroupResponse.class);
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
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), UpdateGroupByUserResponse.class);
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
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), UpdateNoteResponse.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public FindUserResponse findUser(String openid, String lang) {
		FindUserResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("openid", openid));
			params.add(new BasicNameValuePair("lang", lang));
			HttpResponse response = networkService.get(NetworkService.USER_INFO_FIND, params);
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), FindUserResponse.class);
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
