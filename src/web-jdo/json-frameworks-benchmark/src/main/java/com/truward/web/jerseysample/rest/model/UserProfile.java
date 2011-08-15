package com.truward.web.jerseysample.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Represents the user's profile.
 */
@XmlRootElement
public class UserProfile {
    private int id;

    private String displayName;

    private Date created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
