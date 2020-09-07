# SparkJava demo of the Spark interceptor

This is SparkJava-based demo of the Authentication and Authorization framework.
See source of the interceptor at [Github](https://github.com/milanvidakovic/SparkInterceptor).

You need to add the CLASSPATH following jars:
* agent.jar
* aspectjweaver-1.9.6.jar
* aspectjrt-1.9.6.jar
* aspectjtools-1.9.6.jar

To start your SparkJava app with the interceptor, add at the end of VM params: 

```
-javaagent:c:\<path-to-aspectj>\aspectjweaver-1.9.6.jar
```

