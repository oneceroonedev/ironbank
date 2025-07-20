package com.ironbank.bankingsystem.security;

import com.ironbank.bankingsystem.model.users.ThirdParty;
import com.ironbank.bankingsystem.repository.ThirdPartyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ThirdPartyAuthFilter extends OncePerRequestFilter {

    private final ThirdPartyRepository thirdPartyRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Aplica el filtro solo a rutas específicas de third-party
        String path = request.getRequestURI();
        return !path.startsWith("/third-party");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String hashedKey = request.getHeader("hashed-key");

        if (hashedKey == null || hashedKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing hashed-key header");
            return;
        }

        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey).orElse(null);
        if (thirdParty == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid hashed-key");
            return;
        }

        // Autenticamos manualmente el ThirdParty
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(thirdParty, null,
                        Collections.singleton(() -> "ROLE_THIRD_PARTY"));

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}