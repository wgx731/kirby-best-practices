package com.github.wgx731.kirby.handler;

import org.springframework.http.server.PathContainer;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import java.net.URI;
import java.util.Locale;

/**
 * Request Predicate Which Ignores Case
 */
public class CaseInsensitiveRequestPredicate implements RequestPredicate {

    private final RequestPredicate target;

    public CaseInsensitiveRequestPredicate(RequestPredicate target) {
        this.target = target;
    }

    @Override
    public boolean test(ServerRequest request) {
        return this.target.test(new LowerCaseUriServerRequestWrapper(request));
    }

    @Override
    public String toString() {
        return this.target.toString();
    }

    /**
     * Inner Server Request Wrapper To Change URL To Lower Case
     */
    public static class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper {

        LowerCaseUriServerRequestWrapper(ServerRequest delegate) {
            super(delegate);
        }

        @Override
        public URI uri() {
            return URI.create(super.uri().toString().toLowerCase(Locale.US));
        }

        @Override
        public String path() {
            return uri().getRawPath();
        }

        @Override
        public PathContainer pathContainer() {
            return PathContainer.parsePath(path());
        }

    }

}

