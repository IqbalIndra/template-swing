package com.learn.shirologin.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RatingMatch {
    private Long id;
    private String alternativeName;
    private Double value;
    private Integer rank;
}
