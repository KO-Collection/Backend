package com.example.ko.config;

import com.example.ko.sercurity.JwtUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
//    @Value("ra.jwt.expiration")
    private long JWT_EXPIRATION = 5000*24*60*60L;
//    Tao chuoi JWT tu userName
    public String generateToken(JwtUserDetails jwtUserDetails){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime()+JWT_EXPIRATION);
        return
                Jwts.builder()
                        .setSubject(jwtUserDetails.getUsername())
                                .setIssuedAt(now)
                        .setExpiration(dateExpired)
                        .signWith(SignatureAlgorithm.HS512,JWT_SECRET).compact();

    }
//    Lay thong tin user từ JWT
    public  String getUserFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public  boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException ex){
            log.error("JWT Token không hợp lệ");
        }catch (ExpiredJwtException ex){
            log.error("JWT Tokent đã hết hạn");
        }catch (UnsupportedJwtException ex){
            log.error("JWT Tokent không được hỗ trợ");
        }catch (IllegalArgumentException ex){
            log.error("JWT đang bị rỗng ");
        }
        return false;
    }

}
