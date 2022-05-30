package at.dsteindo.axis2.execute;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.ws.BindingProvider;

import org.apache.axis2.jaxws.samples.client.echo.EchoServicePortProxy;
import org.apache.axis2.jaxws.samples.echo.EchoStringInput;
import org.apache.axis2.jaxws.samples.echo.ObjectFactory;

public class EchoTest {

    private static final String SERVICE_ENDPOINT = "http://localhost:8080/services/echo";

    private EchoServicePortProxy serviceInternal = null;
    private AtomicInteger atomicCount = new AtomicInteger(0);

    public void execute() {
        echo();
        echo();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Thread t = new Thread(() -> threadWorker());
            t.start();
            threads.add(t);
        }
        boolean allFinished = false;
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(6000);
                allFinished = threads.stream().noneMatch(t -> t.isAlive());
                if (allFinished) {
                    System.out.println("Success!!");
                    break;
                }
            }
            if (!allFinished) {
                System.out.print("Failure, maybe the connection pool not handing out connections anymore ...");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        threads.forEach(t -> t.interrupt());
    }

    public void threadWorker() {
        while (atomicCount.incrementAndGet() < 201) {
            echo();
        }
    }

    public void echo() {
        try {
            // Build the input object
            EchoStringInput echoParm = new ObjectFactory().createEchoStringInput();
            echoParm.setEchoInput("hello");
            System.out.println(">> CLIENT: SEI Echo to " + SERVICE_ENDPOINT);

            // Call the service
            String response = getService().echoOperation(echoParm).getEchoResponse();
            System.out.println(">> CLIENT: SEI Echo invocation complete.");
            System.out.println(">> CLIENT: SEI Echo response is: " + response);
        } catch (Exception e) {
            System.out.println(">> CLIENT: ERROR: SEI Echo EXCEPTION.");
            e.printStackTrace();
        }
    }

    public EchoServicePortProxy getService() {
        if (serviceInternal == null) {
            serviceInternal = new EchoServicePortProxy(null);
            serviceInternal._getDescriptor().setEndpoint(SERVICE_ENDPOINT);

            // Configure SOAPAction properties
            BindingProvider bp = (BindingProvider) (serviceInternal._getDescriptor().getProxy());
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SERVICE_ENDPOINT);
            bp.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
            bp.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "echoOperation");
        }
        return serviceInternal;
    }
}
