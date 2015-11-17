package com.amitcodes.conman.pojos;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MockData
{
    private String uri;
    private String body;
    private String contentType;
    private String httpMethod;
    private int statusCode;

    public MockData(){}

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getBody()
    {
        return body;
    }

    public byte[] getBodyBytes()
    {
        if(body == null || body.isEmpty()) {return new byte[0];}

        if(body.trim().startsWith("STREAM_FILE:")){
            // 1. derive location of the file
            String filePath = body.trim().split(":")[1];
            filePath = filePath.trim();

            // 2. load the whole file into memory as byte[]
            // TODO : change this to stream bytes going forward.
            Path path = Paths.get(filePath);
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file " + filePath, e);
            }
        }
        return body.getBytes();
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getHttpMethod()
    {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod)
    {
        this.httpMethod = httpMethod;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    @Override
    public String toString()
    {
        String sb = "MockData{" + "uri='" + uri + '\'' +
                ", body='" + body + '\'' +
                ", contentType='" + contentType + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", statusCode=" + statusCode +
                '}';
        return sb;
    }
}
