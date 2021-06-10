package com.waw_eve.seat.mumble.model;

import lombok.Data;

@Data
public class Request {
    /**
     * Username who need regist to mumble
     */
    private String name;

    /**
     * User's email address
     */
    private String email;
}
