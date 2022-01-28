package com.yyds.billshare.jwt.resource;

import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

    private static final long serialVersionUID = -5616176897013108345L;

//    {
//        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpbjI4bWludXRlcyIsImV4cCI6MTY0MTMyODYxMCwiaWF0IjoxNjQwNzIzODEwfQ.AFjuaW8RmtYt0i-8aNUraN-p9toJXR_ytpd8C0afVtMR-3nU7JUaMnZP277Ea4J5mYPBRFfCIOGOMoWe2nZUPg"
//    }
    private String email;
    private String password;

    public JwtTokenRequest() {
        super();
    }

    public JwtTokenRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
