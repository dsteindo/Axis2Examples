package at.dsteindo.axis2.core;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import at.dsteindo.axis2.execute.PingTest;
import at.dsteindo.axis2.execute.ThreadedExecutor;

public class Registry {
    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH); // avoid problems if host environment is non English
        applicationContext = SpringApplication.run(Application.class, args);

        PingTest test = new PingTest();
        test.ping();
        test.ping();

        ThreadedExecutor executor = new ThreadedExecutor(test::ping);
        executor.execute();

        applicationContext.close();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
