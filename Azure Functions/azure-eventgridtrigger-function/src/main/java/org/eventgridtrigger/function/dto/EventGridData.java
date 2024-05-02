package org.eventgridtrigger.function.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventGridData implements Serializable {

  @JsonProperty(value = "api")
  private String api;

  @JsonProperty(value = "clientRequestId")
  private String clientRequestId;

  @JsonProperty(value = "requestId")
  private String requestId;

  @JsonProperty(value = "eTag")
  private String eTag;

  @JsonProperty(value = "contentType")
  private String contentType;

  @JsonProperty(value = "contentLength")
  private Integer contentLength;

  @JsonProperty(value = "blobType")
  private String blobType;

  @JsonProperty(value = "url")
  private String url;

  @JsonProperty(value = "sequencer")
  private String sequencer;

  @JsonProperty(value = "storageDiagnostics")
  private EventGridStorageDiagnostics storageDiagnostics;
}
