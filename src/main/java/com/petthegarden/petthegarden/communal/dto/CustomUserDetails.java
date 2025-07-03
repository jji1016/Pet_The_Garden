package com.petthegarden.petthegarden.communal.dto;

import com.petthegarden.petthegarden.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetails, OAuth2User {
    private final Member loggedMember;
    private Map<String, Object> oAuth2UserInfo;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("loggedMember.getRole().name()==={}",loggedMember.getRole().name());
        return List.of(new SimpleGrantedAuthority(loggedMember.getRole().name()));
    }

    @Override
    public String getPassword() {
        return loggedMember.getUserPW();
    }

    @Override
    public String getUsername() {
        return loggedMember.getUserID();
    }


    public String getUserrealname(){return loggedMember.getUserName(); }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfo;
    }
    @Override
    public String getName() {
        return oAuth2UserInfo.get("name").toString();
    }
}
