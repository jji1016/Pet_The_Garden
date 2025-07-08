package com.petthegarden.petthegarden.entity;

import com.petthegarden.petthegarden.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "memberID")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String userID;

    @Column(nullable = false)
    private String userPW;

    @Column(nullable = false)
    private String userName;

    private String image;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String tel;

    @Column(nullable = false)
    private boolean deleteStatus = false;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Column(nullable = false)
    private int zipcode;

    @Column(nullable = false)
    private String address01;

    private String address02;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Pet> petList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Report> reportList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Diary> diaryList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ShowOff> showOffList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ShowOffComment> showOffCommentList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boardList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<BoardComment> boardCommentList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Follow> followList;
}
