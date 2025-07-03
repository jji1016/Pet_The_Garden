package com.petthegarden.petthegarden.community.controller;


import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.repository.CommunityRepository;
import com.petthegarden.petthegarden.community.service.CommunityService;
import com.petthegarden.petthegarden.entity.Board;
import com.petthegarden.petthegarden.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final CommunityRepository communityRepository;

    @GetMapping("/board")
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


        //작성자, 작성일
        model.addAttribute("writer", board.getMember().getUserName());
        model.addAttribute("regDate", board.getRegDate());
        model.addAttribute("modifyDate", board.getModifyDate());

        return "/community/boardcorrect";
    }
    //게시글수정과정
    @PostMapping("/boardcorrect/{id}")
    public String updateBoard(@PathVariable Integer id, @ModelAttribute BoardDto boardDto) {
        communityService.updateBoard(id, boardDto);
        return "redirect:/community/boarddetail/" + id;
    }
    //ck editor 이미지 업로드
    @PostMapping("/upload-image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile upload) {
        return communityService.uploadImage(upload);
    }
    //게시글등록페이지 이동
    @GetMapping("/boardreg")
    public String showBoardForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        BoardDto boardDto = new BoardDto();
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("writer", userDetails.getUserrealname()); // 진짜 member테이블 이름
        return "community/boardreg";
    }
    @PostMapping("/boardreg")
    public String registerBoard(@ModelAttribute("boardDto") BoardDto boardDto,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getLoggedMember();
        int memberId = member.getId();

        communityService.saveBoard(boardDto,member);

        return "redirect:/community/board"; // 목록 페이지로 리다이렉트
    }
}
