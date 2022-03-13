package org.aquam.learnrest.service;

import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Long id);
    List<AppUser> findAll();
    AppUser update(AppUser user);
    void delete(AppUser user);
    void deleteById(Long userId);
    AppUser registerUser(AppUser user);

}
