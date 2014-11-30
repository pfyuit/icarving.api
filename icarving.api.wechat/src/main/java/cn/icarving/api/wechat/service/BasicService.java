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
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cn.icarving.api.wechat.message.basic.GetAccessTokenResponse;
import cn.icarving.api.wechat.message.user.GetServerIPResponse;

public class BasicService {
	
	@Autowired
	private NetworkService networkService;

	public GetAccessTokenResponse getAccessToken(String grantType, String appid, String secret) {
		GetAccessTokenResponse result = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("grant_type", grantType));
			params.add(new BasicNameValuePair("openid", grantType));
			params.add(new BasicNameValuePair("secret", grantType));
			HttpResponse response = networkService.get(NetworkService.SYSTEM_BASIC_TOKEN, params);
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}

			result = objectMapper.readValue(sb.toString(), GetAccessTokenResponse.class);
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
			HttpResponse response = networkService.get(NetworkService.SYSTSEM_BASIC_SERVER_IP, params);
			
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}
			result = objectMapper.readValue(sb.toString(), GetServerIPResponse.class);
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
