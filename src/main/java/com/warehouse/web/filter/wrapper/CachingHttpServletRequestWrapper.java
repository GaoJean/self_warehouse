package com.warehouse.web.filter.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

/**
 * @Description:
 * @Author: gaojian
 * @Date: 2021/11/25 21:30
 */
public class CachingHttpServletRequestWrapper extends ContentCachingRequestWrapper {
    private final byte[] body;

    public CachingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachingHttpServletRequestWrapper.CachingServletInputStream(this.body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    class CachingServletInputStream extends ServletInputStream {
        private final byte[] body;
        private int lastIndexRetrieved = -1;
        private ReadListener readListener = null;

        CachingServletInputStream(byte[] body) throws IOException {
            this.body = body;
        }

        @Override
        public boolean isFinished() {
            return this.lastIndexRetrieved == this.body.length - 1;
        }

        @Override
        public boolean isReady() {
            return this.isFinished();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            this.readListener = readListener;
            if (!this.isFinished()) {
                try {
                    readListener.onDataAvailable();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            } else {
                try {
                    readListener.onAllDataRead();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            }

        }

        @Override
        public int read() throws IOException {
            if (!this.isFinished()) {
                int ix = this.body[this.lastIndexRetrieved + 1];
                ++this.lastIndexRetrieved;
                if (this.isFinished() && this.readListener != null) {
                    try {
                        this.readListener.onAllDataRead();
                    } catch (IOException e) {
                        this.readListener.onError(e);
                        throw e;
                    }
                }

                return ix;
            } else {
                return -1;
            }
        }
    }
}
