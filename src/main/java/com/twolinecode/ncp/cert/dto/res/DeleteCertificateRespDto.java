package com.twolinecode.ncp.cert.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteCertificateRespDto {
    private String returnCode;
    private String returnMessage;
    private String totalRows;
    private List<SslCertificateList> sslCertificateList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SslCertificateList {
        private String certificateNo;
        private String certificateName;
        private String memberNo;
        private String dnInfo;
        private String domainAddress;
        private String subDomainAddress;
        private String regDate;
        private String issueDate;
        private String validStartDate;
        private String validEndDate;
        private String disuseDate;
        private String disuseMsg;
        private String requestId;
        private String statusCode;
        private String statusName;
        private String externalYn;
        private String domainCode;
        private String caInfo;
        private String certSerialNumber;
        private String certPublicKeyInfo;
        private String certSignAlgorithmName;
        private String usedInstanceNoList;
    }
}
