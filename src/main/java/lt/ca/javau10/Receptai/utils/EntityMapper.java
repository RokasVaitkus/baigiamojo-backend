package lt.ca.javau10.Receptai.utils;

import org.springframework.stereotype.Component;

import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.entities.UserEntity;


@Component
public class EntityMapper {
	
	public UserEntity toUserEntity(UserDto dto) {
		
		UserEntity entity = new UserEntity();
		entity.setId( dto.getId());
		entity.setUsername( dto.getUsername());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());	
		return entity;		
	}
	
	
	public UserDto toUserDto(UserEntity entity) {
		return new UserDto( 
				entity.getId(), 
				entity.getUsername(), 
				entity.getEmail(), 
				entity.getPassword(), 
				entity.getRoles() 
			);
	}
	
}
