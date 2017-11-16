package com.utils;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;


public class HttpHelper {
	private static Integer socketTimeout =  2 * 60000;
	private static Integer connectTimeout = 2 * 60000;
	private static Integer connectionRequestTimeout = 1 * 60000;

	/**
	 * 使用Get方式 根据URL地址，获取ResponseContent对象
	 * 
	 * @param url
	 *            完整的URL地址
	 * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
	 */
	public static ResponseContent getUrlRespContent(String url) {
		HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout,
				connectTimeout, socketTimeout);
		ResponseContent response = null;
		try {
			response = hw.getResponse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 使用Get方式 根据URL地址，获取ResponseContent对象
	 * a
	 * @param url
	 *            完整的URL地址
	 * @param urlEncoding
	 *            编码，可以为null
	 * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
	 */
	public static ResponseContent getUrlRespContent(String url,
			String urlEncoding) {
		HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout,
				connectTimeout, socketTimeout);
		ResponseContent response = null;
		try {
			response = hw.getResponse(url, urlEncoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	public static ResponseContent getUrlRespContent(String url,
			String urlEncoding,int retry) throws IOException {
		HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout,
				connectTimeout, socketTimeout);
		ResponseContent response = null;
		
		int lop=-1;
		while (response==null) {
			lop++;
			if (lop==retry) {
				throw new IOException(retry+"次请求失败");
			}
			try {
				response = hw.getResponse(url, urlEncoding);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	/**
	 * 将参数拼装在url中，进行post请求。
	 * 
	 * @param url
	 * @return
	 */
	public static ResponseContent postUrl(String url) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			setParams(url, hw);
			ret = hw.postNV(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static void setParams(String url, HttpClientWrapper hw) {
		String[] paramStr = url.split("[?]", 2);
		if (paramStr == null || paramStr.length != 2) {
			return;
		}
		String[] paramArray = paramStr[1].split("[&]");
		if (paramArray == null) {
			return;
		}
		for (String param : paramArray) {
			if (param == null || "".equals(param.trim())) {
				continue;
			}
			String[] keyValue = param.split("[=]", 2);
			if (keyValue == null || keyValue.length != 2) {
				continue;
			}
			hw.addNV(keyValue[0], keyValue[1]);
		}
	}

	/**
	 * 上传文件（包括图片）
	 * 
	 * @param url
	 *            请求URL
	 * @param paramsMap
	 *            参数和值
	 * @return
	 */
	public static ResponseContent postEntity(String url,
			Map<String, Object> paramsMap) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			//setParams(url, hw);
			Iterator<String> iterator = paramsMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = paramsMap.get(key);
				if (value instanceof File) {
					FileBody fileBody = new FileBody((File) value);
					hw.getContentBodies().add(fileBody);
				} else if (value instanceof byte[]) {
					byte[] byteVlue = (byte[]) value;
					ByteArrayBody byteArrayBody = new ByteArrayBody(byteVlue,
							key);
					hw.getContentBodies().add(byteArrayBody);
				} else {
					if (value != null && !"".equals(value)) {
						hw.addNV(key, String.valueOf(value));
					} else {
						hw.addNV(key, "");
					}
				}
			}
			ret = hw.postEntity(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 使用post方式，发布对象转成的json给Rest服务。
	 * 
	 * @param url
	 * @param jsonBody
	 * @return
	 */
	public static ResponseContent postJsonEntity(String url, String jsonBody) {
		return postText(url, jsonBody,"UTF-8", "application/json");
	}

	/**
	 * 使用post方式，发布对象转成的xml给Rest服务
	 * 
	 * @param url
	 *            URL地址
	 * @param xmlBody
	 *            xml文本字符串
	 * @return ResponseContent 如果发生异常则返回空，否则返回ResponseContent对象
	 */
	public static ResponseContent postXmlEntity(String url, String xmlBody) {
		return postEntity(url, xmlBody, "application/xml");
	}
	
	public static ResponseContent postTextEntity(String url, String xmlBody) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			ret = hw.postText(url, xmlBody, "text/xml", "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static ResponseContent postText(String urlstr, String body,
			String urlEncoding, String contentType) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			ret = hw.postText(urlstr, body, urlEncoding, contentType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static ResponseContent postEntity(String url, String body,
			String contentType) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			hw.addNV("body", body);
			ret = hw.postNV(url, contentType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	 /**
		 * @param
		 *            :请求接口
		 * @param
		 *            :参数
		 * @return 返回结果
		 */
		private String getUrlRespContent(String httpUrl, String reqKey,String reqValue) {
		    BufferedReader reader = null;
		    String result = null;
		    StringBuffer sbf = new StringBuffer();
		    
//		    if (paramMap != null) {
//		    	Map<String, Object> params = new HashMap<String, Object>();//请求参数
//	            params.put("carorg",paramMap.get("city"));//城市代码 *
//	            params.put("lsprefix",paramMap.get("hphm").substring(0,1));//号牌号码 完整7位 ,需要utf8 urlencode*
//	            params.put("lsnum",paramMap.get("hphm").substring(1));
//	            params.put("lstype","02");//号牌类型，默认02
//	            params.put("engineno",paramMap.get("engineno"));//发动机号 (根据城市接口中的参数填写)
//	            params.put("frameno",paramMap.get("classno"));//车架号 (根据城市接口中的参数填写)
//	            httpUrl = httpUrl + "?" + urlencode(params);
//			}
		    
		    
		    try {
		        URL url = new URL(httpUrl);
		        HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setRequestMethod("GET");
		        // 填入apikey到HTTP header
		        //connection.setRequestProperty("apikey",  Constants.baiduWzAppKey);
		        connection.setRequestProperty(reqKey,  reqValue);
		        connection.connect();
		        InputStream is = connection.getInputStream();
		        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        String strRead = null;
		        while ((strRead = reader.readLine()) != null) {
		            sbf.append(strRead);
		            sbf.append("\r\n");
		        }
		        reader.close();
		        result = sbf.toString();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return result;
		}


    public static String postWithJson(String url, String json) {
        String response = null;
        try {
            //创建连接
            URL postUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(json);
            out.flush();
            out.close();

            //读取响应
            response = IOUtils.toString(connection.getInputStream(),"UTF-8");
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    public static String postMethod(String url,String jsonString) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String response = null;
        try {
            StringEntity s = new StringEntity(jsonString, "UTF-8");   // 中文乱码在此解决
            s.setContentType("application/json");
            post.setEntity(s);

            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String charset = EntityUtils.getContentCharSet(entity);
                response = IOUtils.toString(entity.getContent(),"UTF-8");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * 发送POST请求，上传文件
     * @author ls
     * @param file
     * @param url
     * @param jsonBody
     * @return
     */
    public static ResponseContent upload(File file,String url,String jsonBody){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        ResponseContent ret = null;
        
        JSONObject jsonObj = JSONObject.fromObject(jsonBody);
        try {
            httpClient = HttpClients.createDefault();
            
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(file);
            System.out.println(file.exists());

            StringBody invKind = new StringBody(jsonObj.getString("invKind"), ContentType.create(
                    "text/plain", Consts.UTF_8));
            StringBody invNum = new StringBody(jsonObj.getString("invNum"), ContentType.create(
                    "text/plain", Consts.UTF_8));
            StringBody imgType = new StringBody(jsonObj.getString("imgType"), ContentType.create(
                    "text/plain", Consts.UTF_8));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    // 相当于<input type="file" name="file"/>
                    .addPart("file", bin)
                    
                    // 相当于<input type="text" name="userName" value=userName>
                    .addPart("invKind", invKind)
                    .addPart("invNum", invNum)
                    .addPart("imgType", imgType)
                    .build();

            httpPost.setEntity(reqEntity);
            //httpPost.setEntity(new StringEntity(jsonBody, Consts.UTF_8));执行此行会覆盖上面设置的setEntity参数
            
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            
            StatusLine statusLine = response.getStatusLine();
            ret = new ResponseContent();
            ret.setStatusCode(statusLine.getStatusCode());
            HttpClientWrapper httpClientWrapper = new HttpClientWrapper();
            httpClientWrapper.getResponseContent(resEntity, ret);            
            System.out.println("The response value of token:" + response.getFirstHeader("token"));
            
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }
            
            // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return ret;

    }

}
