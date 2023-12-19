package com.portfolio.blog.domain.test.resolver;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@GraphQLApi
public class TestResolver {

    @GraphQLQuery(name = "testString")
    public String testString(
            @GraphQLArgument(name = "tests") String tests
    ) {
        return tests;
    }
}
