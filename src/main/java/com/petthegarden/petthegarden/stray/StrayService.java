package com.petthegarden.petthegarden.stray;

import com.petthegarden.petthegarden.entity.Stray;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StrayService {

    private final StrayRepository strayRepository;
    private final StrayApiService strayApiService;
    private final StrayDao strayDao;

    @Transactional
    public void refreshData() {
        List<StrayDto> strayList = strayApiService.fetchStrayData();

        List<Stray> entities = strayList.stream()
                .map(dto -> Stray.builder()
                        .ABDM_IDNTFY_NO(dto.getAbdmIdNtfyNo())
                        .IMAGE_COURS(dto.getImageCours())
                        .DISCVRY_PLC_INFO(dto.getDiscvryPlcInfo())
                        .SPECIES_NM(dto.getSpeciesNm())
                        .PBLANC_BEGIN_DE(dto.getPblancBeginDe())
                        .PBLANC_END_DE(dto.getPblancEndDe())
                        .COLOR_NM(dto.getColorNm())
                        .STATE_NM(dto.getStateNm())
                        .AGE_INFO(dto.getAgeInfo())
                        .BDWGH_INFO(dto.getBdwghInfo())
                        .SEX_NM(dto.getSexNm())
                        .NEUT_YN(dto.getNeutYn())
                        .SFETR_INFO(dto.getSfetrInfo())
                        .SHTER_NM(dto.getShterNm())
                        .SHTER_TELNO(dto.getShterTelno())
                        .PROTECT_PLC(dto.getProtectPlc())
                        .build())
                .toList();

        strayRepository.saveAll(entities);
    }

    public void deleteAllInBatch() {
        strayDao.deleteAllInBatch();
    }

    public int totalStray(String startDate,String endDate,String discvryPlc,String species) {
        startDate = (startDate == null || startDate.isBlank()) ? null : startDate.replace("-", "");
        endDate = (endDate == null || endDate.isBlank()) ? null : endDate.replace("-", "");

        return strayDao.totalStray(startDate,endDate,discvryPlc,species);
    }

    public List<StrayDto> getStrayList(String startDate, String endDate, String discvryPlc, String species, int startItem, int endItem) {
        startDate = (startDate == null || startDate.isBlank()) ? null : startDate.replace("-", "");
        endDate = (endDate == null || endDate.isBlank()) ? null : endDate.replace("-", "");

        System.out.println("startDate: " + startDate + " endDate: " + endDate);
        List<Stray> strayEntities = strayDao.getStrayList(startDate,endDate,discvryPlc,species,startItem,endItem);
        List<StrayDto> strayDtoList = strayEntities.stream()
                .map(Stray::toStrayDto)
                .toList();
        return strayDtoList;
    }
}
