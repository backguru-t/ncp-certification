package com.twolinecode.ncp.cert.service;

import com.twolinecode.ncp.cert.constant.NcloudApiUrls;
import com.twolinecode.ncp.cert.dto.req.DeleteCertificateReqDto;
import com.twolinecode.ncp.cert.dto.req.RegisterExternalCertificateReqDto;
import com.twolinecode.ncp.cert.dto.res.DeleteCertificateRespDto;
import com.twolinecode.ncp.cert.dto.res.GetCertificateListRespDto;
import com.twolinecode.ncp.cert.dto.res.RegisterExternalCertificateRespDto;
import com.twolinecode.ncp.cert.utils.OpenApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CertService extends BaseNcpService {
    private RestTemplate restTemplate = new RestTemplate();
    public GetCertificateListRespDto getCertificateList() {
        final String uri = OpenApiUtils.getOpenApiUrl(NcloudApiUrls.GET_CERTIFICATE_LIST);
        ResponseEntity<GetCertificateListRespDto> response = restTemplate.exchange(certApiHost + uri,
                                    HttpMethod.GET, new HttpEntity(getNcloudApiHeader(HttpMethod.GET, uri)),
                                    GetCertificateListRespDto.class);
        return response.getBody();
    }

    public RegisterExternalCertificateRespDto registerExternalCertificate(final RegisterExternalCertificateReqDto reqDto) {
        URI uri = UriComponentsBuilder.fromHttpUrl(certApiHost + NcloudApiUrls.REGISTER_EXTERNAL_CERTIFICATE).build().toUri();
        HttpHeaders header = getNcloudApiHeader(HttpMethod.POST, OpenApiUtils.getOpenApiUrl(NcloudApiUrls.REGISTER_EXTERNAL_CERTIFICATE));
        RequestEntity<RegisterExternalCertificateReqDto> requestEntity = new RequestEntity(reqDto, header, HttpMethod.POST, uri);

        ResponseEntity<RegisterExternalCertificateRespDto> response = restTemplate.exchange(requestEntity, RegisterExternalCertificateRespDto.class);
        return response.getBody();
    }

    public DeleteCertificateRespDto deleteCertificate(final String pathVar, final DeleteCertificateReqDto reqDto) {
        final String uri = OpenApiUtils.getOpenApiUrl(NcloudApiUrls.DELETE_CERTIFICATE, pathVar, reqDto);
        ResponseEntity<DeleteCertificateRespDto> response = restTemplate.exchange(certApiHost + uri,
                                    HttpMethod.DELETE, new HttpEntity(getNcloudApiHeader(HttpMethod.DELETE, uri)),
                                    DeleteCertificateRespDto.class);
        return response.getBody();
    }
}
