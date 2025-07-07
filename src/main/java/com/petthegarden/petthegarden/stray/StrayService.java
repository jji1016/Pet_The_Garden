package com.petthegarden.petthegarden.stray;

import com.petthegarden.petthegarden.entity.Stray;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StrayService {

    private final StrayRepository strayRepository;
    private final StrayApiService strayApiService;

    @Transactional
    public void refreshData() {
        List<StrayDto> strayList = strayApiService.fetchStrayData();

        strayRepository.deleteAllInBatch(); // 기존 데이터 전부 삭제

        List<Stray> entities = strayList.stream()
                .map(dto -> Stray.builder()
                        .IMAGE_COURS(dto.getIMAGE_COURS())
                        .DISCVRY_PLC_INFO(dto.getDISCVRY_PLC_INFO())
                        .SPECIES_NM(dto.getSPECIES_NM())
                        .PBLANC_BEGIN_DE(dto.getPBLANC_BEGIN_DE())
                        .PBLANC_END_DE(dto.getPBLANC_END_DE())
                        .COLOR_NM(dto.getCOLOR_NM())
                        .STATE_NM(dto.getSTATE_NM())
                        .AGE_INFO(dto.getAGE_INFO())
                        .BDWGH_INFO(dto.getBDWGH_INFO())
                        .SEX_NM(dto.getSEX_NM())
                        .NEUT_YN(dto.getNEUT_YN())
                        .SFETR_INFO(dto.getSFETR_INFO())
                        .SHTER_NM(dto.getSHTER_NM())
                        .SHTER_TELNO(dto.getSHTER_TELNO())
                        .PROTECT_PLC(dto.getPROTECT_PLC())
                        .build())
                .toList();

        strayRepository.saveAll(entities);
    }
}
