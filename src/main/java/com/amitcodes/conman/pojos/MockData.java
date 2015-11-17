package com.amitcodes.conman.pojos;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockData
{
    private String uri;
    private String body;
    private String contentType;
    private String httpMethod;
    private int statusCode;
    private static ObjectMapper jsonMapper;
    
    static {
        jsonMapper = new ObjectMapper();
        jsonMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        jsonMapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
    }

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
                byte[] data = Files.readAllBytes(path);
                return data;
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

    public static Map<String, MockData> parse(File mockFileLocation) throws IOException
    {

        List<MockData> list = null;

        if(mockFileLocation.getName().toLowerCase().endsWith(".json")) {
            list = parseJson(mockFileLocation);
        } else
        if(mockFileLocation.getName().toLowerCase().endsWith(".yaml") || mockFileLocation.getName().toLowerCase().endsWith(".yml")) {
            list = parseYaml(mockFileLocation);
        } else {
            throw new RuntimeException("Unsupported configuration file type: " + mockFileLocation);
        }

        Map<String, MockData> lookup =  new HashMap<>(list.size());
        for(MockData d : list) {
            lookup.put(d.getHttpMethod().toUpperCase() + "_" + d.getUri(), d);
        }
        return lookup;
    }

    private static List<MockData> parseYaml(File mockFileLocation) throws IOException
    {
        Yaml yaml = new Yaml();
        List<MockData> list;
        try(FileReader reader = new FileReader(mockFileLocation)) {
            //noinspection unchecked
            list = (List<MockData>) yaml.load(reader);
        }
        return list;
    }

    public static List<MockData> parseJson(File mockFileLocation) throws IOException
    {
        JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, MockData.class);
        List<MockData> list = jsonMapper.readValue(mockFileLocation, type);
        return list;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("MockData{");
        sb.append("uri='").append(uri).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append(", contentType='").append(contentType).append('\'');
        sb.append(", httpMethod='").append(httpMethod).append('\'');
        sb.append(", statusCode=").append(statusCode);
        sb.append('}');
        return sb.toString();
    }
}
