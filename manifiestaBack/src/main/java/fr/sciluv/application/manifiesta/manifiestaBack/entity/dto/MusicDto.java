package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto;

public class MusicDto {
    // Attributes from Music and MusicStreamingServiceInformation
    private String name;
    private String artist;
    private String album;
    private String uri;
    private String imageUrl;
    private Long durationMs;

    // Constructors

    public MusicDto(String name, String artist, String album, String uri, String imageUrl, Long durationMs) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.uri = uri;
        this.imageUrl = imageUrl;
        this.durationMs = durationMs;
    }


    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }
}
