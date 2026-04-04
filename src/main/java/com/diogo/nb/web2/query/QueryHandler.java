package com.diogo.nb.web2.query;

public interface QueryHandler<Q, R> {
    R execute(Q query);
}
