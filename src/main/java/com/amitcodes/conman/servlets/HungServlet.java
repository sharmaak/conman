package com.amitcodes.conman.servlets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.StringTokenizer;

public class HungServlet extends HttpServlet
{
    private static final Logger logger = LoggerFactory.getLogger(HungServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try {
            Thread.sleep(1000 * getHangTimeInSeconds(req));
            resp.setStatus(HttpURLConnection.HTTP_OK);
            resp.setContentType("application/json");
            resp.getOutputStream().println("{\"status\": \"ok\"}");
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
    
    private int getHangTimeInSeconds(HttpServletRequest req){
        StringTokenizer tokenizer = new StringTokenizer(req.getRequestURI(), "/");
        if(tokenizer.countTokens() > 1) {
            tokenizer.nextToken();
            try {
                return Integer.parseInt(tokenizer.nextToken());
            } catch (NumberFormatException nex){
                // ignore quietly
            }
        }
        
        return 120; // 120 secs = 2 minutes
    }
}
