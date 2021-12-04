import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JwsTimeMain {
    public static String key = "amF2YS1hcHBsaWNhdGlvbi1zZWN1cmUtc3R1ZHktand0LXNlY3JldGtleS1pcy1zaG91bGQtYmUtYmlnZ2VyLXRoYW4tNTEyYml0cw==";
    public static String AUTHENTICATION = "Auth";
    public static Long tokenValidityInMilliseconds = 100000L;

    public static void main(String args[]) throws ParseException {
        Key secretKey = Keys.hmacShaKeyFor(key.getBytes());
        Date date = new Date(System.currentTimeMillis()+ tokenValidityInMilliseconds);

        String jwt = Jwts.builder()
                .setSubject("test")
                .claim(AUTHENTICATION, "jwt")
                .signWith(secretKey, SignatureAlgorithm.HS512)
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
        System.out.println("expiration time : " + body.get("exp", Date.class));
        System.out.println("expiration time : " + body.getExpiration());
        System.out.println("sub : " + body.get("sub"));
        System.out.println("Auth : " + body.get("Auth"));
    }
}
