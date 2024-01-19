package com.portfolio.blog.domain.test.datafetcher;

import com.netflix.graphql.dgs.*;
import com.portfolio.blog.types.Actor;
import com.portfolio.blog.types.Show;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class TestDataFetcher {
    private final List<Show> shows = List.of(
            new Show(1, "Stranger Things", 2016, null),
            new Show(2, "Ozark", 2017, null),
            new Show(3, "The Crown", 2016, null),
            new Show(4, "Dead to Me", 2019, null),
            new Show(5, "Orange is the New Black", 2013, null)
    );

    private final List<Actor> actors = List.of(
            new Actor("빈지노", 32, shows.get(1)),
            new Actor("이센스", 32, shows.get(1)),
            new Actor("쏜애플", 4, shows.get(2)),
            new Actor("아도", 20, shows.get(4)),
            new Actor("요루시카", 12, shows.get(4))
    );

    @DgsQuery
    public List<Show> test(@InputArgument String testString) {
        if(testString == null) {
            return shows;
        }

        return shows.stream().filter(show -> show.getTitle().contains(testString)).collect(Collectors.toList());
    }

    @DgsData(parentType = "Show", field = "actors")
    public List<Actor> showActors(DgsDataFetchingEnvironment dfe) {
        Show show = dfe.getSource();

        return actors.stream().filter(actor -> actor.getShows().getId().equals(show.getId())).collect(Collectors.toList());
    }

}
