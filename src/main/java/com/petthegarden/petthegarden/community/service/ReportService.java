package com.petthegarden.petthegarden.community.service;

import com.petthegarden.petthegarden.community.dto.ReportDto;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Report;
import com.petthegarden.petthegarden.community.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void report(ReportDto reportDto, Member member) {

        boolean alreadyReported = reportRepository
                .findByMemberIdAndRepIDAndType(member.getId(), reportDto.getRepID(), reportDto.getType())
                .isPresent();

        if (alreadyReported) {
            throw new IllegalStateException("이미 신고한 항목입니다.");
        }

        Report report = Report.builder()
                .reason(reportDto.getReason())
                .type(reportDto.getType())
                .repID(reportDto.getRepID())
                .member(member)
                .reportDate(LocalDateTime.now())
                .build();

        reportRepository.save(report);
    }
}
