package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.ScoreServiceModel;

import java.security.Principal;

public interface SongService {
    void saveScore(String name, ScoreServiceModel map);
    ScoreServiceModel getSongScore(Long songId, Principal principal);
}
