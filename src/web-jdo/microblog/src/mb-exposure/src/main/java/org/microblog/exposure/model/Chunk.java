package org.microblog.exposure.model;

import java.util.Collection;

/**
 * Represents chunk of certain data.
 */
public final class Chunk <E> {
    private int count;

    private Collection<E> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Collection<E> getItems() {
        return items;
    }

    public void setItems(Collection<E> items) {
        this.items = items;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chunk)) return false;

        Chunk chunk = (Chunk) o;

        if (count != chunk.count) return false;
        if (items != null ? !items.equals(chunk.items) : chunk.items != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }
}
