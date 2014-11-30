package cn.icarving.api.wechat.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NetworkService {

	public static final String CUSTOMER_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
	public static final String CUSTOMER_SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	
	public static final String CUSTOMER_BROADCAST_ALL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	public static final String CUSTOMER_BROADCAST_GROUP = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	public static final String CUSTOMER_BROADCAST_OPENID = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
	public static final String CUSTOMER_BROADCAST_DELETE = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";
	
	public static final String USER_GROUP_CREATE = "https://api.weixin.qq.com/cgi-bin/groups/create";
	public static final String USER_GROUP_FIND = "https://api.weixin.qq.com/cgi-bin/groups/get";
	public static final String USER_GROUP_FIND_BYUSER = "https://api.weixin.qq.com/cgi-bin/groups/getid";
	public static final String USER_GROUP_UPDATE = "https://api.weixin.qq.com/cgi-bin/groups/update";
	public static final String USER_GROUP_UPDATE_BYUSER = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
	
	public static final String USER_NOTE_UPDATE = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
	
	public static final String USER_INFO_FIND = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	public static final String USER_SUBSCRIBES_FIND = "https://api.weixin.qq.com/cgi-bin/user/get";
	
	public static final String SYSTEM_BASIC_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	public static final String SYSTSEM_BASIC_SERVER_IP = "https://api.weixin.qq.com/cgi-bin/getcallbackip";

	private static Logger LOGGER = Logger.getLogger(NetworkService.class);

	@SuppressWarnings({ "deprecation", "resource" })
	public HttpResponse get(final String endpoint, List<NameValuePair> params) {
		HttpGet httpGet = new HttpGet(endpoint + (params != null ? "?" + URLEncodedUtils.format(params, "UTF-8") : ""));

		LOGGER.info("Calling: " + httpGet.getRequestLine());
		LOGGER.info("RequestParams: " + params);

		HttpParams parameter = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameter, 5000);
		DefaultHttpClient httpclient = new DefaultHttpClient(parameter);

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		} catch (Exception e) {
		}

		LOGGER.info("GetResponseStatus: " + response.getStatusLine());
		return response;
	}

	@SuppressWarnings({ "deprecation", "resource" })
	public HttpResponse post(final String endpoint, final String payload) {
		HttpPost httpPost = new HttpPost(endpoint);
		BasicHttpEntity httpEntity = new BasicHttpEntity();
		httpEntity.setContent(new ByteArrayInputStream(payload.getBytes()));
		httpPost.setEntity(httpEntity);
		httpPost.setHeader("Content-type", "application/json");

		LOGGER.info("Calling: " + httpPost.getRequestLine());
		LOGGER.info("RequestBody: " + payload);

		HttpParams parameter = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameter, 5000);
		DefaultHttpClient httpclient = new DefaultHttpClient(parameter);

		HttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		} catch (Exception e) {
		}

		
		LOGGER.info("GetResponseStatus: " + response.getStatusLine());
		return response;
	}
}
