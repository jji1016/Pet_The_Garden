package com.petthegarden.petthegarden.petnote.controller;


import com.petthegarden.petthegarden.petnote.service.DiaryApiService;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DairyApiController {
    private final DiaryApiService diaryApiService;

    @GetMapping("/api/diary/{petID}")
    @ResponseBody
    public Map<String, Object> getDiaryPage(@PathVariable Integer petID,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "8") int size) {
        Page<DiaryDto> diaryPage = diaryApiService.getDiaryListByPetId(petID,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "regDate")));

        Map<String, Object> result = new HashMap<>();
        result.put("diaries", diaryPage.getContent());
        result.put("totalPages", diaryPage.getTotalPages());
        result.put("currentPage", diaryPage.getNumber());

        return result;
    }
}
