package cl.coopeuch.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class TaskRequest {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("description")
	@NotNull
	private String description;

	@JsonProperty("enabled")
	private boolean enabled;
}