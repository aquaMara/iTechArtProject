package org.aquam.learnrest.dto;

import lombok.*;
import org.aquam.learnrest.model.UserRole;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "Can not be empty")
    @Length(min = 6, max = 16, message = "Between 6 and 16 characters")
    private String password;
    @NotBlank(message = "Can not be empty")
    private String name;
    @NotBlank(message = "Can not be empty")
    @Email(message = "Valid only")
    private String email;

}
