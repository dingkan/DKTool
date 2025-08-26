package com.o2o.dktool;

import com.o2o.dktool.response.Response;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRAGService {
    public Response<List<String>> queryRagTagList();

    public Response<String> uploadFile(@RequestParam String ragTag, @RequestParam("file")List<MultipartFile> files);
}
