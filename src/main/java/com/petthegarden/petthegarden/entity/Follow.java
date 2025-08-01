package com.petthegarden.petthegarden.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "followID")
    private Integer Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBERID", nullable = false)
    private Member member; //사람이

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "PETID", nullable = false)
    private Pet pet; //펫을 팔로우
}
