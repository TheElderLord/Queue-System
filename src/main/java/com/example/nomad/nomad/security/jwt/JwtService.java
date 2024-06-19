package com.example.nomad.nomad.security.jwt;

import com.example.nomad.nomad.dto.operatorAuth.OperatorAuthDto;
import com.example.nomad.nomad.model.Operator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
        private final String SECRET_KEY = "759974790a29cd2b29623522b833cdb9eb510d9cb5516b4981d585a867679db7";


        public boolean isValid(String token, UserDetails operatorAuthDto){
            String username = extractUsername(token);
            return  (username.equals(operatorAuthDto.getUsername())) && !isTokenExpired(token);
        }

    private boolean isTokenExpired(String token) {
            return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
            return extractClaim(token,Claims::getExpiration);
    }

    public String extractUsername(String token){
            return extractClaim(token,Claims::getSubject);
        }
        public <T> T extractClaim(String token, Function<Claims,T> resolver){
            Claims claims = extractAllClaims(token);
            return  resolver.apply(claims);
        }
        private Claims extractAllClaims(String token){
            return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        }
        public String generateToken(Operator operator){
            String token = Jwts.builder().subject(operator.getLogin())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                    .signWith(getSigningKey())
                    .compact();
            return token;
        }
        private SecretKey getSigningKey(){
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
        }
}
