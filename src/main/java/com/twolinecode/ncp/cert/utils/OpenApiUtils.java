package com.twolinecode.ncp.cert.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

public class OpenApiUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static String getOpenApiUrl(String uri) {
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        //return uriBuilder.toUriString() + "?responseFormatType=json";
        return uriBuilder.toUriString();
    }

    public static String getOpenApiUrl(String uri, Object requestDto) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        //return uriAndParamMerge(uriBuilder, "", requestDto).toUriString() + "&responseFormatType=json";
        MultiValueMap<String, String> map = MultiValueMapConverter.convert(objectMapper, requestDto);
        for(String key : map.keySet()) {
            uriBuilder = uriBuilder.replaceQueryParam(key, map.get(key));
        }

        return uriBuilder.toUriString();
    }

    public static String getOpenApiUrl(String uri, String pathVar, Object requestDto) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        MultiValueMap<String, String> map = MultiValueMapConverter.convert(objectMapper, requestDto);
        for(String key : map.keySet()) {
            uriBuilder = uriBuilder.replaceQueryParam(key, map.get(key));
        }

        return uriBuilder.buildAndExpand(pathVar).toUriString();
    }
}
