package com.shakeel.firebasefileuploade;

import android.net.Uri;

import java.io.InputStream;

public class Upload {
    private Uri uri;
    private byte[] bytes;
    private InputStream stream;

    public Upload(Uri uri) {
        this.uri = uri;
    }

    public Upload() {
    }

    public Upload(byte[] bytes) {
        this.bytes = bytes;
    }

    public Upload(InputStream stream) {
        this.stream = stream;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}
