package com.example.emmisiontracker.domain.user;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@GraphQLType
@Data
public class UserDto {

    private String username;
    private String email;
    private String password;
    private String profilePictureURL;

}
