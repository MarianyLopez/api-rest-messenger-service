package com.api.messengerservice.dtos;

import com.api.messengerservice.utils.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NotNull(message = "The id cannot be null")
    private Integer id;

    @Column(nullable = false)
    private String name;
    @NotNull(message = "The email cannot be null")
    private String email;

    @NotNull(message = "The password cannot be null")
    private String password;

    @NotNull(message = "The role cannot be null")
    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer IdAdmin;

}
