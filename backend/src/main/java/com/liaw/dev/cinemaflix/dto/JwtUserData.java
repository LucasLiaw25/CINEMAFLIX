package com.liaw.dev.cinemaflix.dto;

import lombok.Builder;

@Builder
public record JwtUserData(Long id, String username, String email) {
}
