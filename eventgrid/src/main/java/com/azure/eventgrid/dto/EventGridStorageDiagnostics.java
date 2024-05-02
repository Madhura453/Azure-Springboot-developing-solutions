package com.azure.eventgrid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventGridStorageDiagnostics implements Serializable {

  @JsonProperty(value = "batchId")
  private String batchId;
}
