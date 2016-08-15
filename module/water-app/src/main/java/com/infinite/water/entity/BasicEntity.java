package com.infinite.water.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BasicEntity implements Serializable {
}
