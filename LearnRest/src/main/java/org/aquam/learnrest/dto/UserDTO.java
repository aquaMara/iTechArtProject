package org.aquam.learnrest.dto;

import lombok.*;
import org.aquam.learnrest.exception.EmptyInputException;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.model.UserRole;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long userId;
    // @NotBlank(message = "Can not be empty")
    private UserRole userRole;
    @NotBlank(message = "Can not be empty")  // not null + no whitespaces
    private String username;
    @Length(min = 6, max = 16, message = "Between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Can not be empty")
    private String name;
    @NotBlank(message = "Can not be empty")
    @Email(message = "Valid only")
    private String email;

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    /*
    public AppUser toUserMap(@Valid UserDTO userDTO) {
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
        return AppUser.builder()
                .userRole(userDTO.getUserRole())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
    }
     */


    // все поля меняем при update
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
        AppUser user = new AppUser();
        user.setUserRole(userDTO.getUserRole());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

}
