package com.example.springbatch5_example.batch.chunk.writer;

import com.example.springbatch5_example.batch.domain.ApiRequestVO;
import com.example.springbatch5_example.batch.domain.ApiResponseVO;
import com.example.springbatch5_example.service.AbstractApiService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class ApiItemWriter3 implements ItemWriter<ApiRequestVO> {

    private final AbstractApiService apiService;

    public ApiItemWriter3(AbstractApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void write(Chunk<? extends ApiRequestVO> chunk) throws Exception {
        ApiResponseVO responseVo = apiService.service(chunk.getItems());
        System.out.println("responseVo : " + responseVo);
    }
}
