package com.alexshabanov.scopes;

/**
 * Represents an arbitrary scoped symbol.
 */
public interface ScopedElement {

    /**
     * @return Name, associated with this element, must not be null.
     */
    String getName();
}
