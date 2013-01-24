package com.alexshabanov.scopes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public final class ScopeTest {
    private final String name = "elem";
    private Scope<Sym> scope;

    @Before
    public void initScope() {
        scope = new DefaultScope<Sym>();
    }

    @Test
    public void shouldFindNothing() {
        assertFalse(scope.getAll().iterator().hasNext());
        assertTrue(scope.get(name).isNil());
    }

    @Test
    public void shouldAddAndFindElement() {
        final Sym e1 = new Sym(name);
        scope.put(e1);
        assertThat(asList(scope.get(name)), is(Collections.singletonList(e1)));
        assertThat(ImmutableList.copyOf(scope.getAll()), is(Collections.singletonList(e1)));
    }

    @Test
    public void shouldAddAndFindMultipleElements() {
        final Sym e1 = new Sym(name, 1L);
        assertTrue(scope.get(e1.getName()).isNil());

        scope.put(e1);
        assertThat(asList(scope.get(name)), is(Arrays.asList(e1)));

        final Sym e2 = new Sym(name, 2L);
        final Sym e3 = new Sym(name, 3L);

        scope.put(e2);
        scope.put(e3);

        assertThat(Sets.newHashSet(asList(scope.get(name))), is(Sets.newHashSet(e1, e2, e3)));
    }

    @Test
    public void shouldReturnAllElements() {
        final Sym e1 = new Sym(name, 1L);
        final Sym e2 = new Sym(name, 2L);
        final Sym e3 = new Sym(name + "_3", 3L);
        scope.put(e1);
        scope.put(e2);
        scope.put(e3);

        assertThat(Sets.newHashSet(asList(scope.get(name))), is(Sets.newHashSet(e1, e2)));
        assertThat(asList(scope.get(e3.getName())), is(Arrays.asList(e3)));
        assertThat(Sets.newHashSet(scope.getAll()), is(Sets.newHashSet(Arrays.asList(e1, e2, e3))));
    }

    @Test
    public void shouldWorkWithMultipleElements() {
        final int count = 1000;
        final Random random = new Random(1253245L);

        // add the test data
        final Map<String, List<Sym>> names = new HashMap<String, List<Sym>>();
        for (int i = 0; i < count; ++i) {
            final String name = randString(random);
            List<Sym> c = names.get(name);
            if (c == null) {
                c = new ArrayList<Sym>();
                names.put(name, c);
            }

            // should be able to find all the previous elements
            assertThat(Sets.newHashSet(asList(scope.get(name))), is(Sets.newHashSet(c)));

            final Sym sym = new Sym(name);
            scope.put(sym);
            c.add(sym);
        }

        // should be able to find all the new elements
        final List<Sym> collected = new ArrayList<Sym>(count);
        for (final Map.Entry<String, List<Sym>> e : names.entrySet()) {
            assertThat(Sets.newHashSet(asList(scope.get(e.getKey()))), is(Sets.newHashSet(e.getValue())));
            collected.addAll(e.getValue());
        }

        // ensure all the elements are presented in the scope
        assertThat(Sets.newHashSet(scope.getAll()), is(Sets.newHashSet(collected)));
    }

    private static String randString(Random random) {
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_$";
        final int length = random.nextInt(3) + 1;
        final StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }

    /**
     * Returns an list of elements this entry points to.
     *
     * @param entry Source entry, must not be null
     * @param <T> Element type
     * @return Non-null, immutable list of elements
     */
    public static <T> List<T> asList(Scope.Entry<T> entry) {
        final List<T> result = new ArrayList<T>();
        for (Scope.Entry<T> e = entry; !e.isNil(); e = e.next()) {
            result.add(e.getElement());
        }
        return ImmutableList.copyOf(result);
    }

    private static final class Sym implements ScopedElement {
        private final String name;
        private final Integer overridenHashCode;
        private final long token;

        public Sym(String name, Integer overridenHashCode, Long token) {
            assert name != null;
            this.name = name;
            this.overridenHashCode = overridenHashCode;
            this.token = token != null ? token : -1L;
        }

        public Sym(String name) {
            this(name, null, null);
        }

        public Sym(String name, long token) {
            this(name, null, token);
        }

        public long getToken() { return token; }

        @Override public String getName() { return name; }

        // NB: The default equals implementation SHALL BE used!!

        @Override public int hashCode() {
            if (overridenHashCode != null) {
                return overridenHashCode;
            }

            // in case of non-overriden hash code
            return name.hashCode() * 31 + (int)((token >>> 32) ^ token);
        }

        @Override public String toString() {
            return "Sym#" + hashCode() + "{name='" + name + "'" +
                    (overridenHashCode != null ? "', hashCode=" + overridenHashCode : "") +
                    (getToken() >= 0 ? ", token=" + getToken() : "") +
                    "}";
        }
    }
}
