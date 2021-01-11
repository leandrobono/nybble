package com.nybble.nybble.security;

import com.nybble.nybble.model.JwtUser;
import com.nybble.nybble.services.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@WebFilter(urlPatterns = { "/*" })
public class JwtFilter implements Filter, HandlerInterceptor
{

    @Autowired
    private JwtService jwtTokenService;

    @Value("${jwt.auth.header}")
    String authHeader;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String authHeaderVal = httpRequest.getHeader(authHeader);

        if (!httpRequest.getMethod().equals("OPTIONS")) {
            String path = httpRequest.getRequestURI();
            if ("/login".equals(path) || "/usuario".equals(path)) {
                chain.doFilter(request, response);
                return;
            }

            if (null == authHeaderVal) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            try {
                JwtUser jwtUser = jwtTokenService.getUser(authHeaderVal);

                httpRequest.setAttribute("jwtUser", jwtUser);
                httpResponse.addHeader("Access-Control-Expose-Headers", "x-auth-token");
                httpResponse.addHeader("Access-Control-Allow-Headers", "x-auth-token");
                httpResponse.setHeader("x-auth-token", jwtTokenService.getToken(jwtUser));
            } catch (JwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return;
            }

            chain.doFilter(httpRequest, httpResponse);
        } else {
            chain.doFilter(request, response);
            return;
        }
    }
}