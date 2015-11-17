package com.amitcodes.conman.servlets;


import com.amitcodes.conman.EchoLauncher;
import com.amitcodes.conman.pojos.MockData;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MockServlet extends HttpServlet
{
    private static final Logger logger = LoggerFactory.getLogger(EchoLauncher.class);
    private ObjectMapper jsonMapper;
    private Map<String, MockData> mockDataMap;
    
    @Override
    public void init() throws ServletException
    {
        super.init();
        jsonMapper = new ObjectMapper();
        jsonMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        jsonMapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
        
        // Load mapping file 
        try {
            loadMappingFile();
        } catch (IOException e) {
            logger.error("Exception occurred", e);
            throw new ServletException(e);
        }
    }

    private void loadMappingFile() throws IOException
    {
        String mockMappingFileLocation = getServletContext().getInitParameter("mapping-file-location");
        if(mockMappingFileLocation == null) {
            throw new IllegalStateException("Mapping file must be provided");
        }

        File mockFileLocation = new File(mockMappingFileLocation);
        if( mockFileLocation.exists() && mockFileLocation.isFile() ) {
            this.mockDataMap = MockData.parse(mockFileLocation);
        } else {
            throw new IOException("Invalid mapping file: " + mockMappingFileLocation);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        MockData data = mockDataMap.get(req.getMethod() + "_" + req.getRequestURI());
        resp.setContentType(data.getContentType());
        resp.getOutputStream().write(data.getBodyBytes());
        resp.setStatus(data.getStatusCode());
    }
}
