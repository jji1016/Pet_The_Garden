package com.petthegarden.petthegarden.community.controller;


import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.repository.CommunityRepository;
import com.petthegarden.petthegarden.community.service.CommunityService;
import com.petthegarden.petthegarden.entity.Board;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.repository.MypageMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final MypageMemberRepository mypageMemberRepository;

    @GetMapping("/board")
    public String community(Model model, @RequestParam(required = false) String keyword,
                            @PageableDefault(size = 7, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> boardPage;

        if (keyword != null && !keyword.isBlank()) {
            boardPage = communityService.searchBoards(keyword, pageable);
        } else {
            boardPage = communityService.getAllBoards(pageable);
        }

        List<Board> boards = communityService.getBoardList();
        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("keyword", keyword);
        return "/community/board";
    }

    @GetMapping("/boarddetail/{id}")
    public String boardDetail(@PathVariable Integer id, Model model,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Board board = communityService.getBoardById(id);
        model.addAttribute("board", board);
        String loginUsername = userDetails.getUsername();
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
        boardDto.setImage(board.getImage());
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("boardId", id);


        //작성자, 작성일
        model.addAttribute("writer", board.getMember().getUserName());
        model.addAttribute("regDate", board.getRegDate());
        model.addAttribute("modifyDate", board.getModifyDate());

        return "/community/boardcorrect";
    }
    //게시글수정
    @PostMapping("/boardcorrect/{id}")
    public String updateBoard(@PathVariable Integer id, @ModelAttribute BoardDto boardDto,
                              @RequestParam(value = "extraImage", required = false) MultipartFile extraImage,
                              @RequestParam(value = "existingImage", required = false) String existingImage) {
        communityService.updateBoard(id, boardDto, extraImage, existingImage);
        return "redirect:/community/boarddetail/" + id;
    }
    //게시글삭제
    @PostMapping("/board/{boardId}/delete")
    public String deleteBoard(@PathVariable Integer boardId,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인 후 이용해주세요.");
        }

        Member member = userDetails.getLoggedMember();

        boolean deleted = communityService.deleteBoard(boardId, member);

        if (!deleted) {
            // 삭제 실패 시(작성자가 아니거나 게시글 없음) 상세 페이지로 이동
            return "redirect:/community/boarddetail/" + boardId + "?error=삭제권한이없습니다";
        }

        // 삭제 성공 시 게시글 목록 페이지로 이동
        return "redirect:/community/board";
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
        String adminName = "관리자";
        if (userDetails != null) {
            model.addAttribute("writer", userDetails.getUserrealname());
        } else {
            model.addAttribute("writer", adminName);
        }
        return "community/boardreg";
    }
    //게시글등록
    @PostMapping("/boardreg")
    public String registerBoard(@ModelAttribute("boardDto") BoardDto boardDto,
                                @RequestParam(value = "extraImage", required = false) MultipartFile extraImage,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member;
        if (userDetails != null) {
            member = userDetails.getLoggedMember();
        } else {
            // userDetails가 null인 경우, 임시로 관리자 계정(아이디 1) 사용
            member = mypageMemberRepository.findById(1)
                    .orElseThrow(() -> new IllegalArgumentException("관리자 계정을 찾을 수 없습니다."));
        }
        communityService.saveBoard(boardDto,member,extraImage);

        return "redirect:/community/board"; // 목록 페이지로 리다이렉트
    }
    //댓글작성
    @PostMapping("/board/{boardId}/comment")
    @ResponseBody
    public String addComment(@PathVariable Integer boardId,
                             @RequestParam("content") String content,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member;
        if (userDetails != null) {
            member = userDetails.getLoggedMember();
        } else {
            member = mypageMemberRepository.findById(1)
                    .orElseThrow(() -> new IllegalArgumentException("관리자 계정을 찾을 수 없습니다."));
        }

        communityService.saveComment(boardId, content, member);

        return "";
    }
    //댓글삭제
    @PostMapping("/comment/{commentId}/delete")
    @ResponseBody
    public String deleteComment(@PathVariable Integer commentId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "로그인 후 이용해주세요.";
        }

        Member member = userDetails.getLoggedMember();

        communityService.deleteComment(commentId, member);

        return "댓글이 삭제되었습니다.";
    }
    //댓글수정
    @PostMapping("/comment/{commentId}/edit")
    @ResponseBody
    public String editComment(@PathVariable Integer commentId,
                              @RequestParam String content,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "로그인 후 이용해주세요.";
        }

        Member member = userDetails.getLoggedMember();

        try {
            communityService.updateComment(commentId, content, member);
            return "댓글이 수정되었습니다.";
        } catch (Exception e) {
            return "수정 실패: " + e.getMessage();
        }
    }
    @GetMapping("/board/{boardId}/comment-html")
    public String getCommentHtml(@PathVariable Integer boardId,
                                 Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        Board board = communityService.getBoardById(boardId);
        model.addAttribute("board", board);
        String loginUsername = userDetails != null ? userDetails.getUsername() : null;
        model.addAttribute("loginUsername", loginUsername);
        return "community/boardcomment";
    }
}
