
# Overview

This sample application demonstrates how to use Application Context Initializer.

This is the new concept introduced in Spring 3.1 and it allows to introduce properties to the spring application context that spring will be able to use on the context initialization stage.

What it means in practicality is the ability to use property placeholders in things like import directives in the spring XML.

For example, it is possible to use constructions like that:

```xml
<!-- 
    app.userService.mode is either 'local' or 'remote'.
    and there are two contexts: user-service-local.xml and user-service-remote.xml correspondingly.
-->
<import resource="user-service-${app.userService.mode}.xml"/>
```

It was impossible to use things like that with an old style property placeholder configurers.

# Key points

For details how it work see the following:

* ``App.java``
* ``AppContextInitializer.java``
* ``app-context.xml``
* ``core.properties``, ``empty.properties`` and ``prod.properties``
and how they are referred in ``App.java`` and ``AppContextInitializer.java``

# How to demo

Start application by running ``mvn exec:java``.

The log line should say something like``1187 INFO c.a.s.s.service.LocalFooService - LOCAL FooService.bar()``.

Then start application by running ``mvn exec:java -Dexec.args="--override classpath:/springPropsApp/prod.properties"``.

Now the log line should say something like ``1187 INFO c.a.s.s.service.LocalFooService - REMOTE FooService.bar()``.

Note the difference (LOCAL vs REMOTE) - the beans that produce this message are actually defined in different contexts
that spring captures while initializing the context. These configuration are mutually exclusive - if LOCAL specified,
REMOTE bean should not be initialized and vice versa.


