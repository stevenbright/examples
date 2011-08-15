package org.microblog.exposure.model;

/**
 * Represents thematical tag.
 */
public class BlogTag {
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogTag)) return false;

        BlogTag blogTag = (BlogTag) o;

        if (id != blogTag.id) return false;
        if (name != null ? !name.equals(blogTag.name) : blogTag.name != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
