import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtTest {
    public static String secrete = "amF2YS1hcHBsaWNhdGlvbi1zZWN1cmUtc3R1ZHktand0LXNlY3JldGtleS1pcy1zaG91bGQtYmUtYmlnZ2VyLXRoYW4tNTEyYml0cw==";
    public static String AUTHENTICATION = "Auth";

    @Test
    public void create_token(){
        String token = Jwts.builder()
                .setSubject("test")
                .compact();

        Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(token);

        Claims claims = headerClaimsJwt.getBody();

        assertThat(claims.getSubject()).isEqualTo("test");
    }

    @Test
    public void create_token_jws(){
        Key key = Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject("test")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Header header = claimsJws.getHeader();
        Claims claims = claimsJws.getBody();

        assertThat(header.get("alg")).isEqualTo("HS512"); // Header에 서명 알고리즘이 들어가는 것을 확인할 수 있다.
        assertThat(claims.getSubject()).isEqualTo("test");
    }

    @Test
    public void create_token_jws_SignatureException() throws InterruptedException {
        Key key = Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));
        Key key2 = Keys.hmacShaKeyFor((secrete+"abcd").getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject("test")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        assertThrows(SignatureException.class, () -> {
            Jwts.parserBuilder()
                    .setSigningKey(key2)
                    .build()
                    .parseClaimsJws(token);
        });
    }

    @Test
    public void create_token_jws_UnsupportedJwtException(){
        Key key = Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject("test")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // JWS 를  parseClaimsJwt 메소드를 이용해 Parsing 할 경우 UnsupportedJwtException 예외가 발생한다.
        assertThrows(UnsupportedJwtException.class, () -> {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJwt(token);
        });
    }

    @Test
    public void create_token_jws_ExpiredJwtException() throws InterruptedException {
        Key key = Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));
        Long tokenValidityInMilliseconds = 100L;
        Date date = new Date(System.currentTimeMillis() + tokenValidityInMilliseconds);

        String token = Jwts.builder()
                .setSubject("test")
                .setExpiration(date)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // token Time Out을 일으키기 위한 Sleep
        Thread.sleep(1000L);

        assertThrows(ExpiredJwtException.class, () -> {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        });
    }

    @Test
    public void token_header(){
        Key key =  Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));
        Date date = new Date(System.currentTimeMillis() + 100000L);

        Map<String, Object> headers = new HashMap<>();
        long now = System.currentTimeMillis();
        headers.put("Date", now);
        headers.put("type", "JWT");
        headers.put("name", "test");

        String token = Jwts.builder()
                .setHeader(headers)
                .setExpiration(date)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Header jwtHeader = claimsJws.getHeader();
        Claims body = claimsJws.getBody();

        assertThat(jwtHeader.get("Date")).isEqualTo(now);
        assertThat(jwtHeader.get("type")).isEqualTo("JWT");
        assertThat(jwtHeader.get("name")).isEqualTo("test");
    }

    @Test
    public void token_header2(){
        Key key =  Keys.hmacShaKeyFor(secrete.getBytes(StandardCharsets.UTF_8));
        Date date = new Date(System.currentTimeMillis() + 100000L);

        Map<String, Object> headers = new HashMap<>();
        long now = System.currentTimeMillis();
        headers.put("Date", now);
        headers.put("type", "JWT");
        headers.put("name", "test");

        JwtBuilder jwtBuilder =  Jwts.builder()
                .setHeader(headers)
                .setExpiration(date)
                .signWith(key, SignatureAlgorithm.HS512);

        Map<String, Object> headers2 = new HashMap<>();
        headers2.put("type2", "JWT");
        headers2.put("alg", "HS256");
        headers2.put("regDate2", System.currentTimeMillis());

        jwtBuilder.setHeader(headers2); // 기존 해더 정보를 대체하게 된다.
        String token = jwtBuilder.compact();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Header jwtHeader = claimsJws.getHeader();
        Claims body = claimsJws.getBody();

        assertThat(jwtHeader.get("Date")).isEqualTo(now);
        assertThat(jwtHeader.get("type")).isEqualTo("JWT");
        assertThat(jwtHeader.get("name")).isEqualTo("test");
    }
}
