
# Overview

Demonstrates performing manual SQL migration to the completely new schema.

# H2 cheats

## Initialize

Create a property override file:

```
eol.dao.dataSource.url=jdbc:h2:/tmp/lairedb
```

... and use it by referring to it in JVM properties: ``-Dapp.properties.override=file:/Path/to/eol.properties``.

## Import data

```
java -cp ~/.m2/repository/com/h2database/h2/1.4.183/h2-1.4.183.jar org.h2.tools.RunScript -url jdbc:h2:/tmp/lairedb -user sa -script booklib-website/src/main/resources/sql/h2/book/book-schema.sql

java -cp ~/.m2/repository/com/h2database/h2/1.4.183/h2-1.4.183.jar org.h2.tools.RunScript -url jdbc:h2:/tmp/lairedb -user sa -script booklib-website/src/main/resources/sql/h2/book/book-fixture.sql

java -cp ~/.m2/repository/com/h2database/h2/1.4.183/h2-1.4.183.jar org.h2.tools.RunScript -url jdbc:h2:/tmp/lairedb -user sa -script eolaire-server/src/main/resources/eolaireService/sql/eolaire-schema.sql
```

## Connect

```
rlwrap java -cp ~/.m2/repository/com/h2database/h2/1.4.183/h2-1.4.183.jar org.h2.tools.Shell -url jdbc:h2:/tmp/lairedb -user sa
```

