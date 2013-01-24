package com.alexshabanov.scopes;

/**
 * Represents scope of certain symbols.
 * </p>
 * This class is similar to {@link java.util.Map} with the exception that key-value pair is solely represented by
 * the scoped element that can be placed within current scope.
 * In this case the key is the name, naturally associated with the given element and key is the element itself.
 * </p>
 * Null entries are not allowed. It is also expected, that searching across this scope will be performed by using
 * non-null names only.
 * </p>
 * The instances of this class are not thread safe.
 * @param <T> Element, this scope can operate with.
 * @author Alexander Shabanov
 */
public interface Scope<T> {

    /**
     * Represents element's search result.
     *
     * @param <T> Element associated with the current scope entry
     */
    interface Entry<T> {
        /**
         * Checks whether an entry is nil or not.
         * Querying elements for nil entries results in exception
         *
         * @return {@code true} if the entry is nil, {@code false} otherwise
         */
        boolean isNil();

        /**
         * Returns entry that follows this one.
         *
         * @return Non-null entry instance.
         */
        Entry<T> next();

        /**
         * Returns current element when cursor is in the readable state.
         * Throws {@link IllegalStateException} if cursor is not in the readable state.
         *
         * @return Non-null element associated with the entry this cursor points to
         */
        T getElement();

        /**
         * @return Owner scope.
         */
        Scope<T> getOwnerScope();
    }

    /**
     * Puts element in this scope.
     *
     * @param element Non-null element to be placed in this scope, must not be null.
     */
    void put(T element);

    void put(T element, Scope<T> scope);

    void join(Scope<T> childScope);

    void tearOff(Scope<T> childScope);

    /**
     * Finds element by name by searching in this scope and in the joined ones.
     *
     * @param name Element's name, must not be null.
     * @return Non-null entry
     */
    Entry<T> get(String name);

    /**
     * Returns all the elements within the current scope including added by an external scope
     *
     * @return Non-null entry
     */
    Iterable<T> getAll();
}
