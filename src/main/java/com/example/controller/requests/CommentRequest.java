package com.example.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author mgudelj
 */
@Data
public class CommentRequest {
    @NotEmpty
    String comment ;
}
