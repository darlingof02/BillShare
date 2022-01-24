package com.yyds.billshare.jwt.resource;

import java.io.Serial;
import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5616176897013108345L;

//    {
//        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpbjI4bWludXRlcyIsImV4cCI6MTY0MTMyODYxMCwiaWF0IjoxNjQwNzIzODEwfQ.AFjuaW8RmtYt0i-8aNUraN-p9toJXR_ytpd8C0afVtMR-3nU7JUaMnZP277Ea4J5mYPBRFfCIOGOMoWe2nZUPg"
//    }
    private String username;
    private String password;

    public JwtTokenRequest() {
        super();
    }

    public JwtTokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
