package com.truward.swd.util;


import com.truward.swd.domain.Contact;

public final class DataUtil {

    private static final String firstnames[] = new String[] {
            "Firstname",
            "Alice",
            "Bob",
            "Cavin",
            "Derek",
    };

    private static final String lastnames[] = new String[] {
            "Lastname",
            "Mc Guaire",
            "Finch",
            "Ulrich",
            "Anderson",
    };

    private static final String phones[] = new String[] {
            "+30001002030",
            "+30801234567",
            "+30801234568",
            "+30801234569",
            "+30801234560",
    };

    private static final String emails[] = new String[] {
            "username@domain.dom",
            "alice@mail.ru",
            "bob@yahoo.com",
            "cavin_ulrich@mail.com",
            "derek@abc.com",
    };


    public static Contact createContact(int index) {
        final Contact contact = new Contact();
        contact.setFirstname(firstnames[index]);
        contact.setLastname(lastnames[index]);
        contact.setEmail(emails[index]);
        contact.setTelephone(phones[index]);
        return contact;
    }


    /**
     * Hidden ctor
     */
    private DataUtil() {}
}
