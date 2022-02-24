package cl.coopeuch.backend.mappers;

import cl.coopeuch.backend.dto.TaskDTO;
import cl.coopeuch.backend.entity.Task;

public class TaskMapper {
    public static TaskDTO toTaskDto(Task obj) {
        return new TaskDTO().setId(obj.getId())
                .setDescription(obj.getDescription())
                .setCreateAt(obj.getCreateAt())
                .setEnabled(obj.isEnabled());
    }
}
