package com.amitcodes.conman.config;

/**
 * //TODO: Write class javadoc
 *
 * @author Amit K. Sharma
 */
public class ConfigurationException extends RuntimeException
{
    public ConfigurationException(String message, Exception e)
    {
        super(message, e);
    }
}
