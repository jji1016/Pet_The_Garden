package com.petthegarden.petthegarden.community.controller;


import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.repository.CommunityRepository;
import com.petthegarden.petthegarden.community.service.CommunityService;
import com.petthegarden.petthegarden.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final CommunityRepository communityRepository;

    @GetMapping
    public String community(Model model, @RequestParam(required = false) String keyword) {
        List<Board> boards = communityService.getBoardList();
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        return "/community/board";
    }

    @GetMapping("/boarddetail/{id}")
    public String boardDetail(@PathVariable Integer id, Model model) {
        Board board = communityService.getBoardById(id);
        model.addAttribute("board", board);
        String loginUsername = "admin";
        model.addAttribute("loginUsername", loginUsername);
        return "/community/boarddetail";
    }
    //게시글수정페이지 이동
    @GetMapping("/boardcorrect/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Board board = communityService.getBoardById(id);
        BoardDto boardDto = new BoardDto();
        boardDto.setSubject(board.getSubject());
        boardDto.setContent(board.getContent());
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("boardId", id);
        model.addAttribute("existingImage", board.getImage());

        //작성자, 작성일
        model.addAttribute("writer", board.getMember().getUserName());
        model.addAttribute("regDate", board.getRegDate());
        model.addAttribute("modifyDate", board.getModifyDate());

        return "/community/boardcorrect";
    }
    //게시글수정과정
    @PostMapping("/boardcorrect/{id}")
    public String updateBoard(@PathVariable Integer id, @ModelAttribute BoardDto boardDto,
                              @RequestParam("file") MultipartFile file) {
        communityService.updateBoard(id, boardDto, file);
        return "redirect:/community/boarddetail/" + id;
    }
    //ck editor 이미지 업로드
    @PostMapping("/upload-image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile upload) {
        return communityService.uploadImage(upload);
    }
}
