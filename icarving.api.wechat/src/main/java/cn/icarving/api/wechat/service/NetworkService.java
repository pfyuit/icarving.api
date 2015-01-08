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

	public static String GLOBAL_API_ACCESS_TOKEN = "";
	
	public static final String WECHAT_PUBLIC_APP_ID = "APPID";
	public static final String WECHAT_PUBLIC_APP_SECRET = "APPSECRET";
	public static final String WECHAT_PINCHE_REGISTER_OR_LOGIN = "http://127.0.0.1:8080/icarving.api.pinche/user/wechatRegisterOrLogin";

	public static final String CUSTOMER_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
	public static final String CUSTOMER_SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send";

	public static final String CUSTOMER_BROADCAST_ALL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	public static final String CUSTOMER_BROADCAST_GROUP = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	public static final String CUSTOMER_BROADCAST_OPENID = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
	public static final String CUSTOMER_BROADCAST_DELETE = "https://api.weixin.qq.com/cgi-bin/message/mass/delete";

	public static final String USER_GROUP_CREATE = "https://api.weixin.qq.com/cgi-bin/groups/create";
	public static final String USER_GROUP_GET = "https://api.weixin.qq.com/cgi-bin/groups/get";
	public static final String USER_GROUP_GET_BYUSER = "https://api.weixin.qq.com/cgi-bin/groups/getid";
	public static final String USER_GROUP_UPDATE = "https://api.weixin.qq.com/cgi-bin/groups/update";
	public static final String USER_GROUP_UPDATE_BYUSER = "https://api.weixin.qq.com/cgi-bin/groups/members/update";

	public static final String USER_NOTE_UPDATE = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";

	public static final String USER_INFO_GET = "https://api.weixin.qq.com/cgi-bin/user/info";

	public static final String USER_SUBSCRIBES_GET = "https://api.weixin.qq.com/cgi-bin/user/get";

	public static final String AUTH_USER_ACCESS_TOKEN_GET = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public static final String AUTH_USER_ACCESS_TOKEN_REFRESH = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	public static final String AUTH_USER_ACCESS_TOKEN_VALIDATE = "https://api.weixin.qq.com/sns/auth";
	public static final String AUTH_USER_INFO_GET = "https://api.weixin.qq.com/sns/userinfo";

	public static final String SYSTEM_BASIC_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	public static final String SYSTEM_BASIC_SERVER_IP = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
	
	private static Logger LOGGER = Logger.getLogger(NetworkService.class);

	@SuppressWarnings({ "deprecation", "resource" })
	public HttpResponse get(final String endpoint, List<NameValuePair> params) {
		HttpGet httpGet = new HttpGet(endpoint + ((params != null && !params.isEmpty()) ? "?" + URLEncodedUtils.format(params, "UTF-8") : ""));

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
