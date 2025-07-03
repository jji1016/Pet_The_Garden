package com.petthegarden.petthegarden.petnote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/petnote")
@RequiredArgsConstructor
public class PetNoteController {
    @GetMapping("/list")
    public String list() {
        return "petnote/list";
    }

    @GetMapping("/diary")
    public String diary() {
        return "petnote/diary";
    }


    @GetMapping("/profile")
    public String profile() {
        return "petnote/profile";
    }
}
