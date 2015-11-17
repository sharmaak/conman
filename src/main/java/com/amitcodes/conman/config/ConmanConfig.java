package com.amitcodes.conman.config;

import com.amitcodes.conman.pojos.MockData;

import java.util.List;

/**
 * Configuration class for ConMan
 *
 * @author Amit Kumar Sharma
 */
public class ConmanConfig
{
    private int port = 8888;
    private List<String> mockServletUriMappings;
    private List<String> echoServletUriMappings;
    private List<String> hungServletUriMappings;
    private List<MockData> mockMappings;

    public ConmanConfig()
    {
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public List<String> getMockServletUriMappings()
    {
        return mockServletUriMappings;
    }

    public void setMockServletUriMappings(List<String> mockServletUriMappings)
    {
        this.mockServletUriMappings = mockServletUriMappings;
    }

    public List<String> getEchoServletUriMappings()
    {
        return echoServletUriMappings;
    }

    public void setEchoServletUriMappings(List<String> echoServletUriMappings)
    {
        this.echoServletUriMappings = echoServletUriMappings;
    }

    public List<String> getHungServletUriMappings()
    {
        return hungServletUriMappings;
    }

    public void setHungServletUriMappings(List<String> hungServletUriMappings)
    {
        this.hungServletUriMappings = hungServletUriMappings;
    }

    public List<MockData> getMockMappings()
    {
        return mockMappings;
    }

    public void setMockMappings(List<MockData> mockMappings)
    {
        this.mockMappings = mockMappings;
    }

    @Override
    public String toString()
    {
        return "ConmanConfig{" +
                "port=" + port +
                ", mockServletUriMappings=" + mockServletUriMappings +
                ", echoServletUriMappings=" + echoServletUriMappings +
                ", hungServletUriMappings=" + hungServletUriMappings +
                ", mockMappings=" + mockMappings +
                '}';
    }
}
