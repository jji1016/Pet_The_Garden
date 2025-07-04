package com.petthegarden.petthegarden.stray;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StrayScheduler {

    private final StrayService strayService;

    // 매주 월요일 오전 4시
    @Scheduled(cron = "0 0 4 ? * MON")
    public void updateStrayData() {
        log.info("주간 유기동물 데이터 업데이트 시작");
        strayService.refreshData();
        log.info("업데이트 완료");
    }

//    @PostConstruct
//    public void init() {
//        log.info("앱 시작 시 데이터 초기화");
//        strayService.refreshData();
//    }
}
