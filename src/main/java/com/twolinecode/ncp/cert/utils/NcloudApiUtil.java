package com.twolinecode.ncp.cert.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class NcloudApiUtil {
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static String getApiUrl(String uri) {
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        return uriBuilder.toUriString();
    }

    public static String getApiUrl(String uri, Object requestDto) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        MultiValueMap<String, String> map = convert(objectMapper, requestDto);
        for(String key : map.keySet()) {
            uriBuilder = uriBuilder.replaceQueryParam(key, map.get(key));
        }

        return uriBuilder.toUriString();
    }

    public static String getApiUrl(String uri, String pathVar, Object requestDto) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

        MultiValueMap<String, String> map = convert(objectMapper, requestDto);
        for(String key : map.keySet()) {
            uriBuilder = uriBuilder.replaceQueryParam(key, map.get(key));
        }

        return uriBuilder.buildAndExpand(pathVar).toUriString();
    }

    private static MultiValueMap<String, String> convert(ObjectMapper objectMapper, Object dto) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});
            params.setAll(map);

            return params;
        } catch (Exception e) {
            throw new IllegalStateException("Url Parameter converting error happend");
        }
    }
}
