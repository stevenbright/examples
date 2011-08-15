package com.truward.hiberdemo.services;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utilizes hibernate session
 */
public final class HibernateAccessor {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static final Logger log = Logger.getLogger(HibernateAccessor.class);

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Exception e) {
            log.error(e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private HibernateAccessor() {}
}
