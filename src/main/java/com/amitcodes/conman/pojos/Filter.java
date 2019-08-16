package com.amitcodes.conman.pojos;

import java.util.Map;

/**
 * Created by raiabhij on 12/7/2017.
 */
public class Filter {
    private String fqcn;
    private Map<String, String> initParams;

    public String getFqcn() {
        return fqcn;
    }

    public void setFqcn(String fqcn) {
        this.fqcn = fqcn;
    }

    public Map<String, String> getInitParams() {
        return initParams;
    }

    public void setInitParams(Map<String, String> initParams) {
        this.initParams = initParams;
    }
}
