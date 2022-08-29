package com.youland.markting.config.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.youland.commons.constant.CommonResponseEnum;
import com.youland.commons.model.ErrorResponse;
import com.youland.commons.utils.JsonUtil;
import com.youland.lib.uac.IJwtService;
import com.youland.lib.uac.model.user.UserProfile;
import com.youland.markting.config.WebSecurityConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    private final AntPathMatcher antPathMatcher;
    private final UrlPathHelper urlPathHelper;

    private static final String TOKEN_TYPE = "Bearer ";

    private UserProfile extractUserProfile(HttpServletRequest request)
            throws JsonProcessingException {
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        UserProfile userProfile = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith(TOKEN_TYPE)) {
            String jwtToken = requestTokenHeader.substring(TOKEN_TYPE.length());
            userProfile = jwtService.getProfile(jwtToken);
        }
        return userProfile;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {
        boolean needVerified = Arrays.stream(WebSecurityConfig.AUTH_LIST).anyMatch(s ->
                antPathMatcher.match(s, urlPathHelper.getLookupPathForRequest(request)));
        //Skip unverified urls
        if (!needVerified) {
            chain.doFilter(request, response);
            return;
        }

        UserProfile userProfile;
        try {
            userProfile = extractUserProfile(request);
            if (Objects.nonNull(userProfile)) {
                List<GrantedAuthority> grantedAuthorities = userProfile.getRoles().stream().map(userRole ->
                        new SimpleGrantedAuthority(userRole.getValue())).collect(Collectors.toList());

                var authToken = new UsernamePasswordAuthenticationToken(userProfile, null, grantedAuthorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            HttpStatus code = HttpStatus.UNAUTHORIZED;
            ErrorResponse errorResponse = ErrorResponse.getInstance(CommonResponseEnum.TOKEN_EXPIRED, code, request);
            response.setStatus(code.value());
            response.setContentType("text/html;charset=utf-8");
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.write(JsonUtil.stringify(errorResponse));
            }
        }
    }
}
