package com.dj.mall.model.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.InputStream;

public class QiniuUtils {

//    /**
//     * 密钥AK
//     */
//    private static final String ACCESSKEY = "hMAwMGAZYIw0KzTyYNJA1wHU4kefk-j9G3Jhzp-d";
//
//    /**
//     * 密钥SK
//     */
//    private static final String SECRETKEY = "UekV0k8ydkdlus9iLn3b3ecRDhDxsVs3Z4cAXr6j";
//
//    /**
//     * 存储空间名称
//     * 1975977946
//     *
//     */
//    private static final String BUCKET = "zjq01";
//
//    /**
//     * 下载链接
//     * http://q9cgmldxi.bkt.clouddn.com/
//     * http://qazo01v5q.bkt.clouddn.com/
//     */
//    public static final String URL = "http://qazo01v5q.bkt.clouddn.com/";

    /**
     * 密钥AK
     */
    private static final String ACCESSKEY = "ZFQJWv71iFy0OiJDPVtF7FQ7qmMlyl4B__JqXa3R";

    /**
     * 密钥SK
     */
    private static final String SECRETKEY = "9qnkG8AS1-p_LyHNTyueQ5RO2OSIJLWk3lmLvTWw";

    /**
     * 存储空间名称
     */
    private static final String BUCKET = "wbbimg";

    /**
     * 下载链接
     */
    public static final String URL = "http://qbdozixhr.bkt.clouddn.com/";


    /**
     * 构造一个带指定 Region 对象的配置类
     */
    private static Configuration cfg = new Configuration(Region.region0());

    /**
     * 构造文件上传管理器
     */
    private static UploadManager uploadManager = new UploadManager(cfg);
    /**
     * 生成上传策略
     */
    private static Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
    private static String upToken = auth.uploadToken(BUCKET);

    /**
     * 本地文件上传
     * @param fileName 文件名
     */
    public static void upload(String fileName) {
        try {
            //如果是Windows情况下，格式是 D:\\qiniu\\test.png
            String localFilePath = "E:\\upload\\" + fileName;
            uploadManager.put(localFilePath, fileName, upToken);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 通过输入流上传至七牛云空间
     * @param inputStream 要上传的文件
     * @param fileName    文件名
     */
    public static void uploadByInputStream(InputStream inputStream, String fileName) {
        try {
            uploadManager.put(inputStream, fileName, upToken, null, null);
            System.out.println("上传成功");
        } catch (QiniuException ex) {
            System.err.println("上传失败");
            ex.printStackTrace();
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
    }

    /**
     * 通过字节数组上传
     *
     * @param file     要上传的文件
     * @param fileName 文件名
     */
    public static void uploadByByteArray(byte[] file, String fileName) {
        try {
            uploadManager.put(file, fileName, upToken);
            System.out.println("上传成功");
        } catch (QiniuException ex) {
            System.err.println("上传失败");
            ex.printStackTrace();
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 根据文件名删除bucket中的文件
     * @param fileName 文件名
     */
    public static void delFile(String fileName) {
        try {
            BucketManager bucketManager = new BucketManager(auth, cfg);
            bucketManager.delete(BUCKET, fileName);
            System.out.println("删除成功");
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.out.println("删除失败");
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
