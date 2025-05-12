package exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;

import Exception.UserExistingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserServiceExceptionTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test getAllUsers : exception simulée
    @Test
    public void getAllUsers_ShouldThrowRuntimeException() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Erreur BDD"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.getAllUsers();
        });

        assertTrue(thrown.getMessage().contains("récupération de la liste"));
    }

    // Test getUserById : exception simulée
    @Test
    public void getUserById_ShouldThrowRuntimeException() {
        when(userRepository.findById(anyInt())).thenThrow(new RuntimeException("Erreur BDD"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1);
        });

        assertTrue(thrown.getMessage().contains("récupération du User"));
    }

    // Test addUser : utilisateur existant
    @Test
    public void addUser_WhenUserAlreadyExists_ShouldThrowUserExistingException() {
        User existing = new User();
        existing.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(existing);

        User newUser = new User();
        newUser.setUsername("existingUser");
        newUser.setPassword("Password@123!");
        newUser.setFullname("Nicolas");
        newUser.setRole("USER");

        assertThrows(UserExistingException.class, () -> {
            userService.addUser(newUser);
        });
    }

    // Test updateUser : username déjà utilisé par un autre user
    @Test
    public void updateUser_WhenUsernameExistsOnAnotherUser_ShouldThrowUserExistingException() {
        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setUsername("duplicateUser");

        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("duplicateUser");
        userToUpdate.setPassword("Password@123!");
        userToUpdate.setFullname("Test User");
        userToUpdate.setRole("USER");

        when(userRepository.findByUsername("duplicateUser")).thenReturn(existingUser);

        assertThrows(UserExistingException.class, () -> {
            userService.updateUser(1, userToUpdate);
        });
    }

    // Test deleteUser : exception lors de delete()
    @Test
    public void deleteUser_WhenDeleteFails_ShouldThrowRuntimeException() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test");

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        doThrow(new RuntimeException("Erreur suppression")).when(userRepository).delete(user);

        assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(1);
        });
    }
}
