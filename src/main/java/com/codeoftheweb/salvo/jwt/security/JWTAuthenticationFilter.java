package com.codeoftheweb.salvo.jwt.security;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.util.Constante;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private PlayerRepository playerRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            com.codeoftheweb.salvo.jwt.security.User credenciales = new ObjectMapper().readValue(request.getInputStream(),com.codeoftheweb.salvo.jwt.security.User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        credenciales.getEmail(), credenciales.getPassword(),AuthorityUtils.createAuthorityList("USER")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(Constante.ISSUER_INFO)
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Constante.TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Constante.SUPER_SECRET_KEY).compact();
        //Agrego al header "Authorization": "Bearer + token generado";
        response.addHeader("Access-Control-Expose-Headers", Constante.HEADER_AUTHORIZACION_KEY);
        response.addHeader(Constante.HEADER_AUTHORIZACION_KEY, Constante.TOKEN_BEARER_PREFIX + " " + token);

    }
}