package com.finance.backend.security;

import com.finance.backend.model.User;
import com.finance.backend.repository.UserRepository;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends GenericFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            try {
                String token = header.substring(7);

                //  Extract email from JWT
                String email = jwtUtil.extractEmail(token);

                //  Fetch user from DB
                User user = userRepository.findAll().stream()
                        .filter(u -> u.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);

                if (user != null && user.isActive()) {

                    //  Convert role to Spring authority
                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                    );

                    //  Create auth token with role
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                //  Invalid token → ignore (request will be unauthorized)
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}