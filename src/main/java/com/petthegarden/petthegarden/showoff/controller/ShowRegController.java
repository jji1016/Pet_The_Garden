package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.showoff.service.ShowRegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import java.io.IOException;
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
    public String showRegisterForm(Model model, HttpSession session) {
        return showRegisterFormInternal(model, session);
    }

    @GetMapping("/showreg")
    public String showRegisterFormAlias(Model model, HttpSession session) {
        return showRegisterFormInternal(model, session);
    }

    private String showRegisterFormInternal(Model model, HttpSession session) {
        model.addAttribute("showRegDto", new ShowRegDto());
        return "showoff/showreg";
    }

    @PostMapping({"/showreg", "/detail/{id}/edit"})
    public String registerOrEditShowOff(
            @Valid @ModelAttribute ShowRegDto showRegDto,
            BindingResult bindingResult,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            @PathVariable(value = "id", required = false) Integer id) {
        if (bindingResult.hasErrors()) {
            return "showoff/showreg";
        }
        try {
            ShowOff savedShowOff;
            if (id == null) {
                savedShowOff = showRegService.registerShowOff(showRegDto, null, null);
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
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("upload") MultipartFile file) {
        log.info("여기는?????????");
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

            String imageUrl = "/uploads/images/" + fileName;
            log.info("들어오나?????????");

            response.put("url", imageUrl); // ✅ CKEditor에서 사용하는 key
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", Map.of("message", "이미지 업로드 실패: " + e.getMessage()));
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<ShowOff> showOffs = showRegService.getAllShowOffs();
        model.addAttribute("showOffs", showOffs);
        return "showoff/list";
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
