
Simple JDO sample.

Key points:

1) All the persistable classes must be marked with an attribute @PersistentCapable

2) datanucleus.properties is where all the properties including the data source are stored

3) mappings are specified in the *.jdo file(s)

4) in order to show the results, run:

mvn clean compile exec:java
