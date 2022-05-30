package at.dsteindo.axis2.controller;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    AtomicInteger count = new AtomicInteger(0);

    @RequestMapping(path = "services/ping", method = RequestMethod.POST)
    public String ping(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ok " + count.incrementAndGet());
        response.addHeader("Connection", "Keep-Alive");
        response.addHeader("Keep-Alive", "timeout=5, max=90");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"urn:bs_cavok_soap\">"
                + "<soapenv:Body>"
                + "<ns1:loginResponse>"
                + "<sessionID>"
                + UUID.randomUUID().toString()
                + "</sessionID>"
                + "</ns1:loginResponse>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";
    }

    @RequestMapping(path = "services/echo", method = RequestMethod.POST)
    public String echo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ok " + count.incrementAndGet());
        response.addHeader("Connection", "Keep-Alive");
        response.addHeader("Keep-Alive", "timeout=5, max=90");
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://org/apache/axis2/jaxws/samples/echo/\">"
                + "<soapenv:Body>"
                + "<ns1:echoStringResponse>"
                + "<echoResponse>"
                + UUID.randomUUID().toString()
                + "</echoResponse>"
                + "</ns1:echoStringResponse>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";
    }
}