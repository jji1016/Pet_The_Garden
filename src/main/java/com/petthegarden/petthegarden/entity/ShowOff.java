package com.petthegarden.petthegarden.entity;

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
@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ShowOff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "showOffID")
    private Integer Id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;

    private String image;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    @OneToMany(mappedBy = "showOff", cascade = CascadeType.ALL)
    private List<ShowOffComment> showOffCommentList;

    @Column(nullable = false)
    private int showOffLike;
}
