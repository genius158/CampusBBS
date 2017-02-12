package com.yan.campusbbs.repository.entity;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponse extends ResponseBody {
    private ResponseBody orginalResponseBody;
    private List<DownloadProListener> progressListeners;
    private BufferedSource mBufferSource;

    public ProgressResponse(ResponseBody orginalResponseBody) {
        this.orginalResponseBody = orginalResponseBody;
    }

    public ProgressResponse(ResponseBody orginalResponseBody, List<DownloadProListener> mListen) {
        this.orginalResponseBody = orginalResponseBody;
        this.progressListeners = mListen;
    }

    @Override
    public MediaType contentType() {
        return orginalResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return orginalResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferSource == null) {
            mBufferSource = Okio.buffer(getSource(orginalResponseBody.source()));
        }
        return mBufferSource;
    }

    private Source getSource(Source source) {
        ForwardingSource forwardingSource = new ForwardingSource(source) {
            private long currentByte;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long read = super.read(sink, byteCount);
                if (read == -1) {
                    for (DownloadProListener proListener : progressListeners) {
                        proListener.onProgress(contentLength(), contentLength());
                    }
                } else {
                    currentByte += read;
                    for (DownloadProListener proListener : progressListeners) {
                        proListener.onProgress(currentByte, contentLength());
                    }
                }
                return read;
            }
        };
        return forwardingSource;
    }

}
