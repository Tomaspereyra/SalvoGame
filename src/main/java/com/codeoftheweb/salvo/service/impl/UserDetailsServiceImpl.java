package com.codeoftheweb.salvo.service.impl;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        System.out.println("inputname:"+email);
        Player player = playerRepository.findByEmailAddress(email);

        if (player != null) {
            return new User(player.getEmailAddress(), player.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("Unknown user:" + email);
        }
    }
}
