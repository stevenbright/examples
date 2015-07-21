
## What is it?

An application that produces log records by using simple REPL interface.

This application is useful to test log analyzers and other tools that you might have for your file logs.

Sample commands:

```
logDebug
logInfo
logInfo @lapse timeDelta=155, succeeded=true
logWarn
logError
help
quit
```

## How to run in dev mode

```
mvn exec:java
```

## Compile and run self-contained JAR

First do:

```
mvn package -Passembly
```

Then something like:

```
rlwrap java -Dapp.logback.rootLogId=ROLLING_FILE -Dapp.logback.logBaseName=/tmp/slp1-log -jar target/sample-log-producer-1.0-SNAPSHOT.jar
```

Tail logs in separate window:

```
tail -f /tmp/slp1-log.log
```

## Sample Big Message

(~400 bytes, to exhaust log file quickly)

```
logInfo Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at lobortis urna. Sed suscipit mi vitae tempus volutpat. Sed vel augue pretium, gravida lectus eu, aliquam dolor. Morbi tellus dui, volutpat eget viverra aliquet, commodo vitae nunc. Aliquam mauris dolor, semper eget pellentesque sit amet, auctor sed dolor. Duis pharetra finibus tristique. Suspendisse molestie eget magna at convallis.
```

680 bytes error message:

```
logError Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis at lobortis urna. Sed suscipit mi vitae tempus volutpat. Sed vel augue pretium, gravida lectus eu, aliquam dolor. Morbi tellus dui, volutpat eget viverra aliquet, commodo vitae nunc. Aliquam mauris dolor, semper eget pellentesque sit amet, auctor sed dolor. Duis pharetra finibus tristique. Suspendisse molestie eget magna at convallis. Integer fringilla sit amet libero in finibus. Aliquam fringilla at quam a mattis. Aenean vel lacus nec magna vestibulum tempor dapibus at est. Mauris iaculis elit at leo varius, id suscipit est placerat. Suspendisse fermentum leo at mauris placerat, in molestie tortor ullamcorper.
```
