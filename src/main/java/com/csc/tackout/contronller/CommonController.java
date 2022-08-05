package com.csc.tackout.contronller;

import com.csc.tackout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * ClassName:CommonController
 * Package:com.csc.tackout.contronller
 * Description: 文件的上传和下载
 *
 * @Date:4/8/2022 14:42
 * @Author:2394279444@qq.com
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${csc.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求后，临时文件就会删除
        log.info(file.toString());
        //原始文件名
        String originalFilename = file.getOriginalFilename();
       String suffix =  originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID随机生成文件名，防止文件名重复而导致覆盖
        String fileName= UUID.randomUUID().toString()+suffix;
        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //如果不存在 ，就创建一个目录
            dir.mkdirs();
        }
        try {
            //文件转存到指定位置
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);

    }

    /**
     * 文件下载
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        //输入流，通过输入流读取内容
        FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
        //输出流，通过输出流将文件写回浏览器，在浏览器显示图片
        ServletOutputStream outputStream = response.getOutputStream();

        response.setContentType("image/jpeg");

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len=fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }
        //关闭资源
        fileInputStream.close();
        outputStream.close();
    }
}
