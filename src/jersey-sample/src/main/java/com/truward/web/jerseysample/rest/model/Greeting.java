package com.truward.web.jerseysample.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Represents stand-alone greeting.
 * The annotation XmlRootElement lets Jersey understand that this is the serializable class.
 */
@XmlRootElement
public final class Greeting {
    private String username;

    private String greeting;

    private Date created;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
