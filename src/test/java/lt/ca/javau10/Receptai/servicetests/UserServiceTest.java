package lt.ca.javau10.Receptai.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.entities.UserEntity;
import lt.ca.javau10.Receptai.repositories.UserRepository;
import lt.ca.javau10.Receptai.services.UserService;
import lt.ca.javau10.Receptai.utils.EntityMapper;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        // Set up UserDto and UserEntity
        userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");

        userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@example.com");
    }

    @Test
    public void testCreateUser() {
        when(entityMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(entityMapper.toUserDto(userEntity)).thenReturn(userDto);

        UserDto createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void testGetAllUsers() {
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUsername("anotherUser");
        userEntity2.setEmail("another@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity, userEntity2));
        when(entityMapper.toUserDto(userEntity)).thenReturn(userDto);
        when(entityMapper.toUserDto(userEntity2)).thenReturn(new UserDto(1L,"anotherUser", "another@example.com"));

        List<UserDto> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        assertEquals("anotherUser", users.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_Exists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(entityMapper.toUserDto(userEntity)).thenReturn(userDto);

        Optional<UserDto> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_NotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserDto> foundUser = userService.getUserById(1L);

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testPatchUpdateUser() {
        UserDto updateDto = new UserDto();
        updateDto.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(entityMapper.toUserDto(userEntity)).thenReturn(userDto);

        Optional<UserDto> updatedUser = userService.patchUpdateUser(1L, updateDto);

        assertTrue(updatedUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void testPatchUpdateUser_NotExists() {
        UserDto updateDto = new UserDto();
        updateDto.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserDto> updatedUser = userService.patchUpdateUser(1L, updateDto);

        assertFalse(updatedUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testLoadUserByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));
        when(entityMapper.toUserDto(userEntity)).thenReturn(userDto);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }


}
