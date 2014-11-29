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

	public static final String SEND_CUSTOMER_ENDPOINT = "https://api.weixin.qq.com/cgi-bin/message/custom/send";

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
