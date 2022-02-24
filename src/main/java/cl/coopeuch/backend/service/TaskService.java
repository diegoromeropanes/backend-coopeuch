package cl.coopeuch.backend.service;

import java.util.List;

import cl.coopeuch.backend.response.TaskResponse;
import org.springframework.http.ResponseEntity;

import cl.coopeuch.backend.dto.TaskDTO;
import cl.coopeuch.backend.request.TaskRequest;

public interface TaskService {
	ResponseEntity<List<TaskDTO>> getTasksList();
	ResponseEntity<TaskResponse> findById(Long id);
	ResponseEntity<TaskResponse> create(TaskRequest request);
	ResponseEntity<TaskResponse> update(TaskRequest request);
	ResponseEntity<TaskResponse> delete(Long id);
}