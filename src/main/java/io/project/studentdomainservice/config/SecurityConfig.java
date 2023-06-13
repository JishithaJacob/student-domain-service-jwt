package io.project.studentdomainservice.config;


import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /*@Autowired
    JpaStudentDetailsService jpaStudentDetailsService;*/

    @Value("${jwt.key}")
    private String jwtKey;

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("jishi")
                        .password("{noop}password")
                        .authorities("READ","ROLE_USER")
                        .build());
    }
    @Bean
    /*WebSecurityConfigureAdapter got deprecated therefore using SecurityFilterChain*/
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .csrf(AbstractHttpConfigurer::disable) // disable csrf
                   .authorizeHttpRequests(auth-> auth
                                   .requestMatchers("/api/auth/token").hasRole("ROLE_USER")
                                   .anyRequest().hasAnyAuthority("SCOPE_READ")// anyone hitting url needs to have the authority read
                   )
                   .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) //set up OAuth2 resource server
                   .httpBasic(withDefaults())
                   .build();

    }


    @Bean
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] bytes = jwtKey.getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length,"RSA");
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS512).build();
    }


}
