package com.infinite.framework.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by hx handler 16-6-15.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AbstractEntity implements Serializable {
}
