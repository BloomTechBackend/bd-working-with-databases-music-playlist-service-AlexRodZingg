package dynamodb;

import com.amazon.ata.music.playlist.service.dynamodb.PlaylistDao;
import com.amazon.ata.music.playlist.service.dynamodb.models.Playlist;
import com.amazon.ata.music.playlist.service.exceptions.NullPlaylistException;
import com.amazon.ata.music.playlist.service.exceptions.PlaylistNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlaylistDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private PlaylistDao playlistDao;

    private Playlist realPlaylist;
    private Playlist emptyPlaylist;
    private Playlist nullPlaylist;
    private Playlist noPlaylistId;

    @BeforeEach
    private void setup() {
        initMocks(this);
        emptyPlaylist = new Playlist();
        noPlaylistId = new Playlist();
        noPlaylistId.setId("");
        nullPlaylist = null;
        realPlaylist = new Playlist();
        realPlaylist.setId("111");
        realPlaylist.setName("Johnny");
        realPlaylist.setCustomerId("A1A");
    }

    @Test
    void getPlaylist_validPlaylist_returnsPlaylist() {
        when(dynamoDBMapper.load(Playlist.class, realPlaylist.getId())).thenReturn(realPlaylist);

        String id = realPlaylist.getId();
        Playlist testPlaylist = playlistDao.getPlaylist(id);

        assertEquals(realPlaylist, testPlaylist, "PlaylistDao doesn't properly return requested Playlist");
    }

    @Test
    void getPlaylist_playlistIsNull_throwsPlaylistNotFoundException() {
        String nullPlaylistId = "10101";
        when(dynamoDBMapper.load(Playlist.class, nullPlaylistId)).thenReturn(nullPlaylist);

        assertThrows(PlaylistNotFoundException.class, () -> playlistDao.getPlaylist(nullPlaylistId),
                "Retrieving null playlist from playlistDao.getPlaylist() should have thrown PlaylistNotFoundException");
    }

    @Test
    void savePlaylist_savesValidPlaylist() {
        Playlist testPlaylist = playlistDao.savePlaylist(realPlaylist);

        verify(dynamoDBMapper).save(realPlaylist);
        assertEquals(realPlaylist, testPlaylist, "Playlist should not have changed after executing .save()");
    }

    @Test
    void savePlaylist_savesNullPlaylist_throwsNullPlaylistException() {
        assertThrows(NullPlaylistException.class, () -> playlistDao.savePlaylist(nullPlaylist));
    }

    @Test
    void savePlaylist_playlistIsEmpty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> playlistDao.savePlaylist(emptyPlaylist));
    }

    @Test
    void savePlaylist_playlistIdIsEmpty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> playlistDao.savePlaylist(noPlaylistId));
    }
}
