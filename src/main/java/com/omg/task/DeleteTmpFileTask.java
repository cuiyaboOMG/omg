package com.omg.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

/**
 * 删除临时文件
 * @Author:         cyb
 * @CreateDate:     2019/3/15 14:23
 */
@Component
@Slf4j
public class DeleteTmpFileTask {
    @Value("${attach.dir}")
    private String path;

    public void deleteTmpFile(){
        File fileDirectory = new File(path+"tmp");
        if(fileDirectory.isDirectory()){
            File[] files = fileDirectory.listFiles(new ImplementFilter());
            for (File file:files) {
                log.debug("删除文件：{}",file.getName());
                file.delete();
            }
        }
    }


    public class ImplementFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            BasicFileAttributes bAttributes = null;
            try {
                bAttributes = Files.readAttributes(pathname.toPath(),
                        BasicFileAttributes.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long createTime = bAttributes.creationTime().toMillis();
            long now = System.currentTimeMillis();
            if(pathname.isDirectory()){
                return false;
            }else{
                if( TimeUnit.MILLISECONDS.toHours(now-createTime)> TimeUnit.HOURS.toHours(2)){
                    return true;
                }
            }
            return false;
        }
    }
}
