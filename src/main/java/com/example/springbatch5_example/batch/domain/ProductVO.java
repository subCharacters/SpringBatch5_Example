package com.example.springbatch5_example.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private int price;
    private String type;
}
