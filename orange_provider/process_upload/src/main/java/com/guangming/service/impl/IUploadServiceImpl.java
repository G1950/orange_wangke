package com.guangming.service.impl;

import com.guangming.service.IUploadService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class IUploadServiceImpl implements IUploadService {
    @Value("${upload.imgDir}")
    private String baseDir;
    @Value("${upload.avatarUrl}")
    private String baseUrl;

    @Override
    public Result uploadAvatar(MultipartFile file, String id) {
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName;
        try {
            suffix = suffix.toLowerCase();
            if (!(suffix.equals(".png") || suffix.equals(".jpg") || suffix.equals(".jpeg"))) {
                log.error("仅支持png|jpg格式图片");
                return Result.build(ResultEnum.UPLOAD_IMG_WARN);
            }
            File dir = new File(baseDir + "/" + id);
            System.out.println(dir.getAbsolutePath());
            boolean result = true;
            if (!dir.exists()) {
                result = dir.mkdirs();
            }
            if (!result) {
                log.error("目录创建失败");
                return Result.build(ResultEnum.UPLOAD_IMG_FAIL);
            }
            fileName = id + suffix;
            File newFile = new File(dir + "/" + fileName);
            file.transferTo(newFile);
            log.info("图片上传成功");
            return Result.build(ResultEnum.UPLOAD_IMG_SUCCESS, baseUrl + "/" + id + "/" + fileName);
        } catch (IOException e) {
            log.error("图片上传失败：" + e.getMessage());
            return Result.build(ResultEnum.UPLOAD_IMG_FAIL);
        }
    }
}
