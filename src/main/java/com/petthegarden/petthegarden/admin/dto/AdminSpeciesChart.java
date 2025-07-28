package com.petthegarden.petthegarden.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminSpeciesChart {
    private List<String> species;

    private List<Integer> counts;
}
