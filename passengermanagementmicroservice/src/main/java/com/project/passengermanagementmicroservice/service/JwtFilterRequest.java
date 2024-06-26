package com.project.passengermanagementmicroservice.service;


import com.project.passengermanagementmicroservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PassengerService passengerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader=request.getHeader("Authorization");
        String email=null;
       // String pwd=null;
        String jwtToken=null;

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer "))
        {
            jwtToken=authorizationHeader.substring(7);
            email=jwtUtils.extractUsername(jwtToken);
        }
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
          UserDetails currentDetails = passengerService.loadUserByUsername(email);
          Boolean tokenValidated=jwtUtils.validateToken(jwtToken,currentDetails);

          if(tokenValidated)
          {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(currentDetails,null,currentDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          }

        }
        filterChain.doFilter(request,response);
    }
}
