package com.alexshabanov.springintdemo.service.impl;

import com.alexshabanov.springintdemo.service.CommService;
import org.springframework.stereotype.Service;

@Service
public class CommServiceImpl implements CommService {

    @Override
    public int getTimeoutMillis() {
        return 500;
    }
}
