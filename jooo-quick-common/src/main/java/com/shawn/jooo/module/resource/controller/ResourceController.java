package com.shawn.jooo.module.resource.controller;


import com.qcloud.cos.model.PutObjectResult;
import com.shawn.jooo.framework.client.TencentCosTemplate;
import com.shawn.jooo.framework.core.response.Response;
import com.shawn.jooo.framework.core.response.Responses;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("resource")
public class ResourceController {

    Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private TencentCosTemplate cosTemplate;

    @RequestMapping("uploadFile")
    @ResponseBody
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        String url = null;
        PutObjectResult result = null;
        if (file.getSize() <= 0) {
            throw new RuntimeException("文件内容大小错误");
        }

        String key = cosTemplate.getRandomKey(file);
        try {
            result = cosTemplate.uploadFile(key, file.getInputStream(), file.getContentType(), file.getSize());
        } catch (IOException e) {
            logger.error("uploadFile resource error", e);
            throw new RuntimeException("uploadFile resource error");
        }
        logger.debug("上传资源文件成功:{}", key);

        Map map = new HashedMap();
        map.put("url", cosTemplate.getDownloadUrl(key));
        map.put("md5", result.getContentMd5());
        return Responses.success(map);
    }


}
