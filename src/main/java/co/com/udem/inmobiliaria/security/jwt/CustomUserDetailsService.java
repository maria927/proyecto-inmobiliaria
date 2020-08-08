package co.com.udem.inmobiliaria.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import co.com.udem.inmobiliaria.repositories.RegistroRepository;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    private RegistroRepository users;

    public CustomUserDetailsService(RegistroRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.users.findByNumeroIdentificacion(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}
