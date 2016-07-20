
## Demo REST call

```
curl 127.0.0.1:8080/sample/user
```

should result in:

```
Greetings, user!
```

Try also ``curl 127.0.0.1:8080/user/1`` to see JSON output.

# Sample Perf Run

5 concurrent requests:

```
ab -n 1000 -c 5 http://127.0.0.1:8080/user/1
```
