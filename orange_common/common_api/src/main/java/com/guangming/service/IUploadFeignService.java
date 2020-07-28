package com.guangming.service;

import com.guangming.configbeans.FeignConfig;
import com.guangming.configbeans.FeignMultipartSupportConfig;
import com.guangming.service.impl.IUploadFeignServiceImpl;
import com.guangming.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "ORANGE-PROVIDER-UPLOAD", configuration = {FeignMultipartSupportConfig.class, FeignConfig.class}, fallbackFactory = IUploadFeignServiceImpl.class)
public interface IUploadFeignService {
    @PostMapping(value = "/upload/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result uploadImg(@RequestPart(value = "file") MultipartFile file, @RequestParam("id") String id);
}
