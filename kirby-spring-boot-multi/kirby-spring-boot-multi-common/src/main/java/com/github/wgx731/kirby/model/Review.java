package com.github.wgx731.kirby.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Review Model
 */
@Builder
@EqualsAndHashCode
@ToString
// NOTE: below used by spring boot
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    /**
     * review name
     */
    private String name;

    /**
     * review score
     */
    private Double score;

}
