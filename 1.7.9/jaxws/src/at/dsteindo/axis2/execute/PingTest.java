package at.dsteindo.axis2.execute;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import bs_cavok_soap.BsCavokSoap;
import bs_cavok_soap.BsCavokSoapPortType;
import bs_cavok_soap.PingStringInput;

public class PingTest {

    private BsCavokSoapPortType serviceInternal = null;

    public void execute() {
        ping();
        ping();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 201; i++) {
            Thread t = new Thread(() -> ping());
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

    public void ping() {
        PingStringInput input = new PingStringInput();
        input.setPingInput("hello");
        getService().pingOperation(input);
    }

    public BsCavokSoapPortType getService() {
        // Important Note: For JAX-WS with Axis2 to work without Apache HTTP Commons the
        // system property "axis2.xml" needs to be set to a custom configuration file
        if (serviceInternal == null) {
            final BsCavokSoap bsCavokSoap = new BsCavokSoap();
            final BsCavokSoapPortType service = bsCavokSoap.getBsCavokSoap();
            final BindingProvider bindingprovider = (BindingProvider) service;
            final String endpointURL = "http://localhost:8080/services/ping";
            bindingprovider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
            bindingprovider.getRequestContext().put("SO_TIMEOUT", 60000);
            bindingprovider.getRequestContext().put("CONNECTION_TIMEOUT", 60000);
            serviceInternal = service;
        }
        return serviceInternal;
    }
}
