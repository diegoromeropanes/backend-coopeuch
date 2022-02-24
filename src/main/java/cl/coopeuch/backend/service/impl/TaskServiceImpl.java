package cl.coopeuch.backend.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cl.coopeuch.backend.entity.Task;
import cl.coopeuch.backend.mappers.TaskMapper;
import cl.coopeuch.backend.response.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.coopeuch.backend.dto.TaskDTO;
import cl.coopeuch.backend.repository.TaskRepository;
import cl.coopeuch.backend.request.TaskRequest;
import cl.coopeuch.backend.service.TaskService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	TaskRepository repository;

	public ResponseEntity<List<TaskDTO>> getTasksList(){
		List<TaskDTO> response = new ArrayList<>();
		try {
			List<Task> taskList = repository.findAll().stream().filter(task -> task.isEnabled()).collect(Collectors.toList());
			if(Objects.nonNull(taskList) && !taskList.isEmpty()) {
				for(Task task : taskList) {
					response.add(TaskMapper.toTaskDto(task));
				}
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else{
				log.info("Lista vacía");
				return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			log.error("Exception: {}", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<TaskResponse> findById(Long id) {
		TaskResponse response = new TaskResponse();
		try{
			Optional<Task> task = repository.findById(id);
			if(task.isPresent()){
				response.setMessage(HttpStatus.OK.toString());
				response.setStatus(HttpStatus.OK.value());

				response.setTask(TaskMapper.toTaskDto(task.get()));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setMessage(HttpStatus.NOT_FOUND.toString());
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}catch (Exception e){
			return serverErrorResponse(response, e);
		}
	}

	@Override
	public ResponseEntity<TaskResponse> create(TaskRequest request) {
		TaskResponse response = new TaskResponse();
		try {
			Task task = repository.save(new Task()
					.setDescription(request.getDescription())
					.setEnabled(request.isEnabled()))
					.setCreateAt(new Date());

			if(Objects.isNull(task)){
				response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			response.setTask(TaskMapper.toTaskDto(task));
			response.setMessage(HttpStatus.CREATED.toString());
			response.setStatus(HttpStatus.CREATED.value());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}catch(Exception e) {
			return serverErrorResponse(response, e);
		}
	}

	@Override
	public ResponseEntity<TaskResponse> update(TaskRequest request) {
		TaskResponse response = new TaskResponse();
		try {
			ResponseEntity<TaskResponse> taskResponse = findById(request.getId());
			if(taskResponse.getStatusCode().value() == 200) {
				TaskDTO taskDTO = taskResponse.getBody().getTask();

				Task task = repository.save(new Task().setId(taskDTO.getId())
						.setDescription(request.getDescription())
						.setEnabled(request.isEnabled()));

				response.setTask(TaskMapper.toTaskDto(task));
				response.setMessage(HttpStatus.CREATED.toString());
				response.setStatus(HttpStatus.CREATED.value());
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}
			return taskResponse;
		}catch (Exception e){
			return serverErrorResponse(response, e);
		}
	}

	@Override
	public ResponseEntity<TaskResponse> delete(Long id) {
		TaskResponse response = new TaskResponse();
		try {
			repository.deleteById(id);
			
			response.setMessage(HttpStatus.OK.toString());
			response.setStatus(HttpStatus.OK.value());
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch(Exception e) {
			return serverErrorResponse(response, e);
		}
	}

	private ResponseEntity<TaskResponse> serverErrorResponse(TaskResponse response, Exception e){
		log.error("Exception: {}", e.getMessage());
		response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
