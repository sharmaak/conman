package com.amitcodes.conman.pojos;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response
{

    private String url;
    private String origin;
    private String method;
    private LinkedHashMap<String, String> headers = new LinkedHashMap<>();
    private String data;
    private Map<String, Object> args = new LinkedHashMap<>();

    private Response()
    {
    }

    public static Response fromRequest(HttpServletRequest req)
    {
        Response response = new Response();
        response.setHeaders(req);
        response.setOrigin(req);
        response.setMethod(req);
        response.setArgs(req);
        response.setData(req);
        response.setUrl(req);
        return response;
    }

    public String getUrl()
    {
        return url;
    }

    public String getData()
    {
        return data;
    }

    public String getMethod()
    {
        return method;
    }

    public Map<String, Object> getArgs()
    {
        return args;
    }

    public String getOrigin()
    {
        return origin;
    }

    public LinkedHashMap<String, String> getHeaders()
    {
        return headers;
    }

    private void setHeaders(HttpServletRequest req)
    {
        String headerName;
        Enumeration<String> headerValues;
        String multivaluedHeaderValue;

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            headerValues = req.getHeaders(headerName);
            multivaluedHeaderValue = "";
            while (headerValues.hasMoreElements()) {
                multivaluedHeaderValue = multivaluedHeaderValue + headerValues.nextElement() + ", ";
            }
            multivaluedHeaderValue = multivaluedHeaderValue.replaceAll(", $", "");

            headers.put(headerName, multivaluedHeaderValue);
        }
    }

    public void setOrigin(HttpServletRequest req)
    {
        origin = req.getRemoteHost();
    }

    private void setUrl(HttpServletRequest req)
    {
        url = req.getRequestURL().toString();
    }

    private void setData(HttpServletRequest req)
    {
        //---- Handle Body (POST, PATCH, PUT) ----
        StringBuilder b = new StringBuilder();
        String line;
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream())) ) {
            while ((line = br.readLine()) != null){
                b.append(line);
            }
        } catch (IOException iox) {
            throw new RuntimeException(iox);
        }
        data = b.toString();
    }

    private void setArgs(HttpServletRequest req)
    {
        Map<String, String[]> paramMap = req.getParameterMap();
        String[] values;
        if (paramMap != null && paramMap.size() > 0) {
            for (String paramName : req.getParameterMap().keySet()) {
                values = paramMap.get(paramName);
                if (values == null || values.length == 0) {
                    continue;
                }
                if (values.length == 1) { // single valued
                    args.put(paramName, values[0]);
                }
                else { // multi valued
                    args.put(paramName, values);
                }
            }
        }
    }

    private void setMethod(HttpServletRequest req)
    {
        method = req.getMethod();
    }
}
