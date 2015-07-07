package learn;

import learn.model.Contact;
import learn.model.Person;

public final class Learn {
    
    public static void main(String[] args) throws Exception {
        final Person p = new Person();
        p.setName("bob");
        p.setAge(28);
        System.out.println("person=" + p);

        final Contact contact = Contact.builder()
                .value("val")
                .type(1)
                .primary(true)
                .build();
        System.out.println("contact=" + contact);

        try {
            final Contact invContact = Contact.builder().value(null).build();
            System.out.println("[FAIL] invContact=" + invContact);
        } catch (NullPointerException e) {
            System.out.println("[OK] got NPE while trying to pass null to the obj field: " + e.getMessage());
        }
    }
}
