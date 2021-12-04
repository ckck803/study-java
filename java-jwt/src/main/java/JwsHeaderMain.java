import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwsHeaderMain {
    public static String key = "amF2YS1hcHBsaWNhdGlvbi1zZWN1cmUtc3R1ZHktand0LXNlY3JldGtleS1pcy1zaG91bGQtYmUtYmlnZ2VyLXRoYW4tNTEyYml0cw==";
    public static String AUTHENTICATION = "Auth";
    public static Long tokenValidityInMilliseconds = 100000L;

    public static void main(String args[]) throws ParseException {
        Key secretKey = Keys.hmacShaKeyFor(key.getBytes());
        Date date = new Date(System.currentTimeMillis() + tokenValidityInMilliseconds);

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());


        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject("test")
                .setHeader(header)
                .claim(AUTHENTICATION, "jwt")
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setExpiration(date);

        Map<String, Object> header2 = new HashMap<>();
        header2.put("type2", "JWT");
        header2.put("alg", "HS512");
        header2.put("regDate2", System.currentTimeMillis());

//        jwtBuilder.setHeader(header2);
        jwtBuilder.setHeaderParams(header2); // 해더 정보를 추가하거나 값은 값이 있는 경우 대체하게 된다.

        String jwt = jwtBuilder.compact();

        Claims body = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        JwsHeader jwsHeader = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getHeader();

        System.out.println("token : " + jwt);
        System.out.println("JSON : " + body);
        System.out.println("expiration time : " + body.get("exp", Date.class));
        System.out.println("expiration time : " + body.getExpiration());
        System.out.println("jwsHeader = " + jwsHeader);
        System.out.println("sub : " + body.get("sub"));
        System.out.println("Auth : " + body.get("Auth"));
    }
}
