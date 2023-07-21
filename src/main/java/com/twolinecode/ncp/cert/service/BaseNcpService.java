package com.twolinecode.ncp.cert.service;

import com.twolinecode.ncp.cert.constant.NcloudApiHeader;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class BaseNcpService {
    @Value("${ncp.api.accessKey}")
    protected String accessKey;
    @Value("${ncp.api.secretKey}")
    protected String secretKey;
    @Value("${ncp.api.host}")
    protected String certApiHost;
    protected HttpHeaders getNcloudApiHeader(HttpMethod method, String url) {
        try {
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));

            String timeStamp = String.valueOf(System.currentTimeMillis());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(NcloudApiHeader.X_NCP_APIGW_TIMESTAMP, timeStamp);
            httpHeaders.add(NcloudApiHeader.X_NCP_IAM_ACCESS_KEY, accessKey);
            httpHeaders.add(NcloudApiHeader.X_NCP_APIGW_SIGNATURE, generateSignature(timeStamp, method.name(), url));
            httpHeaders.add(NcloudApiHeader.ACCEPT, "application/json");
            httpHeaders.setContentType(mediaType);

            return httpHeaders;
        } catch (Exception ex) {
            return null;
        }
    }

    private String generateSignature(String timeStamp, String method, String url) throws Exception {
        String message = new StringBuilder()
                .append(method)
                .append(" ")
                .append(url)
                .append("\n")
                .append(timeStamp)
                .append("\n")
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }
}
