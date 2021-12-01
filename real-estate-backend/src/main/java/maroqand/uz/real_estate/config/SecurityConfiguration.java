package maroqand.uz.real_estate.config;

import maroqand.uz.real_estate.security.JwtConfigurer;
import maroqand.uz.real_estate.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(@Lazy UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/ads").hasRole("USER")
                .antMatchers("/api/ads/**").permitAll()
                .antMatchers("/api/preview/**").permitAll()
//                .antMatchers("/api/upload").permitAll()
//                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/ads").permitAll()
                .antMatchers("/api/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
