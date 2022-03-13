package org.aquam.learnrest.service.impl;

import lombok.AllArgsConstructor;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.repository.UserRepository;
import org.aquam.learnrest.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String ERROR_MESSAGE = "User with username: %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(ERROR_MESSAGE, username)));
    }

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    // fields to update : username, email, name
    @Override
    public void update(AppUser user) {
    }

    @Override
    public void delete(AppUser user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            userRepository.delete(user);
    }

    @Override
    public void create(AppUser user) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty())
            registerUser(user);
    }

    // обработать исключение, если не создался?
    @Override
    public void registerUser(AppUser user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        create(user);
    }

}
