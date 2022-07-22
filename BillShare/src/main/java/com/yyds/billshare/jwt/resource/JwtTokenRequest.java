package com.yyds.billshare.jwt.resource;

import java.io.Serial;
import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5616176897013108345L;

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
