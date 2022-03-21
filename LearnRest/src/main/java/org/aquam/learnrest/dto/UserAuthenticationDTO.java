package org.aquam.learnrest.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuthenticationDTO {

    private String username;
    private String password;
}
