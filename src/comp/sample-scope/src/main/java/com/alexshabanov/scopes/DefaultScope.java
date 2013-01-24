package com.alexshabanov.scopes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Default mutable, non-thread safe implementation of {@link Scope} interface.
 * @param <TElement> Element type which should be the descendant of {@link ScopedElement} interface.
 * @author Alexander Shabanov
 */
public final class DefaultScope<TElement extends ScopedElement> implements Scope<TElement> {

    /**
     * Nil entry, there should be only one
     */
    private static final EntryImpl NIL = new EntryImpl();

    /**
     * Initial size of hash table
     */
    private static final int INITIAL_HASH_TABLE_SIZE = 16;

    /**
     * Represents a hash table array.
     * Length of this array, *must* always be power of two
     */
    private EntryImpl<TElement>[] entries;

    /**
     * Mask, that should be applied to the particular hash code to quickly lookup the target element
     */
    private int hashMask;

    /**
     * Total entry count, including the ones clashed with the ones on top of entries array
     */
    private int entryCount;

    /**
     * Represents list of all the entries in this scope
     */
    private EntryImpl<TElement> headEntry;

    /**
     * Joined scopes
     */
    private List<Scope<TElement>> joinedScopes = Collections.emptyList();

    public DefaultScope() {
        this.headEntry = nil();
    }

    @Override public void put(TElement element) {
        put(element, this);
    }

    @Override public void put(TElement element, Scope<TElement> scope) {
        growIfNeeded();

        final int pos = element.getName().hashCode() & hashMask;
        final EntryImpl<TElement> clashedEntry = entries[pos];
        final EntryImpl<TElement> newEntry = new EntryImpl<TElement>(element, clashedEntry, headEntry, scope);

        entries[pos] = newEntry;
        headEntry = newEntry;
        ++entryCount;

        for (final Scope<TElement> joinedScope : joinedScopes) {
            joinedScope.put(element, scope);
        }
    }

    @Override public void join(Scope<TElement> childScope) {
        if (childScope == this) {
            throw new IllegalArgumentException("Joining scope to self is not valid");
        }

        if (!(joinedScopes instanceof ArrayList)) {
            joinedScopes = new ArrayList<Scope<TElement>>();
        }

        if (joinedScopes.contains(childScope)) {
            throw new IllegalStateException("Duplicate scope " + childScope);
        }

        joinedScopes.add(childScope);
    }

    @Override public void tearOff(Scope<TElement> childScope) {
        final int index = joinedScopes.indexOf(childScope);
        if (index < 0) {
            throw new IllegalStateException("The specified scope is not joined to the current one");
        }

        // ok, element found
        joinedScopes.remove(index);
    }

    @Override public Entry<TElement> get(String name) {
        if (entries != null) {
            return findLocalEntry(name);
        }

        return nil();
    }

    @Override public Iterable<TElement> getAll() {
        return new SiblingEntryIterable<TElement>(headEntry);
    }


    private static final class SiblingEntryIterator<TElement extends ScopedElement> implements Iterator<TElement> {

        private EntryImpl<TElement> currentEntry;

        public SiblingEntryIterator(EntryImpl<TElement> currentEntry) {
            this.currentEntry = currentEntry;
        }

        @Override public boolean hasNext() {
            return !currentEntry.isNil();
        }

        @Override public TElement next() {
            if (currentEntry.isNil()) {
                throw new IllegalStateException();
            }
            final TElement result = currentEntry.getElement();
            currentEntry = currentEntry.sibling;

            return result;
        }

        @Override public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SiblingEntryIterable<TElement extends ScopedElement> implements Iterable<TElement> {
        private final EntryImpl<TElement> entry;

        public SiblingEntryIterable(EntryImpl<TElement> entry) {
            this.entry = entry;
        }

        @Override public Iterator<TElement> iterator() {
            return new SiblingEntryIterator<TElement>(entry);
        }
    }

