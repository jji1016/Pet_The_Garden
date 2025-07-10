package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.showoff.service.ShowRegService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/showoff")
public class ShowRegController {

    private final ShowRegService showRegService;

    @Autowired
    public ShowRegController(ShowRegService showRegService) {
        this.showRegService = showRegService;
    }

    // /showoff/register (기존 등록 폼)
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        return showRegisterFormInternal(model, session);
    }

    // /showoff/showreg (직접 접근용)
    @GetMapping("/showreg")
    public String showRegisterFormAlias(Model model, HttpSession session) {
        return showRegisterFormInternal(model, session);
    }

    // 실제 등록 폼 내부 로직 (중복 제거용)
    private String showRegisterFormInternal(Model model, HttpSession session) {
        List<Pet> pets = createDummyPets();
        model.addAttribute("showRegDto", new ShowRegDto());
        model.addAttribute("pets", pets);
        return "showoff/showreg";
    }

    // 장기자랑 등록 처리 (폼 제출 시 상세페이지로 이동)
    @PostMapping("/showreg")
    public String registerShowOff(@Valid @ModelAttribute ShowRegDto showRegDto,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<Pet> pets = createDummyPets();
            model.addAttribute("pets", pets);
            return "showoff/showreg";
        }
        try {
            Pet selectedPet = findPetById(showRegDto.getPetId());
            if (selectedPet == null) {
                model.addAttribute("error", "선택된 반려동물을 찾을 수 없습니다.");
                List<Pet> pets = createDummyPets();
                model.addAttribute("pets", pets);
                return "showoff/showreg";
            }
            ShowOff savedShowOff = showRegService.registerShowOff(showRegDto, null, selectedPet);
            redirectAttributes.addFlashAttribute("message", "장기자랑이 성공적으로 등록되었습니다!");
            // 등록 후 상세페이지로 리다이렉트
            return "redirect:/showoff/detail/" + savedShowOff.getId();
        } catch (Exception e) {
            model.addAttribute("error", "장기자랑 등록 중 오류가 발생했습니다: " + e.getMessage());
            List<Pet> pets = createDummyPets();
            model.addAttribute("pets", pets);
            return "showoff/showreg";
        }
    }

    // CKEditor 이미지 업로드 API
    @PostMapping("/upload-image")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("error", Map.of("message", "업로드할 파일이 없습니다."));
                return ResponseEntity.badRequest().body(response);
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("error", Map.of("message", "이미지 파일만 업로드 가능합니다."));
                return ResponseEntity.badRequest().body(response);
            }
            if (file.getSize() > 5 * 1024 * 1024) {
                response.put("error", Map.of("message", "파일 크기는 5MB 이하여야 합니다."));
                return ResponseEntity.badRequest().body(response);
            }
            String imageUrl = showRegService.uploadImage(file);
            response.put("url", imageUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", Map.of("message", "이미지 업로드 실패: " + e.getMessage()));
            return ResponseEntity.status(500).body(response);
        }
    }

    // 장기자랑 목록 페이지
    @GetMapping("/list")
    public String showList(Model model) {
        List<ShowOff> showOffs = showRegService.getAllShowOffs();
        model.addAttribute("showOffs", showOffs);
        return "showoff/list";
    }

    // 장기자랑 상세 페이지
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Integer id, Model model) {
        ShowOff showOff = showRegService.getShowOffById(id)
                .orElseThrow(() -> new RuntimeException("장기자랑을 찾을 수 없습니다."));
        model.addAttribute("showOff", showOff);
        return "showoff/detail";
    }

    // 좋아요 증가 API
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

    // 데모용 더미 데이터 생성 메서드들
    private List<Pet> createDummyPets() {
        return List.of(
                createDummyPet(1, "Max", "Dog"),
                createDummyPet(2, "Luna", "Cat"),
                createDummyPet(3, "Charlie", "Dog"),
                createDummyPet(4, "Bella", "Cat"),
                createDummyPet(5, "Rocky", "Dog")
        );
    }

    private Pet createDummyPet(Integer id, String petName, String species) {
        return new Pet(
                id, // id
                null, // regDate
                null, // modifyDate
                species, // species
                petName, // petName
                null, // birthDate
                "dummy.jpg", // profileImg (필수)
                "dummy content", // content (필수)
                null, // follow
                null, // character
                null, // petLike
                null, // petDisLike
                com.petthegarden.petthegarden.constant.PetGender.FEMALE, // petGender (필수)
                null, // member
                null, // diaryList
                null, // showOffList
                null // followList
        );
    }

    private Pet findPetById(Integer petId) {
        return createDummyPets().stream()
                .filter(pet -> pet.getId().equals(petId))
                .findFirst()
                .orElse(null);
    }
}
