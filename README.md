* references:
    * http://restcookbook.com/HTTP%20Methods/idempotency/
    * https://developer.mozilla.org/en-US/docs/Glossary/safe
    * https://harikt.com/blog/2013/11/01/rest-is-delete-idempotent/
    * https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Idempotent_methods_and_web_applications
    * https://tools.ietf.org/html/rfc7231 (Hypertext Transfer Protocol (HTTP/1.1))
    * https://www.blackhillsinfosec.com/three-minutes-with-the-http-trace-method/
    * https://nordicapis.com/understanding-idempotency-and-safety-in-api-design/
    * https://tools.ietf.org/html/rfc5789 (patch)
    * https://httpstatusdogs.com
    * https://stackoverflow.com/questions/2327971/how-do-you-map-a-map-in-hibernate-using-annotations
    
## preface
* goals of this workshop:
    * overview of all http methods
    * introduction to characteristics of http methods
    * overview of http response statuses
    * introduction to mapping a map in hibernate
    * introduction to spring boot h2 console
        * https://github.com/mtumilowicz/spring-boot-h2-console
        * http://localhost:8080/h2-console
    * introduction to swagger
        * https://github.com/mtumilowicz/swagger-gradle-config
* workshop: workshop package, answers: answers package

## http response status codes
* the first digit of the status-code defines the class of response
* 1xx (Informational): The request was received, continuing process
    * indicates an interim response for communicating connection status or request progress prior to completing 
    the requested action and sending a final response
    * 100 - Continue
        * indicates that the initial part of a request has been received and has not yet been rejected by the server
        * the server intends to send a final response after the request has been fully received and acted upon
        * example
            * server has received the request headers, and that the client should proceed to send the request body
            * if the request body is large, sending it to a server when a request has already been rejected based upon 
            inappropriate headers is inefficient
    * 101 - Switching Protocols
        * indicates that the server understands and is willing to comply with the client's request, via the Upgrade 
        header field, for a change in the application protocol being used on this connection
        * example
            * websockets
            * switching to a newer version of HTTP
* 2xx (Successful): The request was successfully received, understood, and accepted
    * 200 - OK
        * indicates that the request has succeeded
        * payload in response depends on the request method
    * 201 - Created
        * indicates that the request has been fulfilled and has resulted in one or more new resources being created
        * the request is identified by a Location header field in the response
    * 202 - Accepted
        * indicates that the request has been accepted for processing, but the processing has not been completed
        * its purpose is to allow a server to accept a request without requiring the connection to the server persist 
        until the process is completed
    * 203 - Non-Authoritative Information
    * 204 - No Content
        * indicates that the server has successfully fulfilled the request and that there is no additional content 
        to send in the response payload body
        * is commonly used with document editing interfaces corresponding to a "save" action, such that the document
        being saved remains available to the user for editing
    * 205 - Reset Content
        * indicates that the server has fulfilled the request and desires that the user agent reset the 
        "document view", which caused the request to be sent, to its original state
        * is intended to support a common data entry use case where the user enters data, causes the entered data 
        to be submitted in a request, and then the data entry mechanism is reset for the next entry so that the 
        user can easily initiate another input action
    * 206 - Partial Content
        * indicates that the server is successfully fulfilling a range request for the target resource by
        transferring one or more parts of the selected representation that correspond to the satisfiable 
        ranges found in the request's Range header field 
* 3xx (Redirection): further action needs to be taken in order to complete the request
    * 300 - Multiple Choices              
    * 301 - Moved Permanently             
    * 302 - Found                         
    * 303 - See Other                     
    * 304 - Not Modified                 
    * 305 - Use Proxy                     
    * 307 - Temporary Redirect 
* 4xx (Client Error): The request contains bad syntax or cannot be fulfilled
    * 400 - Bad Request                   
    * 401 - Unauthorized                 
    * 402 - Payment Required              
    * 403 - Forbidden                     
    * 404 - Not Found                     
    * 405 - Method Not Allowed            
    * 406 - Not Acceptable                
    * 407 - Proxy Authentication Required 
    * 408 - Request Timeout               
    * 409 - Conflict                      
    * 410 - Gone                          
    * 411 - Length Required               
    * 412 - Precondition Failed           
    * 413 - Payload Too Large             
    * 414 - URI Too Long                  
    * 415 - Unsupported Media Type        
    * 416 - Range Not Satisfiable         
    * 417 - Expectation Failed            
    * 426 - Upgrade Required
