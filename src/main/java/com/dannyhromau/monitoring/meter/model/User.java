package com.dannyhromau.monitoring.meter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;
    private boolean isDeleted;
    private List<Authority> authorities;

}
