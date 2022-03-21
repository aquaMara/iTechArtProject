package org.aquam.learnrest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;
}
