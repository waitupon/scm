package com.utils;

import com.baiwang.bop.client.BopException;
import com.baiwang.bop.client.IBopClient;
import com.baiwang.bop.client.ILoginClient;
import com.baiwang.bop.client.impl.BopRestClient;
import com.baiwang.bop.client.impl.PostLogin;
import com.baiwang.bop.request.impl.LoginRequest;
import com.baiwang.bop.request.impl.bizinfo.CompanySearchRequest;
import com.baiwang.bop.request.impl.input.InitRequest;
import com.baiwang.bop.request.impl.input.SyncRequest;
import com.baiwang.bop.respose.entity.LoginResponse;
import com.baiwang.bop.respose.entity.bizinfo.CompanySearchResponse;
import com.baiwang.bop.respose.entity.input.InitResponse;
import com.baiwang.bop.respose.entity.input.SyncResponse;
import com.baiwang.bop.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.plaf.PanelUI;

public class BwUtils{
    private static final Logger log = LoggerFactory.getLogger(BwUtils.class);

    @Value("${bw.app.key}")
    private String appKey = "10000030";
    @Value("${bw.app.secret}")
    private String appSecret = "acae6f3a-0da6-4512-b788-4550a687a412";
    @Value("${bw.token}")
    private String token="c8fe1407-a8bc-4d8b-a2f4-07084bf64009s";
    @Value("${bw.user.salt}")
    private String userSalt="db98a6c4b317476b958a2bf610bc26b4";

    //String url = "http://60.205.83.27/router/rest";
   String url = "http://openapi.baiwang.com/router/rest";
    String username = "admin_1800000021168";
    String password = "123456";

    String taxNo = "91500000747150532A";

    public String getTokenBySecret(){
        String token = null;
        ILoginClient loginClient = new PostLogin(url);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAppkey(appKey);
        loginRequest.setAppSecret(appSecret);
        loginRequest.setUserName(username);
        loginRequest.setPasswordMd5(password);
        loginRequest.setUserSalt(userSalt);
        LoginResponse loginResponse = loginClient.login(loginRequest);

        token=loginResponse.getAccess_token();//记录
        return token;
    }


    public  void companySearch(){
        IBopClient client = new BopRestClient(url, appKey, appSecret);
        CompanySearchRequest request = new CompanySearchRequest();
        request.setCompanyName("北京美世珠漆业有限公司");//沙箱环境只能获取“百望股份”相关信息
        request.setAccuracy("TRUE");
        request.setSort("{\"frequency\": 0}");
       // request.setTaxId("110108339805094");
        request.setAppId("str");
        String token = getTokenBySecret();
        try
        {
            CompanySearchResponse response = client.execute(request, token, CompanySearchResponse.class);
            log.info(JacksonUtil.beanToString(response));
            log.info("success");
        }
        catch ( BopException e)
        {
            log.info("error");
        }
    }


    public  void inputInit(){
        IBopClient client = new BopRestClient(url, appKey, appSecret);
        InitRequest request = new InitRequest();
        request.setTaxNo(taxNo);
        request.setVersion("1.0");
        String token = getTokenBySecret();
        try
        {
            InitResponse response = client.execute(request, token, InitResponse.class);
            assert true;
            log.info(JacksonUtil.beanToString(response));
            log.info("success");
        }
        catch (BopException e)
        {
            assert false;
            log.info("error");
        }
    }

    public void inputSync(){

        IBopClient client = new BopRestClient(url, appKey, appSecret);
        SyncRequest request = new SyncRequest();
        request.setTaxNo(taxNo);
        request.setVersion("1.0");
        String token = getTokenBySecret();
        try
        {
            SyncResponse response = client.execute(request, token, SyncResponse.class);
            assert true;
            log.info(JacksonUtil.beanToString(response));
            log.info("success");
        }
        catch (BopException e)
        {
            assert false;
            log.info("error");
        }
    }

    public static void main(String[] args) {
        BwUtils utils = new BwUtils();
        utils.companySearch();
    }


}
