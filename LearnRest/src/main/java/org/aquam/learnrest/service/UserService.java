package org.aquam.learnrest.service;

import org.aquam.learnrest.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Long id);
    List<AppUser> findAll();
    AppUser updateById(Long userId, AppUser changedUser);
    boolean deleteById(Long userId);
    AppUser registerUser(AppUser user);

}
