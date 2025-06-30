package com.petthegarden.petthegarden.petnote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/petnote")
@RequiredArgsConstructor
public class PetNote {
    @GetMapping("/petlist")
    public String petlist() {
        return "petnote/petlist";
    }
}
