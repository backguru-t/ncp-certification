package com.twolinecode.ncp.cert.controller;

import com.twolinecode.ncp.cert.dto.req.DeleteCertificateReqDto;
import com.twolinecode.ncp.cert.dto.req.RegisterExternalCertificateReqDto;
import com.twolinecode.ncp.cert.dto.res.DeleteCertificateRespDto;
import com.twolinecode.ncp.cert.dto.res.GetCertificateListRespDto;
import com.twolinecode.ncp.cert.dto.res.RegisterExternalCertificateRespDto;
import com.twolinecode.ncp.cert.service.CertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificate")
public class CertController {

    private final CertService certService;

    @GetMapping("/get")
    public GetCertificateListRespDto getCertificates () {
        GetCertificateListRespDto getCertificateListRespDto = certService.getCertificateList();
        return getCertificateListRespDto;
    }

    @DeleteMapping("/delete/{certificateNo}/{certificateName}")
    public DeleteCertificateRespDto deleteCertificate(@PathVariable("certificateNo") String certificateNo,
                                                      @PathVariable("certificateName") String certificateName) {
        final DeleteCertificateReqDto reqDto = DeleteCertificateReqDto.builder().certificateName(certificateName).build();
        DeleteCertificateRespDto deleteCertificateRespDto = certService.deleteCertificate(certificateNo, reqDto);
        return deleteCertificateRespDto;
    }
    @PostMapping("/create/{certificateName}")
    public RegisterExternalCertificateRespDto registerExternalCertificate (@PathVariable String certificateName) throws Exception {
        final RegisterExternalCertificateReqDto reqDto = RegisterExternalCertificateReqDto.builder()
                                .certificateName(certificateName)
                                .privateKey(getKeys("D:/work/ncloud/backguru.info/privkey1.pem"))
                                .publicKeyCertificate(getKeys("D:/work/ncloud/backguru.info/cert1.pem"))
                                .certificateChain(getKeys("D:/work/ncloud/backguru.info/chain.pem")).build();

        return certService.registerExternalCertificate(reqDto);
    }

    private String getKeys (final String pathStr) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(pathStr)), Charset.defaultCharset());
        key = key.replaceAll("(\r|\n|\r\n|\n\r)","");
        return key;
    }
}
