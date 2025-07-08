package com.petthegarden.petthegarden.entity;

import com.petthegarden.petthegarden.constant.PetGender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "petID")
    private Integer id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    private LocalDateTime modifyDate;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private String petName;

    private String birthDate;

    @Column(nullable = false)
    private String profileImg;

    @Column(nullable = false)
    private String content;

    private String follow;

    private String character;

    private String petLike;

    private String petDisLike;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetGender petGender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Diary> diaryList;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<ShowOff> showOffList;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Follow> followList;

}
