package org.eventgrid.events.to.HTTPendpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventGridStorageDiagnostics implements Serializable {
  private static final long serialVersionUID = 1L;
  @JsonProperty(value = "batchId")
  private String batchId;
}
