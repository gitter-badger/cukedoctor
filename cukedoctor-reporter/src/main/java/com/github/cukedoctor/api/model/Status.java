package com.github.cukedoctor.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pestano on 04/06/15.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
    passed, failed, skipped, pending,
    undefined, missing;

}