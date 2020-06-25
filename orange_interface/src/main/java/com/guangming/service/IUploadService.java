package com.guangming.service;

import com.guangming.utils.Result;
import org.springframework.web.multipart.MultipartFile;

//文件上传接口
public interface IUploadService {
    //上传头像
    Result uploadAvatar(MultipartFile file, String id);
}
