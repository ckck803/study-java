import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtMain {

    public static void main(String args[]) {

        String jwt = Jwts.builder()
                .setSubject("test")
                .compact();

        Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(jwt);

        System.out.println("token : " + jwt);
        System.out.println("JWT Header : " + headerClaimsJwt.getHeader());
        System.out.println("JWT Claims : " + headerClaimsJwt.getBody());
    }
}
