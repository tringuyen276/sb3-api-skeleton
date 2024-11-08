package com.digiex.utility.web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String lastName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<String> roles;
}
