package com.kytoon.frameworks_project_backend.service.user;

import com.kytoon.frameworks_project_backend.exception.UserNotFoundException;
import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.repository.UserRepository;
import com.kytoon.frameworks_project_backend.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("user not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(AddUserRequest user) {
        return Optional.of(user).filter(u->!userRepository.existsByEmail(u.getEmail()))
                .map(u->{
                    User newUser = new User();
                    newUser.setUsername(u.getUsername());
                    newUser.setUserType(u.getUserType());
                    newUser.setEmail(u.getEmail());
                    newUser.setPassword(u.getPassword());
                    return userRepository.save(newUser);
                }).orElseThrow(()-> new UserNotFoundException("user already exists"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
