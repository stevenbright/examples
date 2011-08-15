package com.mysite.gdatatest;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;

/**
 * Entry point
 */
public final class App {

    private static class GetAllCalendars implements CalendarServiceCallback {
        public void accept(CalendarService service) throws IOException, ServiceException {
            final URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/owncalendars/full");
            final CalendarFeed resultFeed = service.getFeed(feedUrl, CalendarFeed.class);

            System.out.println("Calendars you own:");
            System.out.println();
            for (int i = 0; i < resultFeed.getEntries().size(); i++) {
                final CalendarEntry entry = resultFeed.getEntries().get(i);
                System.out.println("\t" + entry.getTitle().getPlainText());
            }
        }
    }

    private static class GetEventFeed implements CalendarServiceCallback {
        public void accept(CalendarService calendarService) throws IOException, ServiceException {
            final URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/private/full");

            final CalendarQuery calendarQuery = new CalendarQuery(feedUrl);
            calendarQuery.setMinimumStartTime(DateTime.parseDateTime("2011-04-16T00:00:00"));
            calendarQuery.setMaximumStartTime(DateTime.parseDateTime("2011-09-24T23:59:59"));

            final CalendarEventFeed resultFeed = calendarService.query(calendarQuery, CalendarEventFeed.class);
            int i = 0;
            for (final CalendarEventEntry eventEntry : resultFeed.getEntries()) {
                final StringBuilder s = new StringBuilder();
                s.append("event #");
                s.append(++i);
                s.append(": '");
                s.append(eventEntry.getTitle().getPlainText());
                s.append("', content: '");
                s.append(eventEntry.getContent().toString());
                s.append("'.");

                System.out.println(s.toString());
            }
        }
    }

    public static void main(String[] args) {
        try {
            final CalendarServiceCallback callback;
            if (args.length < 100) {
                callback = new GetEventFeed();
            } else {
                callback = new GetAllCalendars();
            }

            OAuthClientScenarioRunner.run(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
