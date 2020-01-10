package com.omg.controller;

import com.omg.annotation.CurrentUser;
import com.omg.annotation.LogInterface;
import com.omg.domain.result.Result;
import com.omg.domain.vo.CurrentUserInfo;
import com.omg.entity.User;
import com.omg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@Validated
@Api(tags = "用户相关接口")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @LogInterface(value = "查询%s的信息")
    @GetMapping("/select/user/{name}")
    public Result<User> getUser(@PathVariable String name){
        logger.debug("用户名：{}",name);
        return userService.findByName(name);
    }

    @GetMapping("/select/user1")
    public String getUser1(){
        return "test";
    }

    @PostMapping(value = "/insert/user")
    public String insertUser(@CurrentUser CurrentUserInfo currentUser, @RequestBody @Valid User userDto, BindingResult result){
        System.out.println(currentUser.toString());
        return userService.insertUser(userDto);
    }

    @PostMapping(value = "/excelImport")
    public void importFile(@RequestParam MultipartFile file){
        userService.importFile(file);
    }

    @GetMapping(value = "/download/{id}")
    public void downloadAttach(HttpServletResponse response,@PathVariable Integer id) throws IOException {
//        File file = new File("D:\\upload\\official\\渠道客户_a3bef8702a6b491b873510d2f11ac260.xls");
//        FileInputStream inputStream = new FileInputStream(file);;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        //res.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" +  java.net.URLEncoder.encode("test.xls", "UTF-8"));

        try {
            FileInputStream fis = new FileInputStream("D:\\upload\\official\\渠道客户_a3bef8702a6b491b873510d2f11ac260.xls");
            byte[] content = new byte[fis.available()];
            fis.read(content);
            fis.close();

            ServletOutputStream sos = response.getOutputStream();
            sos.write(content);

            sos.flush();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    public void uipload(@RequestParam("file") MultipartFile file){
        userService.upload(file);
    }
}
