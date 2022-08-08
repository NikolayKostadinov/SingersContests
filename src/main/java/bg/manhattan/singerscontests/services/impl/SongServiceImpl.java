package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.Rating;
import bg.manhattan.singerscontests.model.entity.Song;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.ScoreServiceModel;
import bg.manhattan.singerscontests.repositories.SongRepository;
import bg.manhattan.singerscontests.services.SongService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final UserService userService;
    private final ModelMapper mapper;

    public SongServiceImpl(SongRepository repository, UserService userService, ModelMapper modelMapper) {
        this.repository = repository;
        this.userService = userService;
        this.mapper = modelMapper;
    }

    @Override
    public void saveScore(ScoreServiceModel scoreModel, Principal principal) {
        User currentUser = this.userService.getCurrentUser(principal);
        Song song = this.getSongEntity(scoreModel.getSongId());
        Rating rating = song
                .getRatings()
                .stream()
                .filter(r -> r.getJuryMember().getUsername().equals(currentUser.getUsername()))
                .findFirst()
                .orElse(new Rating());

        rating.setArtistry(scoreModel.getArtistry())
                .setIntonation(scoreModel.getIntonation())
                .setRepertoire(scoreModel.getRepertoire())
                .setAverageScore(scoreModel.getAverageScore());

        if (rating.getId() == null) {
            rating.setJuryMember(currentUser);
            song.getRatings().add(rating);
        }

        this.repository.save(song);
    }

    @Override
    public ScoreServiceModel getSongScore(Long songId, Principal principal) {
        Song song = getSongEntity(songId);
        User user = this.userService.getCurrentUser(principal);
        Rating rating = song.getRatings()
                .stream()
                .filter(r -> r.getJuryMember().getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(new Rating().setSong(song));
        ScoreServiceModel result = this.mapper.map(rating, ScoreServiceModel.class);

        return result.setEditionId(song.getContestant().getEdition().getId())
                .setContestantFullName(rating.getSong().getContestant().getFullName())
                .setContestantImageUrl(song.getContestant().getImageUrl());
    }

    private Song getSongEntity(Long songId) {
        return this.repository.findById(songId)
                .orElseThrow(() -> new NotFoundException("Song", songId));
    }
}
