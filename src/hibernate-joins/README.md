
# Overview

Demonstrates hibernate usage.
See also http://www.journaldev.com/2882/hibernate-tutorial-for-beginners-using-xml-annotations-and-property-configurations


# Issues

* hibernate3, hibernate4, hibernate5 are all different in major APIs, hard to use with Spring ORM, easy to make a mistake by using helper spring's class from the wrong package (e.g. AnnotationConfiguration which was deprecated in Hibernate >4).
* HibernateTransactionManager - ``Caused by: java.lang.NoSuchMethodError: org.hibernate.engine.spi.SessionFactoryImplementor.getConnectionProvider()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;``
* sessionFactory is a huge pain in the ass to work with.
* Spring 3.x can't be used with the latest hibernate (e.g. ver. 5.0.1.Final).
* Can't have same objects representing the same record in the table - e.g. ``The following - won't work - 'org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session: [com.alexshabanov.sample.hibernateJoins.model.Person#100]'``.