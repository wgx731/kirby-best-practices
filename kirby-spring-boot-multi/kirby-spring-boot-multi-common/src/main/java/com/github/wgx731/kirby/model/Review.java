package com.github.wgx731.kirby.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
// NOTE: below for spring boot
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private String name;

    private Double score;

}
