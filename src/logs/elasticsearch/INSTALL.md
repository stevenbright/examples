

# Local Install

In order to do that - modify elasticsearch config to disable clustering.
Use the following ``cluster.name: localhost`` in your ``$ELASTICSEARCH/config/elasticsearch.yml`` then do ``./bin/elasticsearch``.

Sample query:

```
curl -X GET 'http://127.0.0.1:9200/_search?q=verb:GET&size=5&pretty=true'
```


## Cluster Health

See https://www.elastic.co/guide/en/elasticsearch/guide/current/cluster-health.html

Sample query: ``curl -s -X GET 'http://127.0.0.1:9200/_cluster/health' | python -mjson.tool``.

## Put sample info

curl -v -X PUT 'http://127.0.0.1:9200/megacorp/employee/1' -d @employee1.json
curl -v -X PUT 'http://127.0.0.1:9200/megacorp/employee/2' -d @employee2.json
curl -v -X PUT 'http://127.0.0.1:9200/megacorp/employee/3' -d @employee3.json

Where:

employee1.json:

```js
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
```

employee2.json:

```js
{
    "first_name" :  "Jane",
    "last_name" :   "Smith",
    "age" :         32,
    "about" :       "I like to collect rock albums",
    "interests":  [ "music" ]
}
```

employee3.json:

```js
{
    "first_name" :  "Douglas",
    "last_name" :   "Fir",
    "age" :         35,
    "about":        "I like to build cabinets",
    "interests":  [ "forestry" ]
}
```

## Retrieval

curl -s -X GET 'http://127.0.0.1:9200/megacorp/employee/1' | python -mjson.tool

Also:
HEAD - to check if a document exists
DELETE - delete the document

## Search

curl -s -X GET 'http://127.0.0.1:9200/megacorp/employee/_search' | python -mjson.tool

curl -s -X GET 'http://127.0.0.1:9200/megacorp/employee/_search?q=last_name:Smith' | python -mjson.tool

Using query DSL:
curl -s -X GET 'http://127.0.0.1:9200/megacorp/employee/_search' -d @smith-query-dsl.json | python -mjson.tool

Where smith-query-dsl.json:

```js
{
    "query" : {
        "match" : {
            "last_name" : "Smith"
        }
    }
}
```

Full text search:

curl -s -X GET 'http://127.0.0.1:9200/megacorp/employee/_search' -d @rock-climbing-search-query.json | python -mjson.tool 

where rock-climbing-search-query.json:

```js
{
    "query" : {
        "match" : {
            "about" : "rock climbing"
        }
    }
}
```

See also https://www.elastic.co/guide/en/elasticsearch/guide/current/_phrase_search.html



