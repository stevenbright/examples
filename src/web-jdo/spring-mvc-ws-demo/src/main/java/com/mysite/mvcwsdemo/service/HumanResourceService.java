package com.mysite.mvcwsdemo.service;

import com.mysite.mvcwsdemo.model.HolidayRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HumanResourceService {
    private final List<HolidayRequest> requests = new ArrayList<HolidayRequest>();

    public List<HolidayRequest> getRequests() {
        return requests;
    }

    public void bookHoliday(Date startDate, Date endDate, String name) {
        final HolidayRequest request = new HolidayRequest();
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setName(name);
    }
}
