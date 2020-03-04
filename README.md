* https://stackoverflow.com/questions/2327971/how-do-you-map-a-map-in-hibernate-using-annotations
* https://github.com/mtumilowicz/spring-boot-h2-console
* http://localhost:8080/h2-console

* @RequestMapping methods mapped to "GET" are also implicitly mapped to "HEAD", i.e. there is no need to have "HEAD" explicitly declared. An HTTP HEAD request is processed as if it were an HTTP GET except instead of writing the body only the number of bytes are counted and the "Content-Length" header set.
* https://www.blackhillsinfosec.com/three-minutes-with-the-http-trace-method/
* a request method can be safe, idempotent, or cacheable
* https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html