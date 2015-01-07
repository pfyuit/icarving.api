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

import cn.icarving.api.wechat.message.basic.GetAccessTokenResponse;
import cn.icarving.api.wechat.message.user.GetServerIPResponse;

@Service
public class BasicService {

	private static Logger LOGGER = Logger.getLogger(BasicService.class);

	@Autowired
	private NetworkService networkService;

	public GetAccessTokenResponse getAccessToken(String grantType, String appid, String secret) {
		GetAccessTokenResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("grant_type", grantType));
			params.add(new BasicNameValuePair("appid", appid));
			params.add(new BasicNameValuePair("secret", secret));
			HttpResponse response = networkService.get(NetworkService.SYSTEM_BASIC_TOKEN, params);
			HttpEntity entity = response.getEntity();
			String res = EntityUtils.toString(entity);
			LOGGER.info("GetResponseString: " + res);
			result = objectMapper.readValue(res, GetAccessTokenResponse.class);
			NetworkService.GLOBAL_API_ACCESS_TOKEN = result.getAccess_token();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public GetServerIPResponse getServerIP() {
		GetServerIPResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			HttpResponse response = networkService.get(NetworkService.SYSTEM_BASIC_SERVER_IP + "?access_token=" + NetworkService.GLOBAL_API_ACCESS_TOKEN, params);
			HttpEntity entity = response.getEntity();
			String res = EntityUtils.toString(entity);
			LOGGER.info("GetResponseString: " + res);
			result = objectMapper.readValue(res, GetServerIPResponse.class);
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
