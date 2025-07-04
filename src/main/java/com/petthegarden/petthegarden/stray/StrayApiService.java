package com.petthegarden.petthegarden.stray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StrayApiService {

    public List<StrayDto> fetchStrayData() {
        String apiKey = "54725d057bbf493eb1aec4920f4b08c6";
        String apiUrl = "https://openapi.gg.go.kr/AbdmAnimalProtect";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("KEY", apiKey)
                .queryParam("Type", "json");

        String url = uriBuilder.toUriString();
        log.info("Request URL: {}", url);

        RestTemplate restTemplate = new RestTemplate();

        try {
            // ResponseEntity로 변경하여 상태 코드도 확인
            ResponseEntity<StrayApiResponseWrapper> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, StrayApiResponseWrapper.class);
            StrayApiResponseWrapper response = responseEntity.getBody();
            log.info("Response: {}", response);

            if (response != null && response.getAbdmAnimalProtect() != null && !response.getAbdmAnimalProtect().isEmpty()) {
                return response.getAbdmAnimalProtect().get(0).getRow();  // 첫 번째 Body의 row 데이터
            }

        } catch (Exception e) {
            log.error("Error occurred while calling API: ", e);
        }

        return Collections.emptyList();
    }
}
