[![Build Status](https://app.travis-ci.com/mtumilowicz/java13-spring-crud-http-methods-workshop.svg?branch=master)](https://travis-ci.com/mtumilowicz/java13-spring-crud-http-methods-workshop)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# java13-spring-CRUD-http-methods-workshop
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
    * https://airbrake.io/blog/http-errors/303-see-other
    * https://airbrake.io/blog/http-errors/304-not-modified
    * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/304
    * https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/412
    
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
* workshop: `ProcessConfigControllerWorkshop`, `ProcessConfigServiceWorkshop`
* answers: `ProcessConfigController`, `ProcessConfigService`

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
    * if a Location header field is provided, the user agent MAY automatically redirect its request to the URI
    referenced by the Location field value
    * 300 - Multiple Choices
        * indicates that the target resource has more than one representation, each with its own more specific 
        identifier, and information about the alternatives is being provided so that the user (or user agent) 
        can select a preferred representation by redirecting its request to one or more of those identifiers
        * web server thinks that the URL provided by the client is  not specific enough, and a further selection 
        needs to be made from a number of choices
    * 301 - Moved Permanently
        * indicates that the target resource has been assigned a new permanent URI and any future
        references to this resource ought to use one of the enclosed URIs
    * 302 - Found
        * indicates that the target resource resides temporarily under a different URI
        * for historical reasons, a user agent MAY change the request method from POST to GET for the 
        subsequent request
    * 303 - See Other
        * indicates that the server is redirecting the user agent to a different resource, as indicated by a
        URI in the Location header field, which is intended to provide an indirect response to the original request
        * user agent can perform a retrieval request targeting that URI (a GET or HEAD request if using HTTP)
        * example
            * POST method request is sent to https://airbrake.io
            * web server may be configured to redirect this POST request to https://airbrake.io/login
            * server may respond with a 303 See Other code and include the Location: https://airbrake.io/login 
            header in the response
    * 304 - Not Modified
        * indicates that a conditional GET or HEAD request has been received and would have resulted in a 200
        (OK) response if it were not for the fact that the condition evaluated to false
        * the server is therefore redirecting the client to make use of that stored representation as if it were 
        the payload of a 200 (OK) response
    * 305 - Use Proxy - deprecated
    * 307 - Temporary Redirect
        * indicates that the target resource resides temporarily under a different URI and the user agent
        MUST NOT change the request method if it performs an automatic redirection to that URI
        * this status code is similar to 302 (Found), except that it does not allow changing the request method 
        from POST to GET
* 4xx (Client Error): The request contains bad syntax or cannot be fulfilled
    * 400 - Bad Request
        * indicates that the server cannot or will not process the request due to something that is perceived to be
         a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing)
    * 401 - Unauthorized
        * indicates that the request has not been applied because it lacks valid authentication credentials for 
        the target resource
        * server generating a 401 response MUST send a WWW-Authenticate header field containing at least one
        challenge applicable to the target resource
    * 402 - Payment Required - reserved for future use
    * 403 - Forbidden
        * indicates that the server understood the request but refuses to authorize it
        * if authentication credentials were provided in the request, the server considers them insufficient to 
        grant access
    * 404 - Not Found
        * indicates that the origin server did not find a current representation for the target resource or is not 
        willing to disclose that one exists
    * 405 - Method Not Allowed
        * indicates that the method received in the request-line is known by the origin server but not supported 
        by the target resource
        * the origin server MUST generate an Allow header field in a 405 response containing a list of the target
        resource's currently supported methods
    * 406 - Not Acceptable
        * indicates that the target resource does not have a current representation that would be acceptable 
        to the user agent, according to the proactive negotiation header fields received in the request, and 
        the server is unwilling to supply a default representation
    * 407 - Proxy Authentication Required
        * similar to 401 (Unauthorized), but it indicates that the client needs to authenticate itself in order 
        to use a proxy
        * proxy MUST send a Proxy-Authenticate header field containing a challenge applicable to that proxy for 
        the target resource
        * The client MAY repeat the request with a new or replaced Proxy-Authorization header field
    * 408 - Request Timeout
        * indicates that the server did not receive a complete request message within the time that it was prepared 
        to wait
    * 409 - Conflict
        * indicates that the request could not be completed due to a conflict with the current state of the target
        resource
        * if versioning were being used and the representation being PUT included changes to a resource that conflict 
        with those made by an earlier (third-party) request
            * the response representation would likely contain information useful for merging the differences 
            based on the revision history
    * 410 - Gone
        * indicates that access to the target resource is no longer available at the origin server and that this
        condition is likely to be permanent
        * if the origin server does not know, or has no facility to determine, whether or not the condition
        is permanent, the status code 404 (Not Found) ought to be used instead
        * such an event is common for limited-time, promotional services and for resources belonging to
        individuals no longer associated with the origin server's site
    * 411 - Length Required
        * indicates that the server refuses to accept the request without a defined Content-Length
    * 412 - Precondition Failed
        * indicates that one or more conditions given in the request header fields evaluated to false when
        tested on the server
        * happens with conditional requests on methods other than GET or HEAD when the condition defined by the 
        If-Unmodified-Since or If-None-Match headers is not fulfilled
        * allows the client to place preconditions on the current resource state (its current representations 
        and metadata) and, thus, prevent the request method from being applied if the target resource is in an 
        unexpected state
    * 413 - Payload Too Large
        * indicates that the server is refusing to process a request because the request payload is larger
        than the server is willing or able to process
    * 414 - URI Too Long
        * indicates that the server is refusing to service the request because the request-target is longer than 
        the server is willing to interpret
        * examples
            * when a client has improperly converted a POST request to a GET request with long query information
            * when the client has descended into a "black hole" of redirection (e.g., a redirected URI prefix that 
            points to a suffix of itself)
            * when the server is under attack by a client attempting to exploit potential security holes
    * 415 - Unsupported Media Type
        * indicates that the origin server is refusing to service the request because the payload is in a format not 
        supported by this method on the target resource
    * 416 - Range Not Satisfiable
        * indicates that none of the ranges in the request's Range header field overlap the current extent of the 
        selected resource or that the set of ranges requested has been rejected due to invalid ranges or an excessive
        request of small or overlapping ranges
        * example
            * for byte ranges, failing to overlap the current extent means that the first-byte-pos of all of the 
            byte-range-spec values were greater than the current length of the selected representation
    * 417 - Expectation Failed
        * indicates that the expectation given in the request's Expect header field could not be met by at least 
        one of the inbound servers
    * 426 - Upgrade Required
        * indicates that the server refuses to perform the request using the current protocol but might be willing 
        to do so after the client upgrades to a different protocol
        * server MUST send an Upgrade header field in a 426 response to indicate the required protocol(s)
* 5xx (Server Error): The server failed to fulfill an apparently valid request
    * indicates that the server is aware that it has erred or is incapable of performing the requested method
    * 500 - Internal Server Error
        * indicates that the server encountered an unexpected condition that prevented it from fulfilling the request
    * 501 - Not Implemented
        * indicates that the server does not support the functionality required to fulfill the request
        * this is the appropriate response when the server does not recognize the request method and is not capable 
        of supporting it for any resource
    * 502 - Bad Gateway
        * indicates that the server, while acting as a gateway or proxy, received an invalid response from an inbound 
        server it accessed while attempting to fulfill the request
    * 503 - Service Unavailable
        * indicates that the server is currently unable to handle the request due to a temporary overload or 
        scheduled maintenance, which will likely be alleviated after some delay
        * existence of the 503 status code does not imply that a server has to use it when becoming overloaded
            * some servers might simply refuse the connection
    * 504 - Gateway Timeout
        * indicates that the server, while acting as a gateway or proxy, did not receive a timely response from 
        an upstream server it needed to access in order to complete the request
    * 505 - HTTP Version Not Supported
        * indicates that the server does not support, or refuses to support, the major version of HTTP that was 
        used in the request message
        * server SHOULD generate a representation for the 505 response that describes why that version is not 
        supported and what other protocols are supported by that server

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
    * OWASP says you should disable HTTP TRACE because it can be used for Cross Site Tracing
        * Modern browsers now prevent TRACE requests being made via JavaScript
    * vulnerability example
        * TRACE verb is handled by the webserver
        * your request may pass through something else on the way to the webserver
            * for example: Web Application Firewall (WAF)
                * filters requests to detect and kill attacks before they get to the webserver
        * if that something else adds headers, then your TRACE response will include those headers 
            * you’ll gain a little information you didn’t already have
        * you can try to tell the WAF that your request is actually the WAF’s request, and should be ignored
            * The X-Forwarded-For header is one of the headers added by some WAFs, and it is sometimes used by 
            the WAF itself to decide if it should filter that request or not

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
    * apart from `@ElementCollection` annotations could be omitted, but defaults are not always handy
    * `@CollectionTable` - specifies the table that is used for the mapping of collections of basic or embeddable types
    * `@MapKeyColumn` - mapping for the key column
    * `@Column` - mapping for the value column
