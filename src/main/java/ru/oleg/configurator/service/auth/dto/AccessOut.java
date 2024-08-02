package ru.oleg.configurator.service.auth.dto;


import java.util.Collection;

public record AccessOut(String type,
                        long id,
                        String name,
                        Collection<String> authorities,
                        String accessToken,
                        long expires) {
    private static final String TYPE = "Bearer";

    public AccessOut(final long id,
                     final String name,
                     final Collection<String> authorities,
                     final String accessToken,
                     final long expires
                        ) {
        this(TYPE,
                id,
                name,
                authorities,
                accessToken,
                expires
                );
    }

}
