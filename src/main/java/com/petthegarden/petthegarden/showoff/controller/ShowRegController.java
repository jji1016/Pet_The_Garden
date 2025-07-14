package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowPetDto;
import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.showoff.service.ShowRegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/showoff")
@Slf4j
public class ShowRegController {

    private final ShowRegService showRegService;

    @Value("${file.path}f1/")
    private String uploadDir;

    @Autowired
    public ShowRegController(ShowRegService showRegService) {
        this.showRegService = showRegService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("showRegDto", new ShowRegDto());
        return "showoff/showreg";
    }

    @GetMapping("/showreg")
    public String showRegisterFormAlias(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Integer memberID = customUserDetails.getLoggedMember().getId();

        List<ShowPetDto> listOfPet = showRegService.getPetList(memberID);
        log.info("listOfPet={}", listOfPet);

        model.addAttribute("listOfPet", listOfPet);
        model.addAttribute("showRegDto", new ShowRegDto());
        return "showoff/showreg";
    }

    @PostMapping({"/showreg", "/detail/{id}/edit"})
    public String registerOrEditShowOff(
            @Valid @ModelAttribute ShowRegDto showRegDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails secMember,
            @PathVariable(value = "id", required = false) Integer id) {

        String img= showRegDto.getImage();
        log.info("img={}", img);

        if (bindingResult.hasErrors()) {
            System.out.println("여기????????");
            System.out.println(bindingResult.getAllErrors());
            return "showoff/showreg";
        }

        try {
            // 세션에서 회원 정보 가져오기
            Integer memberId = secMember.getLoggedMember().getId();
            if (memberId == null) {
                log.info("memberId 없음 / {}", memberId);
                model.addAttribute("error", "로그인이 필요합니다.");
                return "showoff/showreg";
            }

            // 회원 정보 조회
            Member member = showRegService.getMemberById(memberId);
            if (member == null) {
                log.info("member정보 없음 / {}", member);
                model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
                return "showoff/showreg";
            }

            // memberId 세팅
            showRegDto.setMemberId(memberId);

            // 유튜브 링크가 있으면 content에 추가
            String content = showRegDto.getContent();
            if (showRegDto.getYoutubeLink() != null && !showRegDto.getYoutubeLink().isEmpty()) {
                content += "\n\n[YouTube] " + showRegDto.getYoutubeLink();
                showRegDto.setContent(content);
            }

            ShowOff savedShowOff;
            if (id == null) {
                log.info("컨트롤러에서 등록");
                savedShowOff = showRegService.registerShowOff(showRegDto, member);
                redirectAttributes.addFlashAttribute("message", "장기자랑이 성공적으로 등록되었습니다!");
            } else {
                savedShowOff = showRegService.updateShowOff(id, showRegDto);
                redirectAttributes.addFlashAttribute("message", "장기자랑이 성공적으로 수정되었습니다!");
            }

            return "redirect:/showoff/detail/" + savedShowOff.getId();

        } catch (Exception e) {
            model.addAttribute("error", "장기자랑 등록/수정 중 오류가 발생했습니다: " + e.getMessage());
            return "showoff/showreg";
        }
    }

    @PostMapping("/upload-image")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("업로드할 파일이 없습니다.");
            }

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/PTGUpload/images/" + fileName;
            response.put("success", true);
            response.put("url", imageUrl);
            response.put("fileName", fileName);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이미지 업로드 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Integer id, Model model) {
        ShowOff showOff = showRegService.getShowOffById(id)
                .orElseThrow(() -> new RuntimeException("장기자랑을 찾을 수 없습니다."));
        model.addAttribute("showOff", showOff);
        return "showoff/showdetail";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> increaseLike(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            ShowOff updatedShowOff = showRegService.increaseLike(id);
            response.put("success", true);
            response.put("likeCount", updatedShowOff.getShowOffLike());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
