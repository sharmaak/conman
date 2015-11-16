package com.amitcodes.conman;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Launcher class for conman
 *
 * @author Amit K. Sharma
 */
public class EchoLauncher
{
    private static final Logger logger = LoggerFactory.getLogger(EchoLauncher.class);

    public static void main(String[] args) throws Exception {
        ProtectionDomain protectionDomain = EchoLauncher.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        int port = Integer.parseInt(System.getProperty("conman.port", "8888"));
        Server server = new Server(port);
        WebAppContext context = new WebAppContext();
        context.setWar(location.toExternalForm());
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
