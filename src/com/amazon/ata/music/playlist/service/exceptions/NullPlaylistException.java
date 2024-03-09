package com.amazon.ata.music.playlist.service.exceptions;

import javax.swing.plaf.basic.BasicTreeUI;

public class NullPlaylistException extends RuntimeException {


    private static final long serialVersionUID = -2962380546830778129L;

    public NullPlaylistException() {
        super();
    }
    
    public NullPlaylistException(String message) {
        super(message);
    }
    
    public NullPlaylistException(Throwable cause) {
        super(cause);
    }
    
    public NullPlaylistException(String message, Throwable cause) {
        super(message, cause);
    }
}
