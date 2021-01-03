package com.shawn.jooo.framework.client;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.shawn.jooo.framework.request.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云cos工具类
 *
 * @author shawn
 */
@Component
@ConditionalOnProperty("app.oss.tencent")
public class TencentCosTemplate {

    private static final Logger logger = LoggerFactory.getLogger(TencentCosTemplate.class);

    @Value("${app.oss.tencent.secretId}")
    private String secretId;

    @Value("${app.oss.tencent.secretKey}")
    private String secretKey;

    @Value("${app.oss.tencent.region}")
    private String region;

    @Value("${app.oss.tencent.bucketName}")
    private String bucketName;


    private COSClient cosClient;


    @PostConstruct
    public void init() {
        System.out.println("初始化ossClient方法");
        Requests.checkNotEmpty(secretId, new RuntimeException("secretId is empty"));
        Requests.checkNotEmpty(secretKey, new RuntimeException("secretKey is empty"));
        Requests.checkNotEmpty(region, new RuntimeException("region is empty"));
        Requests.checkNotEmpty(bucketName, new RuntimeException("bucketName is empty"));
        this.cosClient = creatClient();
    }

    public COSClient creatClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region ossRegion = new Region(region);
        ClientConfig clientConfig = new ClientConfig(ossRegion);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    public Bucket createBucket(COSClient cosClient) {
        String bucket = bucketName; //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        Bucket bucketResult = null;
        try {
            bucketResult = cosClient.createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
        return bucketResult;
    }

    public void uploadFile(String key, File uploadFile) {
        // 指定要上传到 COS 上对象键
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, uploadFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        putObjectResult.getMetadata().getServerSideEncryption();
    }

    public void uploadFile(String key, InputStream inputStream, String contentType, long size) {
        // 指定要上传到的存储桶
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(size);
        // 指定要上传到 COS 上对象键
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }

    public ObjectMetadata downloadFile(String key, File downFile) {
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 下载对象的 CRC64
        String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        try {
            cosObjectInput.close();
        } catch (IOException e) {
            logger.error("download file err", e);
        }
        getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        return downObjectMeta;
    }
}
