package org.eventgridtrigger.function.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventGridEvent implements Serializable {

  @JsonProperty(value = "id", required = true)
  private String id;

  @JsonProperty(value = "topic")
  private String topic;

  @JsonProperty(value = "subject", required = true)
  private String subject;

  @JsonProperty(value = "data", required = true)
  private EventGridData data;

  @JsonProperty(value = "eventType", required = true)
  private String eventType;

  @JsonProperty(value = "eventTime", required = true)
  private String eventTime;

  @JsonProperty(value = "metadataVersion")
  private String metadataVersion;

  @JsonProperty(value = "dataVersion", required = true)
  private String dataVersion;
}
