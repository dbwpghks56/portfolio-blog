package com.portfolio.blog.domain.test.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import com.portfolio.blog.types.Actor;
import com.portfolio.blog.types.Show;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;
import org.dataloader.MappedBatchLoader;
import org.dataloader.Try;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@DgsDataLoader(name = "actorLoader")
public class TestDataLoader implements MappedBatchLoader<Integer, List<Actor>> {
    private final List<Actor> actors = List.of(
            new Actor("빈지노", 32, new Show(1, "Stranger Things", 2016, null)),
            new Actor("이센스", 32, new Show(1, "Stranger Things", 2016, null)),
            new Actor("쏜애플", 4, new Show(2, "Stranger Things", 2016, null)),
            new Actor("아도", 20, new Show(3, "Stranger Things", 2016, null)),
            new Actor("요루시카", 12, new Show(4, "Stranger Things", 2016, null))
    );
    @Override
    public CompletionStage<Map<Integer, List<Actor>>> load(Set<Integer> keys) {
        Map<Integer, List<Actor>> actorList = new HashMap<>();

// Assuming 'keys' is a collection of integers and 'actors' is a collection of Actor objects
        keys.forEach(integer -> {
            if (!actorList.containsKey(integer)) {
                actorList.put(integer, new ArrayList<>());
            }
            actorList.get(integer).addAll(actors.stream()
                    .filter(actor -> actor.getShows().getId().equals(integer))
                    .toList());
        });


        return CompletableFuture.supplyAsync(() -> actorList);
    }
}
