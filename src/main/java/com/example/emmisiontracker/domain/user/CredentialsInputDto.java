package com.example.emmisiontracker.domain.user;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.Data;

@GraphQLType
@Data
public class CredentialsInputDto {

    private String email;
    private String password;

}
