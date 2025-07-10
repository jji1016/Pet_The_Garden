package com.petthegarden.petthegarden.stray;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StrayApiService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<StrayDto> fetchStrayData() {
        String apiKey = "54725d057bbf493eb1aec4920f4b08c6";
        String apiUrl = "https://openapi.gg.go.kr/AbdmAnimalProtect";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("KEY", apiKey)
                .queryParam("Type", "json")
                .queryParam("pSize", 200);

        String url = uriBuilder.toUriString();
        log.info("Request URL: {}", url);

        RestTemplate restTemplate = new RestTemplate();

        try {
            String rawJson = restTemplate.getForObject(url, String.class);
            // JSON을 Map 형태로 파싱
            Map<String, Object> root = objectMapper.readValue(rawJson, Map.class);

            List<Map<String, Object>> abdmAnimalProtect = (List<Map<String, Object>>) root.get("AbdmAnimalProtect");
            if (abdmAnimalProtect != null) {
                for (Map<String, Object> section : abdmAnimalProtect) {
                    if (section.containsKey("row")) {
                        List<Map<String, Object>> rows = (List<Map<String, Object>>) section.get("row");
                        log.info("Rows size: {}", rows.size());

                        List<StrayDto> strayList = rows.stream()
                                .map(item -> objectMapper.convertValue(item, StrayDto.class))
                                .collect(Collectors.toList());
                        return strayList;
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error occurred while calling API: ", e);
        }

        return Collections.emptyList();
    }
}
