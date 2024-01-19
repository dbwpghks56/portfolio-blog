package com.portfolio.blog.domain.test.datafetcher;

import com.netflix.graphql.dgs.*;
import com.portfolio.blog.types.Actor;
import com.portfolio.blog.types.Show;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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

    @DgsQuery
    public List<Show> test(@InputArgument String testString) {
        if(testString == null) {
            return shows;
        }

        return shows.stream().filter(show -> show.getTitle().contains(testString)).collect(Collectors.toList());
    }

    @DgsData(parentType = "Show", field = "actors")
    public CompletableFuture<List<Actor>> showActors(DgsDataFetchingEnvironment dfe) {
        DataLoader<Integer, List<Actor>> actorLoader = dfe.getDataLoader("actorLoader");
        Show show = dfe.getSource();

        return actorLoader.load(show.getId());
    }

}
