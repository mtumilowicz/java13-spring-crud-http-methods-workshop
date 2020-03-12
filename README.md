* references:
    * http://restcookbook.com/HTTP%20Methods/idempotency/
    * https://developer.mozilla.org/en-US/docs/Glossary/safe
    * https://harikt.com/blog/2013/11/01/rest-is-delete-idempotent/
    * https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Idempotent_methods_and_web_applications
    * https://tools.ietf.org/html/rfc7231

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
|HTTP method   |Safe   |Idempotent   |Cacheable   |
|---           |---    |---          |---         |
|GET           |V      |V            |V           |
|HEAD          |V      |V            |V           |
|OPTIONS       |V      |V            |X           |
|TRACE         |V      |V            |X           |
|PUT           |X      |V            |X           |
|DELETE        |X      |V            |X           |
|POST          |X      |X            |V           |
|CONNECT       |X      |X            |X           |
|PATCH         |X      |X            |X           |

* safe methods
    * safe methods are HTTP methods that do not modify resources representation
    * if it leads to a read-only operation
    * servers can alter their state: e.g. they can log or keep statistics
        * even though the log storage might become full and crash the server
    * a safe request initiated by selecting an advertisement on the Web will often have the side
    effect of charging an advertising account
    * safe methods are methods that can be cached, prefetched without any repercussions to the resource
        * purpose of distinguishing between safe and unsafe methods is to allow automated retrieval processes (spiders) 
        and cache performance optimization (pre-fetching) to work without fear of causing harm
    * it is not possible to ensure that the server does not generate side-effects as a result of 
    performing a GET request
        * failure to do so will result in unfortunate side effects when automated processes perform a GET on every 
        URI reference for the sake of link maintenance, pre-fetching, building a search index, etc.
* idempotent
    * the side-effects of N > 0 identical requests is the same as for a single request (aside from error or 
    expiration issues)
    * refers to the state of the system after the request has completed, so the response code it returns may be 
    different on subsequent requests, the system state will be the same every time
        * example: deleting resource that exists (state of the resource is the same - it will not exist, but the
        responses differ)
            * first: 200 OK
            * next: 404 NOT FOUND
    * the request can be repeated automatically if a communication failure occurs before the client is able to read 
    the server's response
* nullimpotent
    * the side-effects of N >= 0 identical requests is the same as for a single request (aside from error or 
    expiration issues)
    * every nullimpotent is idempotent
* cacheable
    * request methods can be defined as "cacheable" to indicate that responses to them are allowed to be stored for 
    future reuse
    * in general, safe methods that do not depend on a current or authoritative response are defined as
    cacheable
    * the overwhelming majority of cache implementations only support GET and HEAD
        * however POST could be also defined as cacheable

### spring context
* @RequestMapping methods mapped to "GET" are also implicitly mapped to "HEAD", i.e. there is no need to have "HEAD" 
explicitly declared. An HTTP HEAD request is processed as if it were an HTTP GET except instead of writing the body 
only the number of bytes are counted and the "Content-Length" header set.

## mapping a map in hibernate
* https://stackoverflow.com/questions/2327971/how-do-you-map-a-map-in-hibernate-using-annotations