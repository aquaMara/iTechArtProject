package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.dto.UserDTO;
import org.aquam.learnrest.dto.mapper.UserMapper;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.repository.UserRepository;
import org.aquam.learnrest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String USERNAME_ERROR_MESSAGE = "User with username: %s not found";
    private final String ID_ERROR_MESSAGE = "User with id: %s not found";
    private final String EXISTS_ERROR_MESSAGE = "User with username: %s already exists";
    private final String NULL_POINTER_ERROR_MESSAGE = "There are no users";

    private final ModelMapper modelMapper;
    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_ERROR_MESSAGE, username)));
    }

    @Override
    public AppUser findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, userId)));
    }

    @Override
    public List<AppUser> findAll() {
        if (userRepository.findAll().isEmpty())
            throw new NullPointerException(NULL_POINTER_ERROR_MESSAGE);
        return userRepository.findAll();
    }

    @Override
    public AppUser updateById(Long userId, AppUser changedUser) {
        if (userRepository.findById(userId).isPresent()) {
            AppUser user = userRepository.getById(userId);
            user.setName(changedUser.getName());
            user.setEmail(changedUser.getEmail());
            user.setUsername(changedUser.getUsername());
            return user;
        }
        throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, userId));
    }

    @Override
    public AppUser updateById(Long userId, UserDTO changedUserDTO) {
        AppUser changedUser = toUser(changedUserDTO);
        if (userId == null || userRepository.findById(userId).isEmpty())
            throw new EntityNotFoundException(String.format("User with id: %s is not found", userId));
        AppUser oldUser = userRepository.getById(userId);
        oldUser.setUsername(changedUser.getUsername());
        oldUser.setPassword(changedUser.getPassword());
        oldUser.setName(changedUser.getName());
        oldUser.setEmail(changedUser.getEmail());
        return oldUser;
    }

    @Override
    public AppUser registerUser(AppUser user) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
        throw new EntityExistsException(String.format(EXISTS_ERROR_MESSAGE, user.getUsername()));
    }

    @Override
    public AppUser registerUser(UserDTO userDTO) {
        AppUser user = toUser(userDTO);
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new EntityExistsException(String.format("User with username: %s already exists", user.getUsername()));
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        throw new EntityNotFoundException(String.format(ID_ERROR_MESSAGE, userId));
    }

    public AppUser toUser(@Valid UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> validationExceptions = validator.validate(userDTO);
        if (!validationExceptions.isEmpty()) {
            throw new ConstraintViolationException(validationExceptions);
        }
        if (userDTO.getUserRole() == null
                || userDTO.getUsername() == null || userDTO.getPassword() == null
                || userDTO.getName() == null || userDTO.getEmail() == null)
            throw new NullPointerException("Some fields are null");
        if (userDTO.getUsername().isBlank() || userDTO.getPassword().isBlank()
                || userDTO.getName().isBlank() || userDTO.getEmail().isBlank())
            throw new EmptyInputException("Some fields are empty");
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        return user;
    }
}
