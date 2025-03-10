package budhioct.dev.security.module;

public interface UserService {
    UserDTO.RegisterResponse register(UserDTO.RegisterRequest request);
}
