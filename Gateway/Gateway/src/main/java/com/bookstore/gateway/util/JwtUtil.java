package com.bookstore.gateway.util;

import com.bookstore.gateway.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtUtil {
    public final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean validateToken(final String token) {
        try{
            final Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
           return false ;
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
