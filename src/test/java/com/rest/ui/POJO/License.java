package com.rest.ui.POJO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class License {

    @JsonProperty("spdx_id")
    private String spdx;

    public String getSpdx() {
        return spdx;
    }

    public void setSpdx(String spdx) {
        this.spdx = spdx;
    }
}
