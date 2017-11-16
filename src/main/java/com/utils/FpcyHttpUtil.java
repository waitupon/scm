package com.utils;

import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 接口帮助类
 * 1、发票查验
 * 2、剩余次数查询
 */
public class FpcyHttpUtil {

	public final static int timeout = 100000;

	public static String doHttpOutput(String url, String outputStr) {
		String result = "";
		InputStream is = null;
		int len = 0;
		int tmp = 0;
		OutputStream output = null;
		BufferedOutputStream objOutput = null;
		String charSet = "gbk";
		HttpURLConnection con = null;
		BufferedReader br = null;
		try {
			con = getConnection(url);
			output = con.getOutputStream();
			objOutput = new BufferedOutputStream(output);
			objOutput.write(outputStr.getBytes(charSet));
			objOutput.flush();
			objOutput.close();
			output.close();
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				is = con.getInputStream();
				int dataLen = is.available();
				br = new BufferedReader(new InputStreamReader(is, charSet));
				byte[] bytes = new byte[dataLen];
				while ((tmp = is.read()) != -1) {
					bytes[len++] = (byte) tmp;
					if (len == dataLen) {
						dataLen = bytes.length + dataLen;
						byte[] newbytes = new byte[dataLen];
						for (int i = 0; i < bytes.length; i++) {
							newbytes[i] = bytes[i];
						}
						bytes = newbytes;
					}
				}
				result = new String(bytes, 0, len, "gbk");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				if (objOutput != null)
					objOutput.close();
				if (output != null)
					output.close();
				if (br != null)
					br.close();
				if (con != null)
					con.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static HttpURLConnection getConnection(String URL) throws Exception {
		HttpURLConnection con = null;
		con = (HttpURLConnection) new URL(URL).openConnection();
		con.setRequestMethod("POST");
		con.setConnectTimeout(timeout);
		con.setReadTimeout(timeout);
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		return con;

	}
	
	/**
	 * 发票查验次数接口
	 * @param token
	 * @return
	 */
	public static String count(String url) {
		try {
			long ll = System.currentTimeMillis();
			String xml = "<cd><sv>cm</sv></cd>";
			String rResult = FpcyHttpUtil.doHttpOutput(url, xml);
			System.out.println("发票查验次数信息  : " + rResult);
			return rResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	
	/**
	 * 发票查验 接口
	 * url 客户端通信地址  
	 *（1）增值税专用发票（01）：发票代码、发票号码、开票日期和开具金额(不含税)；
	 *（2）机动车销售统一发票（03）：发票代码、发票号码、开票日期和不含税价；
	 *（3）增值税普通发票（04）、增值税电子普通发票（10）、增值税普通发票(卷票)（11）： 发票代码、发票号码、开票日期和校验码后6位；
	 *（4）货物运输业增值税专用发票（02）：发票代码、发票号码、开票日期和合计金额。
	 */
	public static String fpcy(String url, String fpdm, String fphm, String kprq, String value) {
		try {
			long ll = System.currentTimeMillis();
			String xml = "<cd><sv>cd</sv><fpdm>" + fpdm + "</fpdm><fphm>"
					+ fphm + "</fphm><kprq>" + kprq + "</kprq><value>" + value
					+ "</value></cd>";
			System.out.println("send msg:\n"+ xml);
			String rResult = FpcyHttpUtil.doHttpOutput(url, xml);
			System.out.println("发票" + fpdm + " , " + fphm + " : " + rResult);
			System.out.println("发票" + fpdm + " , " + fphm + " 查验时间:"
					+ (System.currentTimeMillis() - ll) + "ms  ");
			return rResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
     	/*String fpcy = FpcyHttpUtil.fpcy("http://127.0.0.1:8805", "3100172130", "37438347", "2017-10-17", "910.68");
		System.out.println(fpcy);*/
		/*String count = FpcyHttpUtil.count("http://127.0.0.1:8805");
        System.out.println(count);
*/

        String url = "http://fpcyweb.tax.sh.gov.cn:1001/WebQuery/query?fpdm=3100172130&fphm=37438347&kprq=20171017&fpje=910.68&fplx=01&yzm=5&yzmSj=2017-11-15+11%3A14%3A34&index=397c2f0129c694a7bdcaa2b422c4ac8f&iv=dc0389c324e2526bf208434d80d93ca0&salt=7c15e9bf1c0cc39451e4c437eac5ea9d";
		ResponseContent urlRespContent = HttpHelper.getUrlRespContent(url);
		System.out.println(urlRespContent.getContent());

	}
}