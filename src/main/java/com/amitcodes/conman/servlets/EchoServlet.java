package com.amitcodes.conman.servlets;


import com.amitcodes.conman.Launcher;
import com.amitcodes.conman.pojos.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

public class EchoServlet extends HttpServlet
{
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
    private ObjectMapper jsonMapper;
    
    @Override
    public void init() throws ServletException
    {
        super.init();
        jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        jsonMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setBufferSize(1000000);

        try {
            resp.setStatus(HttpURLConnection.HTTP_OK);
            resp.setContentType("application/json");
            Response r = Response.fromRequest(req);
            jsonMapper.writeValue(resp.getOutputStream(), r);
        } catch (Exception e) {
            resp.reset();
            resp.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            resp.setContentType("application/json");
            resp.getOutputStream().print(String.format("{\"status\": \"Error\", \"message\":\"%s\"}", e.getMessage()));
            logger.error("Exception occurred!", e);
        }
        finally {
            resp.flushBuffer();
        }
    }
}
