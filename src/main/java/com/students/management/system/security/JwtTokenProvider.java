package com.students.management.system.security;


import com.students.management.system.constants.Literals;
import com.students.management.system.entity.Users;
import com.students.management.system.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.header.name}")
    private String header;
    @Value("${jwt.auth.token.expire.milliseconds}")
    private long authTokenValidityInMilliseconds;

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UsersRepository userRepository;

    @Autowired
    public JwtTokenProvider(UserDetailsServiceImpl userDetailsServiceImpl, UsersRepository userRepository) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAuthToken(Users user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put(Literals.USER_ID, user.getId());
		claims.put("userName", user.getFirstName() + " " + user.getLastName());
        Date now = new Date();
        Date validity = new Date(now.getTime() + authTokenValidityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(Literals.USER_ID).toString();
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(header);
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return (!claims.getBody().getExpiration().before(new Date()) && validateUser(claims.getBody().get(Literals.USER_ID).toString()));
    }

    public boolean validateUser(String userId) {
        Users user = userRepository.findByUserId(Long.valueOf(userId));
        return user.isActive() && !user.isDeleted();
    }

    public Jws<Claims> resolveClaims(String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
    }

}
