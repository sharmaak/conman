![Conman](https://raw.githubusercontent.com/sharmaak/conman/master/conf/ConManBody.jpg)
I Con Webservices!

### What is Conman? 
Conman is a tool to 
* mock RESTful endpoints
* dissect structure of HTTP requests
* simulate hung or malfunctioning endpoints (to test the elusive socket-timeout)  

### What can Conman serve as response? 
Conman can serve: 
* Any test based response (JSON, XML, HTML, TXT, etc) 
* Any binary content (images, pdf, mp3) 

### Quick start
1. Download and unzip the latest conman zip file from [here](https://github.com/sharmaak/conman/releases/latest)
1. Unzip will create a folder called conman. The folder will have two files: conman.jar and conman.yaml. 
1. Update the configuration in conman.yaml to suite your needs. 
1. Execute the jar by issuing `java -jar conman.jar conman.yaml`
1. Invoke GET on http://localhost:8081/tests/100
Example 1: 
```
$ curl -iX GET http://localhost:8081/tests/100
HTTP/1.1 200 OK
Date: Tue, 17 Nov 2015 21:34:30 GMT
Content-Type: application/json
Content-Length: 11
Server: Jetty(9.3.z-SNAPSHOT)

hello world
```
Example 2: 
```
$ curl -iX POST http://localhost:8081/tests/200
HTTP/1.1 200 OK
Date: Tue, 17 Nov 2015 21:35:28 GMT
Content-Type: application/json
Content-Length: 53
Server: Jetty(9.3.z-SNAPSHOT)

{
    "hello": "world",
    "my": "loving dududes"
}

```
That's all, you have mocked your RESTful endpoints!

For detailed configuration refer to the [configuration page](https://github.com/sharmaak/conman/wiki/Configuration) in this wiki. 

### License
Copyright (c) 2015 Amit Kumar Sharma

Apache License 2.0 (Apache-2.0) licensing, for details see file LICENSE.
