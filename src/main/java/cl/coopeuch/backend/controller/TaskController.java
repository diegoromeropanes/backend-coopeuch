package cl.coopeuch.backend.controller;

import java.util.List;

import cl.coopeuch.backend.dto.TaskDTO;
import cl.coopeuch.backend.response.ListResponse;
import cl.coopeuch.backend.response.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.coopeuch.backend.request.TaskRequest;
import cl.coopeuch.backend.service.TaskService;

import javax.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	TaskService service;

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListResponse> getTasksList(){
		return service.getTasksList();
	}

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request){
		return service.create(request);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<TaskResponse> update(@Valid @RequestBody(required = true) TaskRequest request){
		return service.update(request);
	}

	@GetMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id){
		return service.findById(id);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<TaskResponse> delete(@PathVariable(required = true) Long id){
		return service.delete(id);
	}

}