* 5xx (Server Error): The server failed to fulfill an apparently valid request
    * 500 - Internal Server Error         
    * 501 - Not Implemented               
    * 502 - Bad Gateway                   
    * 503 - Service Unavailable           
    * 504 - Gateway Timeout               
    * 505 - HTTP Version Not Supported 

## http methods
|HTTP method   |Request has body   |Response has body
|---           |---                |---
|POST          |V                  |V
|PUT           |V                  |V
|PATCH         |V                  |V
|GET           |O                  |V
|OPTIONS       |O                  |V
|DELETE        |O                  |V
|CONNECT       |O                  |V
|TRACE         |X                  |V
|HEAD          |O                  |X

* GET
    * is the primary mechanism of information retrieval and the focus of almost all performance optimizations
* HEAD
    * is identical to GET except that the server MUST NOT send a message body in the response
    * the server SHOULD send the same header fields in response to a HEAD request as it would have sent if the request 
    had been a GET, except that the payload header fields MAY be omitted
    * is often used for testing hypertext links for validity, accessibility, and recent modification
* POST
    * requests that the target resource process the representation enclosed in the request according to the resource's
    own specific semantics
    * used to creating a new resource that has yet to be identified by the origin server
* PUT
    * requests that the state of the target resource be created or replaced with the state defined by the 
    representation enclosed in the request message payload
    * service that selects a proper URI on behalf of the client, after receiving a state-changing
    request, SHOULD be implemented using the POST method rather than PUT
* PATCH
    * requests that a set of changes described in the request entity be applied to the resource identified by the 
    Request-URI
    * is used to apply partial modifications to a resource
    * PUT method is already defined to overwrite a resource with a complete new body, and cannot be reused to do 
    partial changes
    * difference between the PUT and PATCH requests is reflected in the way the server processes the enclosed 
    entity to modify the resource identified by the Request-URI
        * PUT request, the enclosed entity is considered to be a modified version of the resource stored on 
        the origin server, and the client is requesting that the stored version be replaced
        * PATCH - the enclosed entity contains a set of instructions describing how a resource currently 
        residing on the origin server should be modified to produce a new version
    * can be issued in such a way as to be idempotent which also helps prevent bad outcomes from collisions 
    between two PATCH requests on the same resource in a similar time frame
* DELETE
    * requests that the origin server remove the association between the target resource and its current functionality
    * it expresses a deletion operation on the URI mapping of the origin server rather than an expectation that the 
    previously associated information be deleted
* CONNECT
    * requests that the recipient establish a tunnel to the destination origin server identified by the request-target 
    and, if successful, thereafter restrict its behavior to blind forwarding of packets, in both directions, until 
    the tunnel is closed
* OPTIONS
    * requests information about the communication options available for the target resource, at either the origin 
    server or an intervening intermediary
* TRACE
    * requests a remote, application-level loop-back of the request message

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
* `@GetMapping`
* `@RequestMapping(method = RequestMethod.X)`
* `@RequestMapping` methods mapped to "GET" are also implicitly mapped to "HEAD", i.e. there is no need to have "HEAD" 
explicitly declared
* HTTP HEAD request is processed as if it were an HTTP GET except instead of writing the body 
only the number of bytes are counted and the "Content-Length" header set

## mapping a map in hibernate
* suppose we have tables
    * PROCESS_CONFIG_PROPERTIES (actually a map of properties for each process config)
    
        |PROCESS_CONFIG_ID   |VALUE   |KEY   |
        |---                 |---     |---   |
        |1                   |value1  |key1  |
        |1                   |value2  |key2  |
    * PROCESS_CONFIG
    
        |ID                  |
        |---                 |
        |1                   |
* to map it using hibernate to one entity (Process Config)
    ```
    @ElementCollection
    @CollectionTable(name = "process_config_properties",
            joinColumns = {@JoinColumn(name = "process_config_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> properties = new HashMap<>();
    ```
* where
    * `@ElementCollection` - mainly for mapping non-entities (basic type or embeddable class)
        * they can't have their own lifecycle
        * `@OneToMany` - is only for mapping entities
    * apart from `@ElementCollection` annotations could be omitted, but default are not always handy
    * `@CollectionTable` - specifies the table that is used for the mapping of collections of basic or embeddable types
    * `@MapKeyColumn` - mapping for the key column
    * `@Column` - mapping for the value column