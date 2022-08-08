package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.Rating;
import bg.manhattan.singerscontests.model.entity.Song;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.ScoreServiceModel;
import bg.manhattan.singerscontests.repositories.SongRepository;
import bg.manhattan.singerscontests.services.SongService;
import bg.manhattan.singerscontests.services.UserService;
import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SongServiceImpl(SongRepository repository, UserService userService, ModelMapper modelMapper) {
        this.repository = repository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveScore(String name, ScoreServiceModel map) {
        throw new NotImplementedException();
    }

    @Override
    public ScoreServiceModel getSongScore(Long songId, Principal principal) {
        Song song = this.repository.findById(songId)
                .orElseThrow(() -> new NotFoundException("Song", songId));
        User user = this.userService.getCurrentUser(principal);
        Rating rating = song.getRatings()
                .stream()
                .filter(r -> r.getJuryMember().getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(new Rating().setSong(song));
        ScoreServiceModel result = this.modelMapper.map(rating, ScoreServiceModel.class);

        return result.setEditionId(song.getContestant().getEdition().getId())
                .setContestantFullName(rating.getSong().getContestant().getFullName())
                .setContestantImageUrl(song.getContestant().getImageUrl());
    }
}
