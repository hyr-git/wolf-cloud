package com.hyr.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.coyote.http11.InputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: lichaopi
 * @Date: 2019/8/9 11:55
 * @Description:阿里oss文件上传
 */
@Slf4j
@Component
public class FileOssUtil {

    public static void main(String[] args) {
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            File file = new File("D:\\123123.png");
            FileInputStream inputFilter = new FileInputStream(file);
            byte[] bytes = new byte[inputFilter.available()];
            inputFilter.read(bytes);
            ossClient.putObject(bucketName, "ceshi.png", new ByteArrayInputStream(bytes));
            // 设置文件的访问权限为公共读。
            ossClient.setObjectAcl(bucketName, "ceshi", CannedAccessControlList.PublicRead);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件上传异常");
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

    /**
     * 头像前缀
     */
    public static final String PATH_HEAD = "head/";
    /**
     * 视频前缀
     */
    public static final String VIDEO_HEAD = "video/";
    /**
     * 轻松购审核图片
     */
    public static final String PATH_EASYBUY_EXAMINE = "easybuy/examine/";
    /**
     * 大后台添加图片前缀
     */
    public static final String PATH_ADMIN = "admin/";
    /**
     * 大后台资料文件前缀
     */
    public static final String PATH_ADMIN_OFFICE = "office/";
    /**
     * 阿里云oss外网地址
     */
    public static final String endPoint = "oss-cn-beijing.aliyuncs.com";
    /**
     * keyId
     */
    private static final String accessKeyId = "LTAIif9nIk7qTyRN";
    /**
     * secret
     */
    private static final String accessKeySecret = "1dbQyl2JHeoXDrrKUundDrOsVFfh74";
    /**
     * 存在空间名称
     */
    public static String bucketName = "dev-carsir-agent";



    /**
     * @Author lichaopi
     * @Date 2019/8/9 12:15
     * @Param file：文件，name：文件名（有文件名的话不生成新的文件名），path：文件上传前缀
     * @Description 单个文件上传
     */
    public static String uploadFile(MultipartFile file, String name, String path) throws Exception {
        log.info("上传文件名称，file:{}", file.getOriginalFilename());
        if (StringUtils.isEmpty(name)) {
            String suffix = getExtensionName(file.getOriginalFilename());
            //上传文件名
            name = path + UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        }
        Exception throwE = null;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, name, new ByteArrayInputStream(file.getBytes()));
            // 设置文件的访问权限为公共读。
            ossClient.setObjectAcl(bucketName, name, CannedAccessControlList.PublicRead);
        } catch (Exception e) {
            throwE = e;
            e.printStackTrace();
            log.info("文件上传异常");
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        if (throwE != null) {
            throw throwE;
        }
        return name;
    }

    /**
     * @author: lichaopi
     * @Date: 2019/8/22 15:15
     * @Description:文件批量上传
     */
    public static List<String> uploadFiles(MultipartFile[] files, String path) throws Exception {
        List<String> list = new ArrayList<String>();
        Exception throwE = null;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            for (MultipartFile file : files) {
                String suffix = getExtensionName(file.getOriginalFilename());
                //上传文件名
                String name = path + UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
                //上传文件
                ossClient.putObject(bucketName, name, new ByteArrayInputStream(file.getBytes()));
                // 设置文件的访问权限为公共读。
                ossClient.setObjectAcl(bucketName, name, CannedAccessControlList.PublicRead);
                list.add(name);
            }
        } catch (Exception e) {
            throwE = e;
            e.printStackTrace();
            log.info("文件上传异常");
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        if (throwE != null) {
            throw throwE;
        }

        return list;
    }

    /**
     * @author: lichaopi
     * @Date: 2019/8/22 15:31
     * @Description:删除文件
     */
    public static Boolean delFile(String name) throws Exception {
        Boolean flag = false;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        try {
            boolean found = ossClient.doesObjectExist(bucketName, name);
            if (!found) {
                log.info("文件不存在，name:{}", name);
                return flag;
            }
            ossClient.deleteObject(bucketName, name);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件删除异常");
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        return flag;
    }

    /**
     * @author: lichaopi
     * @Date: 2019/8/9 21:43
     * @Description:获取文件完整地址
     */
    public static String getComplateUrl(String url) {
        return FileOssUtil.bucketName + "." + FileOssUtil.endPoint + "/" + url;
    }

    /**
     * @author: lichaopi
     * @Date: 2019/8/22 15:29
     * @Description:获取文件相对地址
     */
    public static String getRelativeUrl(String url) {
        return url.replaceAll(FileOssUtil.bucketName + "." + FileOssUtil.endPoint + "/", "");
    }

    /**
     * @author: lichaopi
     * @Date: 2019/8/9 12:04
     * @Description:获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
