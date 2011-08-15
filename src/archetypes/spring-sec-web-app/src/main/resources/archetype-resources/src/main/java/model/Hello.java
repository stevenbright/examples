package ${package}.model;

import java.util.Date;

/**
 * Hello model.
 */
public class Hello {
    private String greeting;

    private String origin;

    private Date created;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append("{greeting='").append(greeting).append('\'');
        sb.append(", origin='").append(origin).append('\'');
        sb.append(", created=").append(created);
        sb.append('}');
        return sb.toString();
    }
}
