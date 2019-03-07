package com.omg.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
* @Author:         cyb
* @CreateDate:     2019/2/14 17:30
*/
@Slf4j
@Service
public class AttachUtil {

    public static void uploadAttach(MultipartFile file){
        String attachId = UUID.randomUUID().toString().replace("-","");
        String originalFilename = file.getOriginalFilename();
        //文件名重新命名
        String newFileName = originalFilename.substring(0,originalFilename.lastIndexOf("."))+"_"+attachId;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String pathname = "/usr/project/excel" + File.separator + newFileName + suffix;
        File upload = new File(pathname);
        if(!upload.getParentFile().exists()){
            upload.getParentFile().mkdirs();
        }
        log.info("上传路径：{}",pathname);
        try {
            log.info("开始上传文件：{}",originalFilename);
            file.transferTo(upload);
            log.info("上传文件结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
