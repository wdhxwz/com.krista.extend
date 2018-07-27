package com.krista.extend.mvc.upload;

import com.krista.extend.mvc.views.JsonView;
import com.krista.extend.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
@RequestMapping("fileUpload")
public class FileUploadController {
    private String resourceName;
    public void setResourceName(String resourceName){
        this.resourceName = resourceName;
    }

    @RequestMapping
    public JsonView handleFileUpload(@RequestParam("file") MultipartFile file) {
        StorageService storageService = SpringUtil.getBean(StorageService.class,resourceName);

        return new JsonView(storageService.store(file));
    }
}
