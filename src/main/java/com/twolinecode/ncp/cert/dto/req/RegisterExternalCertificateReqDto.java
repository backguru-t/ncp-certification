package com.twolinecode.ncp.cert.dto.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterExternalCertificateReqDto {
    private String certificateName;
    private String privateKey;
    private String publicKeyCertificate;
    private String certificateChain;
}
