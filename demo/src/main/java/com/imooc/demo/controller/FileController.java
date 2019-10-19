package com.imooc.demo.controller;

import com.imooc.demo.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileController {

    /** 本地测试时使用的地址 */
        @Value("G:\\images")
        private String FILE_LOCAL_UPLOAD_ADDRESS;

    /**
     * 发布到服务器时使用的地址
     */
    //    @Value("/home/images")
//    @Value("/usr/local/tomcat/tomcat/webapps/images")
//    private String FILE_UPLOAD_ADDRESS;

    @RequestMapping("/fileUpload.do")
    @ResponseBody
    public Result imgUpload(MultipartFile file) {
        // 图片路径 安排   IMAGE_SERVER_PATH + 用户名 +"/类型/当前毫秒值.后缀"
        Result result = null;
        try {
            // 创建File
            File fileObj = createFile(file);
            // 将文件写入
            file.transferTo(fileObj);
            result = new Result(true, "上传成功", fileObj.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "上传失败:上传异常");
        }
        return result;
    }

    /**
     * 为 MultipartFile 生成File
     */
    private File createFile(MultipartFile file) {
        // images/年/月/日/毫秒值.文件后缀

        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.indexOf("."));

        StringBuffer sb = new StringBuffer();
        /*//        创建文件夹名   （在sb后面拼串）
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String format = simpleDateFormat.format(new Date());*/

        sb.append(FILE_LOCAL_UPLOAD_ADDRESS).append("/");

        File fileObj = new File(sb.toString());

        if (!fileObj.exists()) {
            fileObj.mkdirs();
        }
        sb.append(System.currentTimeMillis()).append(substring);
        return new File(sb.toString());
    }
}
