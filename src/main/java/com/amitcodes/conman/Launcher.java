package com.amitcodes.conman;

import com.amitcodes.conman.config.ConmanConfig;
import com.amitcodes.conman.config.SimpleYamlParser;
import com.amitcodes.conman.pojos.Servlet;
import com.amitcodes.conman.servlets.EchoServlet;
import com.amitcodes.conman.servlets.HungServlet;
import com.amitcodes.conman.servlets.MockServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Launcher class for conman
 *
 * @author Amit Kumar Sharma
 */
public class Launcher
{
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    private void launch(ConmanConfig config) throws Exception
    {
        Server server = new Server(config.getPort());
        logger.info("Configuring jetty to run on port {}", config.getPort());
        server.setHandler(createWebAppContext(config));
        logger.info("WebAppContext creation successful.");
        server.start();
        server.join();
    }

    private WebAppContext createWebAppContext(ConmanConfig config) {

        WebAppContext context = new WebAppContext();
        ProtectionDomain protectionDomain = Launcher.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        context.setWar(location.toExternalForm());
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        addServlets(context, config);
        addFilters(context, config);
        return context;
    }

    private void addServlets(WebAppContext context, ConmanConfig config) {
        ServletHolder mockServletHolder = new ServletHolder(new MockServlet(config));
        for(String pathSpec : config.getMockServletUriMappings()) {
            logger.info("Servlet: MockServlet, adding pathSpec '{}'", pathSpec);
            context.addServlet(mockServletHolder, pathSpec);
        }

        ServletHolder echoServlet = new ServletHolder(new EchoServlet());
        for(String pathSpec : config.getEchoServletUriMappings()) {
            logger.info("Servlet: EchoServlet, adding pathSpec '{}'", pathSpec);
            context.addServlet(echoServlet, pathSpec);
        }

        ServletHolder hungServlet = new ServletHolder(new HungServlet());
        for(String pathSpec : config.getHungServletUriMappings()) {
            logger.info("Servlet: HungServlet, adding pathSpec '{}'", pathSpec);
            context.addServlet(hungServlet, pathSpec);
        }
        for (Servlet servlet: config.getServlets()) {
            try {
                Class<?> cls = Class.forName(servlet.getFqcn());
                ServletHolder servletHolder = new ServletHolder((HttpServlet)cls.newInstance());
                context.addServlet(servletHolder, servlet.getUriPath());
                for(com.amitcodes.conman.pojos.Filter filter : servlet.getFilters()){
                    Class<?> filterClass = Class.forName(filter.getFqcn());
                    FilterHolder fh = new FilterHolder((Filter) filterClass.newInstance());
                    fh.setInitParameters(filter.getInitParams());
                    context.addFilter(fh, servlet.getUriPath(), null);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFilters(WebAppContext context, ConmanConfig config)
    {
        String corsPathSpec = "/*";
        logger.info("Filter: CrossOriginFilter, adding pathSpec '{}'", corsPathSpec);
        // CORS Filter
        FilterHolder fh = new FilterHolder(CrossOriginFilter.class);
        fh.setInitParameter("allowCredentials", "true");
        fh.setInitParameter("allowedHeaders", "x-requested-with,content-type,accept,origin,authorization,uid");
        fh.setInitParameter("allowedMethods", "HEAD,GET,POST,OPTIONS,PUT,DELETE,PATCH");
        fh.setInitParameter("exposedHeaders", "link,date,Location,Content-Disposition");
        fh.setInitParameter("allowedOrigins", "*");
        // inject CORS filter
        context.addFilter(fh, corsPathSpec, null);
    }

    private void addDynamicFilter(WebAppContext context, ConmanConfig config){

    }

    public static void main(String[] args) throws Exception {
        String configFile = args[0];
        logger.info("Loading configuration from file: {} ...", configFile);
        ConmanConfig config = new SimpleYamlParser<>(ConmanConfig.class).parse(configFile);
        logger.info("Loaded configuration: {} ...", config);

        new Launcher().launch(config);
    }
}
