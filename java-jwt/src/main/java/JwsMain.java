import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwsMain {
    public static String key = "amF2YS1hcHBsaWNhdGlvbi1zZWN1cmUtc3R1ZHktand0LXNlY3JldGtleS1pcy1zaG91bGQtYmUtYmlnZ2VyLXRoYW4tNTEyYml0cw==";
    public static Long tokenValidityInMilliseconds = 100000L;

    public static void main(String args[]) {
        Key secretKey = Keys.hmacShaKeyFor(key.getBytes());
        Date date = new Date((new Date()).getTime() + tokenValidityInMilliseconds);

        String jwt = Jwts.builder()
                .setSubject("test")
                .signWith(secretKey, SignatureAlgorithm.HS512) // JWT 를 암호화 하기 위한 secret 과 알고리즘을 넣어준다.
                .setExpiration(date)
                .compact();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt);

        Header header = claimsJws.getHeader();
        Claims body = claimsJws.getBody();

        System.out.println("token : " + jwt);
        System.out.println("JWT Header : " + header);
        System.out.println("JWT Claims : " + body);
    }
}
