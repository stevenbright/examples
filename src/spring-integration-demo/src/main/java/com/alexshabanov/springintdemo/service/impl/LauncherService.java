package com.alexshabanov.springintdemo.service.impl;

import com.alexshabanov.springintdemo.service.CommService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LauncherService implements Runnable {
    @Resource
    private CommService commService;

    @Override
    public void run() {
        System.out.println("comm service timeout: " + commService.getTimeoutMillis());
    }
}
