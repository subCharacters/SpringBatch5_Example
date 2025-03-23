package com.example.springbatch5_example.batch.chunk.writer;

import com.example.springbatch5_example.batch.domain.ApiRequestVO;
import com.example.springbatch5_example.batch.domain.ApiResponseVO;
import com.example.springbatch5_example.service.AbstractApiService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class ApiItemWriter2 extends FlatFileItemWriter<ApiRequestVO> {

    private final AbstractApiService apiService;

    public ApiItemWriter2(AbstractApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void write(Chunk<? extends ApiRequestVO> chunk) throws Exception {
        ApiResponseVO responseVo = apiService.service(chunk.getItems());
        System.out.println("responseVo : " + responseVo);

        chunk.getItems().forEach(item -> item.setApiResponseVO(responseVo));

        super.setResource(new FileSystemResource(
                "C:\\Users\\wldns\\IdeaProjects\\SpringBatch5_Example\\src\\main\\resources\\product2.txt"));
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());
        super.setAppendAllowed(true);
        super.write(chunk);
    }
}
