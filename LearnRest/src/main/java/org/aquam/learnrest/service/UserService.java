package org.aquam.learnrest.service;

import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Long id);
    List<AppUser> findAll();
    void create(AppUser user);
    void update(AppUser user);
    void delete(AppUser user);
    void registerUser(AppUser user);

}
