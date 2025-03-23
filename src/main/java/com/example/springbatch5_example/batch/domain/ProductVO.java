package com.example.springbatch5_example.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private long id;
    private String name;
    private int price;
    private String type;
}
