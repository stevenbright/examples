
## Dev Mode

System properties:

```
-Dbrikar.settings.gracefulShutdownMillis=100 -Dbrikar.settings.port=8081 -Dbrikar.settings.path=file:/Your/path/to/usus-localdev.properties
```

* brikar.settings.port==8081 - this is needed to run this demo along with ``user-service-server`` on the same machine (both servers use 8080 port as the default one).
* brikar.settings.gracefulShutdownMillis==100 - don't wait for any incoming connection to process on shutdown (important for production but not needed in dev mode).

