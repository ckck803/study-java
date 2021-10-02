package com.example.javahttpclient.wrapper;

import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProgressEntityWrapper extends HttpEntityWrapper {
    private ProgressListener progressListener;


    public ProgressEntityWrapper(HttpEntity wrappedEntity, ProgressListener progressListener) {
        super(wrappedEntity);
        this.progressListener = progressListener;
    }

    @Override
    public void writeTo(OutputStream outStream) throws IOException {
        super.writeTo(new CountingOutputStream(outStream, progressListener, getContentLength()));
    }

    public interface ProgressListener {
        void progress(float percentage);
    }

    public static class CountingOutputStream extends FilterOutputStream {
        private ProgressListener listener;
        private long transferred;
        private long totalBytes;

        public CountingOutputStream(OutputStream outputStream, ProgressListener listener, long totalBytes){
            super(outputStream);
            this.listener = listener;
            this.totalBytes = totalBytes;
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b,off,len);
            transferred += len;
            listener.progress(getCurrentProgress());

        }

        private float getCurrentProgress(){
            return ((float) transferred / totalBytes) * 100;
        }
    }
}
