package com.example.ProyectoIntegradorClinica.configure;

import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Rol;
import com.example.ProyectoIntegradorClinica.service.imp.auth.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    protected void configure(HttpSecurity http)throws Exception{
       /* http
                .csrf().disable()
                .formLogin().and()
                .authorizeRequests()
                .antMatchers("/turnos/**").hasAnyAuthority(Rol.USER.name(), Rol.ADMIN.name())
                .antMatchers("/odontologos/**", "/pacientes/**").hasAnyAuthority(Rol.ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");*/

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/turnos/**").hasAnyAuthority(Rol.USER.name(), Rol.ADMIN.name())
                .antMatchers("/odontologos/**", "/pacientes/**").hasAnyAuthority(Rol.ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin().permitAll();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);
        return provider;
    }

}
