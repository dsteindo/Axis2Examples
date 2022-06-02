package at.dsteindo.axis2.execute;

import javax.xml.ws.BindingProvider;

import bs_cavok_soap.BsCavokSoap;
import bs_cavok_soap.BsCavokSoapPortType;
import bs_cavok_soap.LoginResponse;
import bs_cavok_soap.PingStringInput;

public class PingTest {

    private BsCavokSoapPortType serviceInternal = null;

    public void ping() {
        PingStringInput input = new PingStringInput();
        input.setPingInput("hello");
        LoginResponse response = getService().pingOperation(input);
        System.out.println(response.getSessionID());
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
