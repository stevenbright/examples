package com.alexshabanov.ritest.service;

import com.alexshabanov.ritest.model.WarmGreeting;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Service
public final class GreetingServiceImpl implements GreetingService {

    private String localHostName;

    public GreetingServiceImpl() {
        try {
            this.localHostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new BeanInitializationException("Can't initialize local host name", e);
        }
    }

    @Override
    public String getHello(String subject) {
        return "WarmGreeting, " + subject + " from " + localHostName;
    }

    @Override
    public WarmGreeting createGreeting(String origin, int warmLevel, double sincerity) {
        final WarmGreeting result = new WarmGreeting();
        result.setQuality(warmLevel + (long) (100L * sincerity));
        result.setCreated(new Date());
        result.setGreeting("WarmGreeting, " + origin + " from " + localHostName);
        result.setOrigin(origin);
        return result;
    }
}