package com.portfolio.blog.domain.test.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.portfolio.blog.types.Show;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class TestDataFetcher {
    private final List<Show> shows = List.of(
            new Show("Stranger Things", 2016),
            new Show("Ozark", 2017),
            new Show("The Crown", 2016),
            new Show("Dead to Me", 2019),
            new Show("Orange is the New Black", 2013)
    );

    @DgsQuery
    public List<Show> test(@InputArgument String testString) {
        if(testString == null) {
            return shows;
        }

        return shows.stream().filter(show -> show.getTitle().contains(testString)).collect(Collectors.toList());
    }

}
