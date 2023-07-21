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


//    public static MultiValueMap<String, String> convert(ObjectMapper objectMapper, Object dto) {
//        try {
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            Map<String, String> map = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});
//            params.setAll(map);
//
//            return params;
//        } catch (Exception e) {
//            throw new IllegalStateException("Url Parameter converting error happened");
//        }
//    }



//    private static UriComponentsBuilder uriAndParamMerge(UriComponentsBuilder uriBuilder, String keyPrefix, final Object getParameters) {
//        final Map<String, Object> map = ApplicationContextUtils.getObjectMapper().convertValue(getParameters, new TypeReference<Map>() {
//        });
//        for (final Map.Entry<String, Object> entry : map.entrySet()) {
//            final Object value = entry.getValue();
//            if (value instanceof List) {
//                final List<Map> list = ApplicationContextUtils.getObjectMapper().convertValue(value, new TypeReference<List>() {
//                });
//
//                for (int i=0; i < list.size(); i++) {
//                    if (list.get(i) instanceof Map) {
//                        uriBuilder = uriAndParamMerge(uriBuilder, keyPrefix + entry.getKey() + "." + (i+1) + ".", list.get(i));
//                    } else {
//                        uriBuilder = uriBuilder.replaceQueryParam(keyPrefix + entry.getKey() + "." + (i+1), list.get(i));
//                    }
//                }
//            } else {
//                uriBuilder = uriBuilder.replaceQueryParam(keyPrefix + entry.getKey(), value);
//            }
//        }
//        return uriBuilder;
//    }
}
