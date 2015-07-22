
## Install hekad

Mac OS X installation:

```
brew install Caskroom/cask/heka
```

Then run ``simple-log-producer``:

```
rlwrap java -Dapp.logback.rootLogId=ROLLING_FILE -Dapp.logback.logBaseName=/tmp/slp1 -jar target/sample-log-producer-1.0-SNAPSHOT.jar
```

Then run ``hekad``:

```
sudo hekad --config=sample-configs/echo.toml
```

Enter something in simple-log-producer and see this input appearing in hekad stdout.

## Payload Regex Decoder

See https://hekad.readthedocs.org/en/v0.9.2/config/decoders/payload_regex.html

Regex debugger: https://regoio.herokuapp.com/




