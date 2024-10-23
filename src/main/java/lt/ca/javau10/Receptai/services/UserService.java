package lt.ca.javau10.Receptai.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.entities.UserEntity;
import lt.ca.javau10.Receptai.repositories.UserRepository;
import lt.ca.javau10.Receptai.utils.EntityMapper;



@Service
public class UserService implements UserDetailsService  {

	private final UserRepository userRepository;
	private final EntityMapper entityMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	public UserService(UserRepository userRepository, EntityMapper entityMapper) {
		this.userRepository = userRepository;
		this.entityMapper = entityMapper;
	}
	
	
	
	

	//sukuria vartotoja
	public UserDto createUser(UserDto userDto) {
		UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);

		UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);		
		
		return entityMapper.toUserDto(userEntityAfterSave);		
	}
	//randa visus vartotojus
	public List<UserDto> getAllUsers(){
		List<UserEntity> users = userRepository.findAll();
		
		return users.stream()
				.map(entityMapper::toUserDto)
				.toList();		
	}
	//randa user su id
	public Optional<UserDto> getUserById(Long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		
		return user.map(entityMapper::toUserDto);
	}
	//editina useri su id
	public Optional<UserDto> patchUpdateUser(Long id, UserDto userDto) {
	    Optional<UserEntity> existingUserOpt = userRepository.findById(id);
	    
	    if (existingUserOpt.isPresent()) {
	        UserEntity existingUser = existingUserOpt.get();

	        if (userDto.getUsername() != null) {
	            existingUser.setUsername(userDto.getUsername());
	        }
	        if (userDto.getEmail() != null) {
	            existingUser.setEmail(userDto.getEmail());
	        }
	        UserEntity updatedUser = userRepository.save(existingUser);
	        return Optional.of(entityMapper.toUserDto(updatedUser));
	    } else {
	        return Optional.empty();  
	    }
	}
	//istrina vartotoja su id
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository
							.findByUsername(username)
							.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		
		logger.info("Loaded :"+user.toString());
		return entityMapper.toUserDto(user);
	}
	
}