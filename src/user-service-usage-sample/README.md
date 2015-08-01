
## Dev Mode

```
--graceful-shutdown-millis 100 --port 8081
```

* port==8081 - this is needed to run this demo along with ``user-service-server`` on the same machine (both servers use 8080 port as the default one).
* graceful-shutdown-millis==100 - don't wait for any incoming connection to process on shutdown (important for production but not needed in dev mode).

