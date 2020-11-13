package com.goodlist.core.jwt;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import com.goodlist.domain.enums.Role;
import com.goodlist.domain.models.User;

@NoArgsConstructor
public class JwtUserFactory {

    public static JwtUser build(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), createGrantedAuthorities(user.getRole()));
    }

    private static List<GrantedAuthority> createGrantedAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

}
