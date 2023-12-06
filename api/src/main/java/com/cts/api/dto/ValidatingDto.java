package com.cts.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Id;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ValidatingDto {

    @Id
    @JsonProperty
    private boolean validStatus;
        
}
