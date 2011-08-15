package com.alexshabanov.webuploader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public final class UploadController {

    private int fileCounter = 0;

    @RequestMapping("/upload/index.html")
    public String login() {
        return "upload/index";
    }

    @RequestMapping(value = "/upload/upload.do", method = RequestMethod.POST)
    public String doUpload(@RequestParam("uploaded-file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.getSize() > 0) {
            ++fileCounter;

            for (int index = 0;; ++index) {
                final String suffix = (index == 0 ? "" : "." + index);
                final String fileName = multipartFile.getOriginalFilename() + suffix;

                final File file = new File(fileName);
                if (!file.exists()) {
                    multipartFile.transferTo(file);
                    break;
                }
            }

            return "redirect:/upload/index.html?filenum=" + fileCounter;
        } else {
            return "redirect:/upload/index.html?error=1";
        }
    }
}
