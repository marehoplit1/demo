package com.example.util;

import lombok.Data;

import java.util.List;

/**
 * @author mgudelj
 */
@Data
public class ResultDto {
    public List<Vert> route ;
    double price;
}
