package com.example.springbatch5_example.batch.parition;

import com.example.springbatch5_example.batch.domain.ProductVO;
import com.example.springbatch5_example.batch.job.api.QueryGenerator;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        // product테이블로 부터 type번호가 하나씩 들어있는 배열이 반환됨.
        ProductVO[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();

        for (int i = 0; i < productList.length; i++) {
            ExecutionContext value = new ExecutionContext();

            // reader에서 쓰이는 ExecutionContext의 값을 셋팅해줌
            result.put("partition" + i, value);
            value.put("product", productList[i]);
        }
        return result;
    }
}
