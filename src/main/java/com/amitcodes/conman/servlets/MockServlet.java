package com.amitcodes.conman.servlets;

import com.amitcodes.conman.config.ConmanConfig;
import com.amitcodes.conman.pojos.MockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MockServlet extends HttpServlet
{
    private static final Logger logger = LoggerFactory.getLogger(MockServlet.class);
    private Map<String, MockData> mockDataMap;

    public MockServlet(ConmanConfig config)
    {
        mockDataMap =  new HashMap<>(config.getMockMappings().size());
        for(MockData mockData : config.getMockMappings()) {
            //TODO: Log any mock data that is overwritten due to same key
            mockDataMap.put(mockData.getHttpMethod().toUpperCase() + "_" + mockData.getUri(), mockData);
            logger.debug("Added mock mappings: {}", mockDataMap);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        MockData data = mockDataMap.get(req.getMethod() + "_" + req.getRequestURI());
        if(data == null) {
            notFound(req, resp);
            return;
        }

        resp.setContentType(data.getContentType());
        resp.getOutputStream().write(data.getBodyBytes());
        resp.setStatus(data.getStatusCode());
        if(null != data.getResponseHeaders()) {
            for (Map.Entry<String, String> header : data.getResponseHeaders().entrySet()) {
                resp.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    private void notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        byte[] body =  String.format("{ \n" +
                                             "\"status\": \"Conman mapping error\",\n" +
                                             "\"message\": \"Mapping not found for method=%s, URI=%s\" \n" +
                                             "}\n",
                                     req.getMethod(), req.getRequestURI()).getBytes();
        resp.getOutputStream().write(body);
    }
}
