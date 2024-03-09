package com.amazon.ata.music.playlist.service.converters;

import com.amazon.ata.music.playlist.service.dynamodb.models.AlbumTrack;
import com.amazon.ata.music.playlist.service.models.PlaylistModel;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.models.SongModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelConverter {
    /**
     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
     * @param playlist the playlist to convert
     * @return the converted playlist
     */
    public static PlaylistModel toPlaylistModel(Playlist playlist) {
        List<String> tagsToConvert = null;

        if (playlist.getTags() != null && !playlist.getTags().isEmpty()) {
            tagsToConvert = new ArrayList<>(playlist.getTags());
        }

        return PlaylistModel.builder()
            .withId(playlist.getId()).withName(playlist.getName()).withCustomerId(playlist.getCustomerId()).withSongCount(playlist.getSongCount())
                .withTags(tagsToConvert).build();
    }

    /**
     * Converts a provided {@link AlbumTrack} into a {@link SongModel} representation.
     * @param albumTrack the albumTrack to convert
     * @return the converted albumTrack
     */
    public static SongModel toSongModel(AlbumTrack albumTrack) {
        return SongModel.builder()
                .withAsin(albumTrack.getAsin())
                .withTrackNumber(albumTrack.getTrackNumber())
                .withAlbum(albumTrack.getAlbumName())
                .withTitle(albumTrack.getSongTitle())
                .build();
    }

    /**
     * Leverages toSongModel() with a for loop to convert a {@link List<AlbumTrack>} to a {@link List<SongModel>}
     * @param songs the {@link List<AlbumTrack>} to be converted
     * @return the converted list
     */
    public static List<SongModel> toSongModelList(List<AlbumTrack> songs) {

        if (songs == null || songs.isEmpty()) {
            return new LinkedList<>();
        }

        List<SongModel> songModelList = new LinkedList<>();
        for (AlbumTrack song : songs) {
            songModelList.add(toSongModel(song));
        }

        return songModelList;
    }
}
