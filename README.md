* references:
    * http://restcookbook.com/HTTP%20Methods/idempotency/
    * https://developer.mozilla.org/en-US/docs/Glossary/safe

## preface
* goals of this workshop:
    * introduction to all http methods
    * introduction to characteristics of http methods
    * introduction to mapping a map in hibernate
    * introduction to spring boot h2 console
        * https://github.com/mtumilowicz/spring-boot-h2-console
        * http://localhost:8080/h2-console
    * introduction to swagger
        * https://github.com/mtumilowicz/swagger-gradle-config
* workshop: workshop package, answers: answers package

## http methods
* https://www.blackhillsinfosec.com/three-minutes-with-the-http-trace-method/
* https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html

### http methods characteristics
* safe methods
    * safe methods are HTTP methods that do not modify resources representation
    * if it leads to a read-only operation
    * servers can alter their state: e.g. they can log or keep statistics
    * safe methods are methods that can be cached, prefetched without any repercussions to the resource
    * it is not possible to ensure that the server does not generate side-effects as a result of 
    performing a GET request
* idempotent
* nullimpotent
* cacheable

### spring context
* @RequestMapping methods mapped to "GET" are also implicitly mapped to "HEAD", i.e. there is no need to have "HEAD" 
explicitly declared. An HTTP HEAD request is processed as if it were an HTTP GET except instead of writing the body 
only the number of bytes are counted and the "Content-Length" header set.

## mapping a map in hibernate
* https://stackoverflow.com/questions/2327971/how-do-you-map-a-map-in-hibernate-using-annotations