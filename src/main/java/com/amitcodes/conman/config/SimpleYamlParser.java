package com.amitcodes.conman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleYamlParser<T>
{
    final Class<T> typeParameterClass;

    public SimpleYamlParser(Class<T> typeParameterClass)
    {
        this.typeParameterClass = typeParameterClass;
    }

    public T parse(InputStream in)
    {
        T pojo;
        try{
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            pojo = mapper.readValue(in, typeParameterClass);
        } catch (Exception e) {
            throw new ConfigurationException("YAML parsing failed!", e);
        }

        return pojo;
    }

    public T parse(File yamlFile)
    {
        T pojo;
        try(InputStream fileReader = new FileInputStream(yamlFile)){
            pojo = parse(fileReader);
        } catch (IOException e) {
            throw new ConfigurationException("YAML file access failed!", e);
        }
        return pojo;
    }

    public T parse(String path)
    {
        return parse(new File(path));
    }


}
