package com.amitcodes.conman;

import com.beust.jcommander.Parameter;

/**
 * POJO for command line arguments.
 *
 * @author Amit K. Sharma
 */
public class CliArguments
{
    @Parameter(names = { "--port", "-p" }, description = "Server port")
    private int serverPort = 8888;
    @Parameter(names = { "--mapping-file-location", "-m" }, description = "Location of mapping file for mocking")
    private String mappingFileLocation;

    public CliArguments()
    {
    }

    public int getServerPort()
    {
        return serverPort;
    }

    public void setServerPort(int serverPort)
    {
        this.serverPort = serverPort;
    }

    public String getMappingFileLocation()
    {
        return mappingFileLocation;
    }

    public void setMappingFileLocation(String mappingFileLocation)
    {
        this.mappingFileLocation = mappingFileLocation;
    }

    @Override
    public String toString()
    {
        return "CliArguments{" +
                "serverPort=" + serverPort +
                ", mappingFileLocation='" + mappingFileLocation + '\'' +
                '}';
    }
}
