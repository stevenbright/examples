package com.alexshabanov.ritest.model;

import java.util.Date;

public final class WarmGreeting {
    private String greeting;
    private String origin;
    private Date created;
    private long quality;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getQuality() {
        return quality;
    }

    public void setQuality(long quality) {
        this.quality = quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarmGreeting warmGreeting = (WarmGreeting) o;
        return quality == warmGreeting.quality && !(created != null ?
                !created.equals(warmGreeting.created) : warmGreeting.created != null) &&
                !(greeting != null ? !greeting.equals(warmGreeting.greeting) : warmGreeting.greeting != null) &&
                !(origin != null ? !origin.equals(warmGreeting.origin) : warmGreeting.origin != null);

    }

    @Override
    public int hashCode() {
        int result = greeting != null ? greeting.hashCode() : 0;
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (int) (quality ^ (quality >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "WarmGreeting{" +
                "greeting='" + getGreeting() + '\'' +
                ", origin='" + getOrigin() + '\'' +
                ", created=" + getCreated() +
                ", quality=" + getQuality() +
                '}';
    }
}
