package http.util;

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtil {
	
	private static final CloseableHttpClient httpclient = HttpClients.createDefault();
	
	public static String sendGet(String url) {

		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String result = null;
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity);
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static JSONObject sendPost(String url,JSONObject json){
	    HttpPost post = new HttpPost(url);
	    JSONObject response = null;
	    try {
	      StringEntity s = new StringEntity(json.toString(),"utf-8");
	      s.setContentEncoding("UTF-8");
	      s.setContentType("application/json;charset=UTF-8");//发送json数据需要设置contentType
	      post.setEntity(s);
	      HttpResponse res = httpclient.execute(post);
	      if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	        //HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
	        response = new JSONObject(result);
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	    return response;
	  }
	
	public static JSONObject sendPostWithHeader(String url, JSONObject json, Map<String, String> headers){
	    HttpPost post = new HttpPost(url);
	    for(Map.Entry<String, String> header : headers.entrySet()) {
	    	post.addHeader(header.getKey(), header.getValue());
	    }
	    JSONObject response = null;
	    try {
	      StringEntity s = new StringEntity(json.toString(),"utf-8");
	      s.setContentEncoding("utf-8");
	      s.setContentType("application/json;charset=utf-8");//发送json数据需要设置contentType
	      post.setEntity(s);
	      HttpResponse res = httpclient.execute(post);
	      if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	        //HttpEntity entity = res.getEntity();
	        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
	        response = new JSONObject(result);
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	    return response;
	  }

}
