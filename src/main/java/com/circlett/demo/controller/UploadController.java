package com.circlett.demo.controller;

import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.InetAddress;



//上传图片
@Slf4j
@RestController
@Configuration
public class UploadController {

    @Value("${server.port}")
    private String port;
    @Value("${uploadPathImg}")
    private String uploadPathImg;
    @Value("${ip}")
    private String ip;

    //获取当前IP地址
    public String getIp() {
        InetAddress localhost = null;
        try {

            localhost =InetAddress.getLocalHost();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return localhost.getHostAddress();
    }



    @PostMapping("/user/upload")
    public Result upload(@RequestParam("photos") MultipartFile[] photos)  {
        try {
            //服务器获取的是 127.0.0.1   "121.4.90.69"                        grtIP
            StringBuilder fileAddress = new StringBuilder().append("http://" +ip+ ":" + port + "/static/");
            String[] psth = new String[photos.length];
            if (photos.length > 0) {
                int i = 0;
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) {
                        //原来的文件名
                        String originalFilename = photo.getOriginalFilename();
                        //新的文件名:uuid+后缀名
                        String generateFileName = UUIDUtil.getUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
                        //存入数组
                        psth[i++] = fileAddress + generateFileName;
                        photo.transferTo(new File(uploadPathImg + generateFileName));

                    }
                }
            }
            return Result.succ(psth);
        }catch (IOException e){
            log.info(e.getMessage());
            return   Result.fail("上传失败");
        }

    }

}
