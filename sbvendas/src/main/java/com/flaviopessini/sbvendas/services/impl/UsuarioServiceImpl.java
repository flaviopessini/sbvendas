package com.flaviopessini.sbvendas.services.impl;

import com.flaviopessini.sbvendas.domain.entities.Usuario;
import com.flaviopessini.sbvendas.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = usuarioRepository
                .findByLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário inválido")
                );

        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(user.getLogin())
                .password(user.getSenha()) // já está criptografada no banco de dados.
                .roles(roles)
                .build();
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        final var encryptedPassword = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encryptedPassword);
        return this.usuarioRepository.save(usuario);
    }
}
