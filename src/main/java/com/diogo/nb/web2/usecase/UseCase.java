package com.diogo.nb.web2.usecase;

public interface UseCase<C> {
    void execute(C command);
}
