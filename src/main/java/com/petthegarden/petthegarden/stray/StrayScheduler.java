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
        strayService.deleteAllInBatch(); // 기존 데이터 전부 삭제
        strayService.refreshData();
        log.info("업데이트 완료");
    }


}
