package com.ztnh.publicdisk.http.log;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static okhttp3.internal.http.StatusLine.HTTP_CONTINUE;

/**
 * Author: lijian
 * Email : 16578381@qq.com
 * Date  : 2018-01-02 10:57
 * Description: http日志
 *
 */
public class HttpLogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");


    public HttpLogInterceptor() {
        this(ILogger.DEFAULT);
    }

    public HttpLogInterceptor(ILogger logger) {
        this.logger = logger;
    }

    private final ILogger logger;


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "[ " + request.method()+" ]"+ "--->" + request.url()  ;
        String proStr = protocol.toString();
        if (hasRequestBody) {
            proStr += " (" + requestBody.contentLength() + "-byte body)";
        }

        logger.log("╔════════════════════════════════════════ Request ══════════════════════════════════════");
        logger.log("║ "+requestStartMessage);
        logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
        logger.log("║ "+proStr);

        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                logger.log("║ "+"Content-Type: " + requestBody.contentType());
            }
            if (requestBody.contentLength() != -1) {
                logger.log("║ "+"Content-Length: " + requestBody.contentLength());
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                logger.log("║ "+name + ": " + headers.value(i));
            }
        }

        if (!hasRequestBody) {
            logger.log("║ "+ request.method());
            logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");
            logger.log(" ");
        } else if (bodyEncoded(request.headers())) {
            logger.log("║ "+ request.method() + " (encoded body omitted)");
            logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");
            logger.log(" ");
        } else {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
           String[] str =  JsonUtil.convert(buffer.readString(charset)).split("\n");
           for(String s :str){
               logger.log("║ "+s);
           }

          //  logger.log("║ "+"{"+JsonUtil.convert(buffer.readString(charset))+"}");
            logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
            logger.log("║ "+request.method() + " (" + requestBody.contentLength() + "-byte body)");
            logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");
            logger.log(" ");

        }

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        logger.log("╔════════════════════════════════════════ Response ═════════════════════════════════════");
        logger.log("║ [" + response.code() + "] "  + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + ')');

        Headers responseHeaders = response.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            logger.log("║ " +responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        if (!hasBody(response)) {
            logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
            logger.log("║ "+"END HTTP");
            logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");
            logger.log(" ");
        } else if (bodyEncoded(response.headers())) {
            logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
            logger.log("║ "+"END HTTP (encoded body omitted)");
            logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");
            logger.log(" ");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
                    logger.log("║ "+"Couldn't decode the response body; charset is likely malformed.");
                    logger.log("║ "+"END HTTP");
                    logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");

                    return response;
                }
            }

            if (contentLength != 0) {
                logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
                //logger.log( JsonUtil.convert(buffer.clone().readString(charset)));
                String[] str =  JsonUtil.convert(buffer.clone().readString(charset)).split("\n");
                for(String s :str){
                    logger.log("║ "+s);
                }
            }
            logger.log("╟───────────────────────────────────────────────────────────────────────────────────────");
            logger.log("║ "+"END HTTP (" + buffer.size() + "-byte body)");
            logger.log("╚══════════════════════════════════════════ End ════════════════════════════════════════");
            logger.log(" ");
        }

        return response;
    }

    /**
     * Returns true if the response must have a (possibly 0-length) body. See RFC 2616 section 4.3.
     */
    public boolean hasBody(Response response) {
        // HEAD requests never yield a body regardless of the response headers.
        if (response.request().method().equals("HEAD")) {
            return false;
        }

        int responseCode = response.code();
        if ((responseCode < HTTP_CONTINUE || responseCode >= 200)
                && responseCode != HTTP_NO_CONTENT
                && responseCode != HTTP_NOT_MODIFIED) {
            return true;
        }

        // If the Content-Length or Transfer-Encoding headers disagree with the
        // response code, the response is malformed. For best compatibility, we
        // honor the headers.
        if (contentLength(response) != -1
                || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return true;
        }

        return false;
    }

    public long contentLength(Response response) {
        return contentLength(response.headers());
    }

    public long contentLength(Headers headers) {
        return stringToLong(headers.get("Content-Length"));
    }

    private long stringToLong(String s) {
        if (s == null) return -1;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
