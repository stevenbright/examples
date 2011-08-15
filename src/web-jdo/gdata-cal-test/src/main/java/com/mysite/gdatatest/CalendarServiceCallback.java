package com.mysite.gdatatest;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Operates on the initialized calendar service.
 */
public interface CalendarServiceCallback {
    void accept(CalendarService calendarService) throws IOException, ServiceException;
}
