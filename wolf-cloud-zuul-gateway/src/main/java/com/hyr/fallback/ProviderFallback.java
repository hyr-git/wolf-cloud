package com.hyr.fallback;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;


/**
 * zuul是一个代理服务，但如果被代理的服务突然断了，这个时候zuul上面会有出错信息.
 * 现在服务的调用方已经做了处理，不会出现这样的错误信息，但一般来说，对于zuul本身代理方，也应该进行zuul的降级处理
 * @author m1832
 *
 */
@Component
public class ProviderFallback implements   FallbackProvider  {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders() ;
                headers.set("Content-Type", "text/html; charset=UTF-8");
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                // 响应体
                return new ByteArrayInputStream("产品微服务不可用，请稍后再试。".getBytes());
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.BAD_REQUEST.getReasonPhrase();
            }

            @Override
            public void close() {

            }
        };
    }
}