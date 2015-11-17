package com.amitcodes.conman;

import com.amitcodes.conman.servlets.EchoServlet;
import com.amitcodes.conman.servlets.HungServlet;
import com.amitcodes.conman.servlets.MockServlet;
import com.beust.jcommander.JCommander;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Launcher class for conman
 *
 * @author Amit Kumar Sharma
 */
public class EchoLauncher
{
    private static final Logger logger = LoggerFactory.getLogger(EchoLauncher.class);

    private void launch(CliArguments cliArgs) throws Exception
    {
        Server server = new Server(cliArgs.getServerPort());
        server.setHandler(createWebAppContext(cliArgs));
        server.start();
        server.join();
    }

    private WebAppContext createWebAppContext(CliArguments cliArgs) {

        WebAppContext context = new WebAppContext();

        ProtectionDomain protectionDomain = EchoLauncher.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        context.setWar(location.toExternalForm());
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        addServlets(context, cliArgs);
        addFilters(context, cliArgs);
        return context;
    }

    private void addServlets(WebAppContext context, CliArguments cliArgs) {
        ServletHolder mockServletHolder = new ServletHolder("MockServlet", MockServlet.class);
        mockServletHolder.setInitParameter("mapping-file-location", cliArgs.getMappingFileLocation());
        context.addServlet(mockServletHolder, "/mock/*");

        context.addServlet(new ServletHolder("EchoServlet", EchoServlet.class), "/echo/*");
        context.addServlet(new ServletHolder("HungServlet", HungServlet.class), "/hung/*");
    }

    private void addFilters(WebAppContext context, CliArguments cliArgs)
    {
        // CORS Filter
        FilterHolder fh = new FilterHolder(CrossOriginFilter.class);
        fh.setInitParameter("allowCredentials", "true");
        fh.setInitParameter("allowedHeaders", "x-requested-with,content-type,accept,origin,authorization,uid");
        fh.setInitParameter("allowedMethods", "HEAD,GET,POST,OPTIONS,PUT,DELETE,PATCH");
        fh.setInitParameter("exposedHeaders", "link,date,Location,Content-Disposition");
        fh.setInitParameter("allowedOrigins", "*");

        // inject CORS filter
        context.addFilter(fh, "/*", null);
    }

    public static void main(String[] args) throws Exception {
        CliArguments cliArgs = new CliArguments();
        new JCommander(cliArgs, args);
        System.out.println(cliArgs);
        new EchoLauncher().launch(cliArgs);
    }
}
