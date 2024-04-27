package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music;

public class MusicCurrentlyPlayedDto {
    private String name;
    private String artist;
    private String album;
    private String uri;
    private String imageUrl;
    private int durationMs;
    private int progressMs;

    public MusicCurrentlyPlayedDto(String name, String artist, String album, String uri, String imageUrl, int durationMs, int progressMs) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.uri = uri;
        this.imageUrl = imageUrl;
        this.durationMs = durationMs;
        this.progressMs = progressMs;
    }

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

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public int getProgressMs() {
        return progressMs;
    }

    public void setProgressMs(int progressMs) {
        this.progressMs = progressMs;
    }

    @Override
    public String toString() {
        return "MusicCurrentlyPlayedDto{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", uri='" + uri + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", durationMs=" + durationMs +
                ", progressMs=" + progressMs +
                '}';
    }
}
