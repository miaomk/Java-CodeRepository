package com.techwells.wumei.controller;


import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Administrator
 */
@RestController
public class FileController {

    /**
     * 文件上传 接口
     *
     * @param files 文件
     * @return ResultInfo
     */
    @RequestMapping(value = "/file/imageUpload")
    public @ResponseBody
    Object imageUpload(@RequestParam(value = "file", required = false) MultipartFile[] files,
                       @RequestParam(value = "filterName", required = false) String filterName) {
        ResultInfo rsInfo = new ResultInfo();

        StringBuilder fileUrl = new StringBuilder();
        // 处理图片
        if (files != null && files.length > 0) {

            for (MultipartFile file : files) {
                try {
                    if (StringUtil.isEmpty(filterName)) {

                        fileUrl.append(FileUtil.uploadFile(file, "image")).append(",");
                    } else {
                        fileUrl.append(FileUtil.uploadFile(file, filterName)).append(",");
                    }
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!"".equals(fileUrl.toString())) {
                fileUrl = new StringBuilder(fileUrl.substring(0, fileUrl.length() - 1));
            }
        }

        rsInfo.setMessage("文件上传成功！");
        rsInfo.setData(fileUrl.toString());
        return rsInfo;
    }

}
