package com.alexshabanov.txtest.service.support;

import com.alexshabanov.txtest.service.AppService;
import org.springframework.stereotype.Service;

/**
 * Application service.
 */
@Service
public final class DefaultAppService implements AppService {

    @Override
    public void run(String[] args) {
        System.out.println("default app service run!");
    }
}
