package com.ih.ailh.dto;

import com.ih.ailh.model.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String preferences;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}