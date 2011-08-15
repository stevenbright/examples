
JDO sample that illustrates runtime enhancement.
NB: enhancer processes only the JAVA classes (generated *.class files), so it works only after second launch
when loader will get on with the updated class definitions.

Key points:

1) All the persistable classes must be marked with an attribute @PersistentCapable

2) mappings are specified in the *.jdo file(s)

3) in order to show the results, run:

mvn clean compile
mvn exec:java
mvn exec:java
- (yes, twice in the very same order)