    private Entry<TElement> findLocalEntry(String name) {
        assert name != null;
        EntryImpl<TElement> result = entries[name.hashCode() & hashMask];
        while (!result.isNil() && !result.getElement().getName().equals(name)) {
            result = result.clashedEntry;
        }

        return result;
    }

    // typesafe nil wrapper
    @SuppressWarnings("unchecked")
    private static <TElement extends ScopedElement> EntryImpl<TElement> nil() {
        return NIL;
    }

    private void initializeEntries(int size) {
        assert ((size - 1) & size) == 0 : "The initialization size must not be zero and must be power of two";

        // initialize entries array
        @SuppressWarnings("unchecked")
        final EntryImpl<TElement>[] newEntries = new EntryImpl[size];
        for (int i = 0; i < newEntries.length; ++i) {
            newEntries[i] = nil();
        }
        entries = newEntries;

        // NB: the hash mask trick won't work if the hash table size is not a power of two
        hashMask = size - 1;

        // reset head entry block and entry counter
        headEntry = nil();
        entryCount = 0;
    }

    private void growIfNeeded() {
        if (entries == null) {
            // initial put
            initializeEntries(INITIAL_HASH_TABLE_SIZE);
            return;
        }

        // grow condition: total count of entries must exceed the entries table size before grow
        if (entryCount < entries.length) {
            return;
        }

        // save 'old' elements
        EntryImpl<TElement> savedHeadEntry = headEntry;

        // clear top level hash table entries
        initializeEntries(entries.length * 2);

        // put all the elements again
        for (; !savedHeadEntry.isNil(); savedHeadEntry = savedHeadEntry.sibling) {
            put(savedHeadEntry.element);
        }
    }


    /**
     * Represents entry in the hash table.
     * @param <TElement> Element, this entry represents.
     */
    private static final class EntryImpl<TElement extends ScopedElement> implements Entry<TElement> {

        /**
         * Element, associated with this entry
         */
        private final TElement element;

        /**
         * Entry with the element, which hashCode clashes with the element in this entry
         */
        private EntryImpl<TElement> clashedEntry;

        /**
         * Points to the next element within current scope.
         * Needed to support the {@link #getAll()} method.
         */
        private EntryImpl<TElement> sibling;

        private final Scope<TElement> ownerScope;


        @Override public boolean isNil() {
            return element == null;
        }

        @Override public TElement getElement() {
            if (isNil()) {
                throw new IllegalStateException();
            }
            return element;
        }

        @Override
        public Scope<TElement> getOwnerScope() {
            return ownerScope;
        }

        /**
         * Constructor for non-nil entries only.
         *
         * @param element Element, associated with this entry.
         * @param clashedEntry Element with the same hash code that clashes with this one.
         * @param sibling Next sibling of this entry in the current scope.
         */
        public EntryImpl(TElement element,
                         EntryImpl<TElement> clashedEntry,
                         EntryImpl<TElement> sibling,
                         Scope<TElement> ownerScope) {
            assert element != null && clashedEntry != null && ownerScope != null;
            this.element = element;
            this.clashedEntry = clashedEntry;
            this.sibling = sibling;
            this.ownerScope = ownerScope;
        }

        /**
         * Constructor for nil entries only.
         * Normally there should be only one nil entry.
         */
        protected EntryImpl() {
            this.element = null;
            this.ownerScope = null;
        }

        /**
         * Proceeds to the next entry.
         * The caller party *must* be pretty sure that the entry is not a nil prior to calling this method.
         *
         * @return Next entry, return nil entry if there is no more entries
         */
        public Entry<TElement> next() {
            EntryImpl<TElement> result = clashedEntry;
            while (!result.isNil() && !result.getElement().getName().equals(element.getName())) {
                result = result.clashedEntry;
            }

            return result;
        }

        @Override
        public String toString() {
            return "Entry#" + (isNil() ? "NIL" : "<" + getElement() + ">");
        }
    }
}
