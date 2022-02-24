package cl.coopeuch.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskRequest {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	private String description;

	@JsonProperty("enabled")
	private boolean enabled;
}