package com.o2o.dktool.resources;

import com.o2o.dktool.IRAGService;
import com.o2o.dktool.response.Response;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/rag/")
public class RAGController implements IRAGService {

    @Resource
    private TokenTextSplitter tokenTextSplitter;

    @Resource
    private PgVectorStore pgVectorStore;

    @Resource
    private RedissonClient redissonClient;

    @RequestMapping(value = "raglist", method = RequestMethod.GET)
    @Override
    public Response<List<String>> queryRagTagList() {
        try {
            RList<String> tagTag = redissonClient.getList("ragTag");
            return Response.<List<String>>builder()
                    .code("0000")
                    .info("调用成功")
                    .data(new ArrayList<>(tagTag))
                    .build();
        }catch (Exception e){
            log.error("err:{}", e);
        }
        return null;
    }

    @RequestMapping(value = "file/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @Override
    public Response<String> uploadFile(@RequestParam("ragTag") String ragTag, List<MultipartFile> files) {
        log.info("上传知识库开始{}", ragTag);
        for (MultipartFile file : files) {
            TikaDocumentReader documentReader = new TikaDocumentReader(file.getResource());
            List<Document> documents = documentReader.get();
            List<Document> documentsSplitterList = tokenTextSplitter.apply(documents);

            //添加知识库标签
            documents.forEach(doc -> doc.getMetadata().put("knowledge", ragTag));
            documentsSplitterList.forEach(doc -> doc.getMetadata().put("knowledge", ragTag));

            pgVectorStore.accept(documentsSplitterList);

            //添加知识库记录
            RList<String> elements = redissonClient.getList("ragTag");
            if (!elements.contains(ragTag)){
                elements.add(ragTag);
            }
        }

        log.info("上传知识库完成 {}", ragTag);
        return Response.<String>builder()
                .code("0000")
                .info("上传成功")
                .build();
    }
}
