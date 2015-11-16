package com.amitcodes.conman;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
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
    private static final Logger logger = LoggerFactory.getLogger(EchoLauncher.class);
    private ObjectMapper jsonMapper;
    
    @Override
    public void init() throws ServletException
    {
        super.init();
        jsonMapper = new ObjectMapper();
        jsonMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        jsonMapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
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
