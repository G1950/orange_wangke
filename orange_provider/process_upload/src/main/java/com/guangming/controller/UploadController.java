package com.guangming.controller;

import com.guangming.service.IUploadService;
import com.guangming.utils.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Resource
    private IUploadService uploadService;

    //上传图片
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadAvatar(@RequestPart(value="file") MultipartFile file, @RequestParam("id") String id) {
        return uploadService.uploadAvatar(file,id);
    }
}
