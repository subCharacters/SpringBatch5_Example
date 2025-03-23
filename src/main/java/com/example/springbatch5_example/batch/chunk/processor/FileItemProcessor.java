package com.example.springbatch5_example.batch.chunk.processor;

import com.example.springbatch5_example.batch.domain.Product;
import com.example.springbatch5_example.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {
    @Override
    public Product process(ProductVO item) throws Exception {
        Product product = new Product();
        product.setId(item.getId());
        product.setName(item.getName());
        product.setPrice(item.getPrice());
        product.setType(item.getType());

        // ModelMapper library 를 사용하면 아래와 같이 사용 가능하다.
        // ModelMapper modelMapper = new ModelMapper();
        // Product product = modelMapper.map(item, Product.class);

        System.out.println(product);

        return product;
    }
}
