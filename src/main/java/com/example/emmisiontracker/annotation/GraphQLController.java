package com.example.emmisiontracker.annotation;

import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@GraphQLApi
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface GraphQLController { }
