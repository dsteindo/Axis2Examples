package at.dsteindo.axis2.provider;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class AxisProvider implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        // Tell Axis2 to never ever think again of using HTTP Commons (Client v3) by
        // setting system property to our own definition file
        // Reference: org.apache.axis2.deployment.FileSystemConfigurator
        // Reason: Cavok uses JAX-WS which uses Axis2 under the hood and thus would have
        // issues as it falls back to using Apache HTTP Commons (Client v3) instead of
        // newer Apache HTTP (Client v4) implementation
        String axis2xmlPath = this.getClass().getResource("/axis2/default/conf/axis2.xml").getPath();
        System.setProperty("axis2.xml", axis2xmlPath);
    }
}
