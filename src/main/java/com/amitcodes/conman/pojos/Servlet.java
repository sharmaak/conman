package com.amitcodes.conman.pojos;

import java.util.List;

/**
 * Created by raiabhij on 12/5/2017.
 */
public class Servlet {
    private String fqcn;
    private String uriPath;
    private List<Filter> filters;

    public String getFqcn() {
        return fqcn;
    }

    public void setFqcn(String fqcn) {
        this.fqcn = fqcn;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
