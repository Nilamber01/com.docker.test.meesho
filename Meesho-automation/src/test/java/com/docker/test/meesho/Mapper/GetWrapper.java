package com.docker.test.meesho.Mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWrapper {

    private List<GetResponseMapper> data = null;

    public List<GetResponseMapper> getData() {
        return data;
    }

}
