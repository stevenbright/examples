
# Jetty t1.micro Performance

Launched as ``java -server -jar guice-resteasy-freemarker-jetty-1.0-SNAPSHOT.jar``
Performance measured on other ec2 t1.micro host (in the same availability zone - us-west-2b)

# Jetty 1000/1 Performance

```
ab -n 1000 -c 1 172.31.36.169:8080/rest/hello/1

Time taken for tests:   1.212 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      170000 bytes
HTML transferred:       23000 bytes
Requests per second:    825.26 [#/sec] (mean)
Time per request:       1.212 [ms] (mean)
Time per request:       1.212 [ms] (mean, across all concurrent requests)
Transfer rate:          137.01 [Kbytes/sec] received

Percentage of the requests served within a certain time (ms)
  50%      1
  66%      1
  75%      1
  80%      1
  90%      1
  95%      1
  98%      3
  99%      3
 100%     16 (longest request)
```

# Jetty 10000/10 Performance

```
Concurrency Level:      10
Time taken for tests:   3.819 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      1710000 bytes
HTML transferred:       240000 bytes
Requests per second:    2618.47 [#/sec] (mean)
Time per request:       3.819 [ms] (mean)
Time per request:       0.382 [ms] (mean, across all concurrent requests)
Transfer rate:          437.26 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       1
Processing:     1    3   3.5      2      36
Waiting:        1    3   3.3      2      36
Total:          1    4   3.5      3      37

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      3
  75%      4
  80%      5
  90%      8
  95%     11
  98%     15
  99%     19
 100%     37 (longest request)
```

# Jetty 10000/50 Performance

```
ab -n 10000 -c 50 172.31.36.169:8080/rest/hello/1

Concurrency Level:      50
Time taken for tests:   3.458 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      1710000 bytes
HTML transferred:       240000 bytes
Requests per second:    2891.55 [#/sec] (mean)
Time per request:       17.292 [ms] (mean)
Time per request:       0.346 [ms] (mean, across all concurrent requests)
Transfer rate:          482.87 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.2      0       3
Processing:     1   17   9.9     15     110
Waiting:        1   17   9.8     15     110
Total:          1   17   9.9     15     110

Percentage of the requests served within a certain time (ms)
  50%     15
  66%     19
  75%     21
  80%     22
  90%     28
  95%     35
  98%     47
  99%     55
 100%    110 (longest request)
```

# Jetty 10000/100 Performance

```
ab -n 10000 -c 100 172.31.36.169:8080/rest/hello/1m


Concurrency Level:      100
Time taken for tests:   3.368 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      1710000 bytes
HTML transferred:       240000 bytes
Requests per second:    2968.89 [#/sec] (mean)
Time per request:       33.683 [ms] (mean)
Time per request:       0.337 [ms] (mean, across all concurrent requests)
Transfer rate:          495.78 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   10  95.3      0    1001
Processing:     1   19  15.5     17     252
Waiting:        1   19  15.4     17     252
Total:          1   29  96.6     18    1074

Percentage of the requests served within a certain time (ms)
  50%     18
  66%     21
  75%     24
  80%     26
  90%     31
  95%     38
  98%     55
  99%    230
 100%   1074 (longest request)
```

# Netty t1.micro Performance

Launched as java -server -jar guice-resteasy-netty-1.0-SNAPSHOT.jar
Performance measured on other ec2 t1.micro instance running in the same availability zone (us-west-2b)

# Netty 1000/1 Performance

```
ab -n 1000 -c 1 172.31.36.169:9090/hello/1

Concurrency Level:      1
Time taken for tests:   0.963 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      82000 bytes
HTML transferred:       23000 bytes
Requests per second:    1038.15 [#/sec] (mean)
Time per request:       0.963 [ms] (mean)
Time per request:       0.963 [ms] (mean, across all concurrent requests)
Transfer rate:          83.13 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.2      0       5
Processing:     1    1   0.3      1       7
Waiting:        0    1   0.3      1       7
Total:          1    1   0.4      1       7

Percentage of the requests served within a certain time (ms)
  50%      1
  66%      1
  75%      1
  80%      1
  90%      1
  95%      1
  98%      2
  99%      3
 100%      7 (longest request)
```

# Netty 10000/10 Performance

```
ab -n 10000 -c 10 172.31.36.169:9090/hello/1

Concurrency Level:      10
Time taken for tests:   4.220 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      820000 bytes
HTML transferred:       230000 bytes
Requests per second:    2369.62 [#/sec] (mean)
Time per request:       4.220 [ms] (mean)
Time per request:       0.422 [ms] (mean, across all concurrent requests)
Transfer rate:          189.75 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   0.2      0       2
Processing:     1    4   2.1      3      34
Waiting:        1    4   2.1      3      34
Total:          1    4   2.1      4      35
ERROR: The median and mean for the initial connection time are more than twice the standard
       deviation apart. These results are NOT reliable.

Percentage of the requests served within a certain time (ms)
  50%      4
  66%      4
  75%      5
  80%      6
  90%      7
  95%      8
  98%      9
  99%     10
 100%     35 (longest request)
```

# Netty 10000/50 Performance

```
ab -n 10000 -c 50 172.31.36.169:9090/hello/1

Concurrency Level:      50
Time taken for tests:   3.184 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      820000 bytes
HTML transferred:       230000 bytes
Requests per second:    3140.24 [#/sec] (mean)
Time per request:       15.922 [ms] (mean)
Time per request:       0.318 [ms] (mean, across all concurrent requests)
Transfer rate:          251.46 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    2   1.1      2       7
Processing:     1   14   6.5     14      43
Waiting:        1   14   6.5     13      43
Total:          1   16   6.6     15      44

Percentage of the requests served within a certain time (ms)
  50%     15
  66%     18
  75%     20
  80%     21
  90%     25
  95%     28
  98%     31
  99%     33
 100%     44 (longest request)
```

# Netty 10000/100 Performance

```
ab -n 10000 -c 100 172.31.36.169:9090/hello/1

Concurrency Level:      100
Time taken for tests:   2.652 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      820410 bytes
HTML transferred:       230115 bytes
Requests per second:    3770.78 [#/sec] (mean)
Time per request:       26.520 [ms] (mean)
Time per request:       0.265 [ms] (mean, across all concurrent requests)
Transfer rate:          302.11 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    6  47.9      3    1003
Processing:     1   20  12.3     18     261
Waiting:        1   20  12.3     18     261
Total:          1   26  49.3     22    1025

Percentage of the requests served within a certain time (ms)
  50%     22
  66%     26
  75%     30
  80%     32
  90%     37
  95%     43
  98%     52
  99%     58
 100%   1025 (longest request)
```
