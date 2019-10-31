package com.codeoftheweb.salvo.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

public abstract class GlobalAuthenticationConfigurerAdapter {

    public abstract void init(AuthenticationManagerBuilder auth)throws Exception;


}
