package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MusicListDto {

    @JsonProperty("musics")
    List<MusicDto> musics = new ArrayList<>();

    public MusicListDto(List<MusicDto> musics) {
        this.musics = musics;
    }
}
