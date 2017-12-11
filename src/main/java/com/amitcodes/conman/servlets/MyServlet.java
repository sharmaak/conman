package com.amitcodes.conman.servlets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

public class MyServlet extends HttpServlet
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOGGER.info("In Service Method of MyServlet");
        try {
            resp.setStatus(HttpURLConnection.HTTP_OK);
            resp.setContentType("application/json");
            resp.getOutputStream().println("{\"status\": \"ok\"}");
        } catch (Exception e) {
            resp.reset();
            resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            resp.setContentType("application/json");
            resp.getOutputStream().print(String.format("{\"status\": \"Error\", \"message\":\"%s\"}", e.getMessage()));
            LOGGER.error("Exception occurred!", e);
        }
        finally {
            resp.flushBuffer();
        }
    }
}
