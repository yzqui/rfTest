package com.rf.link.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class LinkRequest {

  @NotEmpty
  private String originalUrl;
}
