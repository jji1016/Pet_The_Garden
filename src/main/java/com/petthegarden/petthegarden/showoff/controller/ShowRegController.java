package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.showoff.service.ShowRegService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/showoff")
public class ShowRegController {

    private final ShowRegService showRegService;

    @GetMapping("/showreg")
    public String showRegForm(Model model) {
        model.addAttribute("showRegDto", new ShowRegDto());
        return "showoff/showreg";
    }

    @PostMapping("/showreg")
    public String createSubmit(@Valid @ModelAttribute ShowRegDto dto, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("showRegDto", dto);
            return "showoff/showreg";
        }
        // 정상 처리
        Member member = (Member) session.getAttribute("loginMember");
        Pet pet = (Pet) session.getAttribute("selectedPet");
        showRegService.createShowReg(dto, member, pet);
        return "redirect:/showoff/list";
    }


    @PostMapping("/image-upload")
    @ResponseBody
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {
        String fileUrl = "/upload/" + file.getOriginalFilename();  // 파일 저장 경로 구현 필요
        return ResponseEntity.ok(Map.of(
                "uploaded", 1,
                "fileName", file.getOriginalFilename(),
                "url", fileUrl
        ));
    }
}
