package com.diogo.nb.web2.security;

import com.diogo.nb.web2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .map(usuario -> new org.springframework.security.core.userdetails.User(
                        usuario.getUsername(),
                        usuario.getPassword(),
                        List.of(new SimpleGrantedAuthority(usuario.getPerfil().getAuthority()))))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
