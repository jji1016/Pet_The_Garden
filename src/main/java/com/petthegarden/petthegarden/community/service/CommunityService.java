package com.petthegarden.petthegarden.community.service;

import com.petthegarden.petthegarden.community.dao.CommunityDao;
import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.repository.CommunityCommentRepository;
import com.petthegarden.petthegarden.community.repository.CommunityRepository;
import com.petthegarden.petthegarden.entity.Board;
import com.petthegarden.petthegarden.entity.BoardComment;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.repository.MypageMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final MypageMemberRepository mypageMemberRepository;
    private final CommunityCommentRepository communityCommentRepository;
    @Value("C:/PTGUpload/board/")
    String boardPath;

    @Value("C:/PTGUpload/tmp")
    String tmpPath;

    private final CommunityDao communityDao;

    public List<Board> getBoardList() {
        return communityDao.findAllBoards();
    }

    public List<Board> getBoardList2(Integer memberId) {
        return communityDao.findByMemberId(memberId);
    }

    public Board getBoardById(Integer id) {
        Board board = communityDao.findById(id);
        if (board != null && board.getBoardCommentList() != null) {
            board.getBoardCommentList().sort(Comparator.comparing(BoardComment::getRegDate));
        }
        return board;
    }

    //게시판 글 등록
    public void saveBoard(BoardDto boardDto, Member member, MultipartFile extraImage ) {

        Board board = boardDto.toEntity();
        board.setRegDate(LocalDateTime.now());
        board.setMember(member);
        String content = boardDto.getContent();

        if (extraImage != null && !extraImage.isEmpty()) {
            Map<String, Object> uploadResult = uploadImage(extraImage);

            if ((int) uploadResult.get("uploaded") == 1) {
                String imageUrl = (String) uploadResult.get("url");
                // content에 이미지 추가
                content += "<div><img src='" + imageUrl + "' style='max-width:100%; margin-top:20px;' /></div>";

                // DB에도 저장
                board.setImage(imageUrl);
            } else {
                throw new RuntimeException("이미지 업로드 실패: " + ((Map<?, ?>) uploadResult.get("error")).get("message"));
            }
        }

        board.setContent(content);

        Member admin = mypageMemberRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("관리자 계정을 찾을 수 없습니다."));
        communityRepository.save(board);
    }

    //게시판 글 수정
    public void updateBoard(Integer id, BoardDto boardDto, MultipartFile extraImage) {
        Board board = getBoardById(id);

        board.setSubject(boardDto.getSubject());
        String content = boardDto.getContent();
        if (extraImage != null && !extraImage.isEmpty()) {
            Map<String, Object> uploadResult = uploadImage(extraImage);

            if ((int) uploadResult.get("uploaded") == 1) {
                String imageUrl = (String) uploadResult.get("url");

                // content에 이미지 추가
                content += "<div><img src='" + imageUrl + "' style='max-width:100%; margin-top:20px;' /></div>";

                // DB에도 저장
                board.setImage(imageUrl);
            } else {
                throw new RuntimeException("이미지 업로드 실패: " + ((Map<?, ?>) uploadResult.get("error")).get("message"));
            }
        }
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());

    }

    //이미지 업로드
    public Map<String, Object> uploadImage(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file == null || file.isEmpty()) {
            response.put("uploaded", 0);
            response.put("error", Map.of("message", "파일이 없습니다."));
            return response;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = baseName + "_" + timestamp + extension;

            File saveDir = new File(boardPath); // 게시판 폴더에 저장
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            File savedFile = new File(saveDir, fileName);
            file.transferTo(savedFile);

            response.put("uploaded", 1);
            response.put("fileName", fileName);
            response.put("url", "/PTGUpload/board/" + fileName);

        } catch (Exception e) {
            response.put("uploaded", 0);
            response.put("error", Map.of("message", "업로드 실패: " + e.getMessage()));
        }

        return response;
    }

    //게시판 댓글 작성
    public void saveComment(Integer boardId, String content, Member member) {
        Board board = communityRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다: " + boardId));
        BoardComment comment = BoardComment.builder()
                .content(content)
                .member(member)
                .board(board)
                .build();
        communityCommentRepository.save(comment);
    }

    //게시판 댓글 삭제
    public void deleteComment(Integer commentId, Member member) {
        BoardComment comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        // 작성자 확인 (본인 댓글만 삭제 가능)
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("본인 댓글만 삭제할 수 있습니다.");
        }

        communityCommentRepository.delete(comment);
    }

    public Integer getBoardIdByCommentId(Integer commentId) {
        return communityCommentRepository.findById(commentId)
                .map(comment -> comment.getBoard().getId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));
    }

    //게시판 댓글 수정
    @Transactional
    public void updateComment(Integer commentId, String content, Member member) {
        BoardComment comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("본인 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
    }

    //게시판 글 삭제
    public boolean deleteBoard(Integer boardId, Member member) {
        Optional<Board> boardOpt = communityRepository.findById(boardId);
        if (boardOpt.isEmpty()) {
            return false;
        }

        Board board = boardOpt.get();

        // 게시글 작성자와 현재 사용자가 같은지 체크
        if (!board.getMember().getId().equals(member.getId())) {
            return false;
        }

        communityRepository.delete(board);
        return true;
    }
    //게시판 글 검색처리관련
    public Page<Board> getAllBoards(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }
    //게시판 글 검색처리관련
    public Page<Board> searchBoards(String keyword, Pageable pageable) {
        return communityRepository.findBySubjectContainingIgnoreCase(keyword, pageable);
    }
    //마이페이지 글 페이징처리관련
    public Page<Board> getBoardsByMember(Integer memberId, Pageable pageable) {
        return communityRepository.findByMemberId(memberId, pageable);
    }
}