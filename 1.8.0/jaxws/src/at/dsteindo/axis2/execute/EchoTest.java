package at.dsteindo.axis2.execute;

import javax.xml.ws.BindingProvider;

import org.apache.axis2.jaxws.samples.client.echo.EchoServicePortProxy;
import org.apache.axis2.jaxws.samples.echo.EchoStringInput;

public class EchoTest {

    private static final String SERVICE_ENDPOINT = "http://localhost:8080/services/echo";

    private EchoServicePortProxy serviceInternal = null;

    public void echo() {
        try {
            // Build the input object
            EchoStringInput echoParm = new EchoStringInput();
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
