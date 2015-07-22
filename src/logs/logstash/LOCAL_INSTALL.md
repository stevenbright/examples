Local Install
=============

Download elasticsearch and logstash.

Start elasticsearch first. In order to do that - modify elasticsearch config to disable clustering.
Use the following ``cluster.name: localhost`` in your ``$ELASTICSEARCH/config/elasticsearch.yml`` then do ``./bin/elasticsearch``.

Then test logstash. Write sample configuration file ``sample/stdin-hello.conf``:

```
input { stdin { } }

filter {
  grok {
    match => { "message" => "%{COMBINEDAPACHELOG}" }
  }
  date {
    match => [ "timestamp" , "dd/MMM/yyyy:HH:mm:ss Z" ]
  }
}

output {
  elasticsearch {
    host => "127.0.0.1"
    protocol => "http"
    port => "9200"
  }
  stdout { codec => rubydebug }
}
```

then start your logstash instance (remember, you should have elasticsearch running on your localhost before doing so): ``$LOGSTASH/bin/logstash -f sample/stdin-hello.conf``.

NOTE: *NEVER* use http protocol for communicating with elasticsearch in production. This is for dev purposes only!

Once logstash started, paste the following to CLI:

```
127.0.0.1 - - [11/Dec/2013:00:01:45 -0800] "GET /xampp/status.php HTTP/1.1" 200 3891 "http://cadenza/xampp/navi.php" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:25.0) Gecko/20100101 Firefox/25.0"
```

Then query elasticsearch to see that you're actually received this message:

```
curl -X GET 'http://127.0.0.1:9200/_search?q=verb:GET&size=5&pretty=true'
```



## Links

* https://www.elastic.co/products/elasticsearch
* https://www.elastic.co/products/logstash
* https://www.elastic.co/guide/en/logstash/current/config-examples.html
* http://okfnlabs.org/blog/2013/07/01/elasticsearch-query-tutorial.html

More on logstash+elasticsearch configuration:

* http://stackoverflow.com/questions/17046047/logstash-with-elasticsearch


