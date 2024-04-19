package com.example.emmisiontracker.controller;

import com.example.emmisiontracker.annotation.GraphQLController;
import com.example.emmisiontracker.domain.user.CredentialsInputDto;
import com.example.emmisiontracker.domain.user.User;
import com.example.emmisiontracker.domain.user.UserDto;
import com.example.emmisiontracker.service.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLRootContext;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;

import javax.security.auth.login.LoginException;
import java.util.Map;

@GraphQLController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GraphQLMutation
    public User registerNewUser(@GraphQLArgument UserDto userDto) {
        return userService.registerNewUser(userDto);
    }

    @GraphQLMutation
    public Map<String, String> login(@GraphQLArgument CredentialsInputDto credentialsInputDto, @GraphQLRootContext DefaultGlobalContext<NativeWebRequest> context) {
        return userService.login(credentialsInputDto);
    }

}
