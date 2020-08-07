package co.com.udem.inmobiliaria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import co.com.udem.inmobiliaria.security.jwt.JwtSecurityConfigurer;
import co.com.udem.inmobiliaria.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //Definir qué request voy a autorizar
        // @formatter:off
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/auth/signin").permitAll() //Permitir login
                //.antMatchers(HttpMethod.GET, "/clubes/**").permitAll()
                //.antMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
                //.antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
                //.antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/addUser**").permitAll()
                //.antMatchers(HttpMethod.POST, "/clubes/adicionarClub**").permitAll()
                .anyRequest().authenticated()
            .and()
            .apply(new JwtSecurityConfigurer(jwtTokenProvider));
        // @formatter:on

 

        //http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
        
    }

 

    
}