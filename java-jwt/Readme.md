# Java - JWT(Json Web Token) 알아보기

## JWT Header 조작하기

| 메소드          | 설명                                                                      |
| --------------- | ------------------------------------------------------------------------- |
| setHeader       | 기존에 설정된 JWT의 Header 정보를 설정하고자 하는 Header 정보로 대체한다. |
| setHeaderParams | JWT의 Header 정보들을 추가하고자 할때 사용하는 메서드                     |
| setHeaderParam  | JWT의 Header 정보를 추가하고자 할때 사용하는 메서드                       |

### setHeader

```java
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


        String jwt = Jwts.builder()
                .setSubject("test")
                .setHeader(header)
                .claim(AUTHENTICATION, "jwt")
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setExpiration(date)
                .compact();

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
        System.out.println("jwsHeader = " + jwsHeader);
        System.out.println(body.get("sub"));
        System.out.println(body.get("Auth"));
    }
}
```

```java
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
        header2.put("alg", "HS256");
        header2.put("regDate2", System.currentTimeMillis());

        jwtBuilder.setHeader(header2); // 기존 해더 정보를 대체하게 된다.

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
        System.out.println("jwsHeader = " + jwsHeader);
        System.out.println("sub : " + body.get("sub"));
        System.out.println("Auth : " + body.get("Auth"));
    }
}
```

```
token : eyJ0eXBlMiI6IkpXVCIsInJlZ0RhdGUyIjoxNjM4NTg1NzEyMDAyLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiQXV0aCI6Imp3dCIsImV4cCI6MTYzODU4NTgxMX0.RXz5-UlWgYankj6PYC-Lc5la3M__MlffPZbN48O9LW9eH9ZmGs2-S-f7H1GkEm3odhLYBTc7djhQbixsSsNZ_Q
JSON : {sub=test, Auth=jwt, exp=1638585811}
expiration time : Sat Dec 04 11:43:31 KST 2021
jwsHeader = {type2=JWT, regDate2=1638585712002, alg=HS512}
sub : test
Auth : jwt
```

### setHeaderParams

```java
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
        System.out.println("jwsHeader = " + jwsHeader);
        System.out.println("sub : " + body.get("sub"));
        System.out.println("Auth : " + body.get("Auth"));
    }
}
```

```
token : eyJyZWdEYXRlIjoxNjM4NTg1ODEyNDk2LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsInR5cGUyIjoiSldUIiwicmVnRGF0ZTIiOjE2Mzg1ODU4MTI1MDd9.eyJzdWIiOiJ0ZXN0IiwiQXV0aCI6Imp3dCIsImV4cCI6MTYzODU4NTkxMn0.LzGPOldLXKsnF4fO96xZKGgd1hArWsXylDQnmckkcenPxb0dBi5lMwyErvNt37EiLcyWXe2akhKfu8rwdK-AKw
JSON : {sub=test, Auth=jwt, exp=1638585912}
expiration time : Sat Dec 04 11:45:12 KST 2021
jwsHeader = {regDate=1638585812496, typ=JWT, alg=HS512, type2=JWT, regDate2=1638585812507}
sub : test
Auth : jwt
```

## JWT Claim 설정하기

| 메소드    | 설명                                      |
| --------- | ----------------------------------------- |
| setClaims | JWT의 Claim 데이터를 추가하기 위한 메소드 |
| addClaims | JWT의 Claim 데이터를 추가하기 위한 메소드 |




| 메소드              | 설명                                       |
| ------------------- | ------------------------------------------ |
| setSubject          |
| setExpiration       | 토큰 만료 시간 정보를 저장하기 위한 메소드 |
| setIssuer           |
| setAudience         |
| setNotBefore        |
| setIssuedAt         |
| setId               |
| claim               |
| signWith            |
| base64UrlEncodeWith |


| 메소드                      | 설명 |
| --------------------------- | ---- |
| requireId                   |      |
| requireSubject              |
| requireAudience             |
| requireIssuer               |
| requireIssuedAt             |
| requireExpiration           |
| requireNotBefore            |
| require                     |
| setClock                    |
| setAllowedClockSkewSeconds  |
| setSigningKey               |
| setSigningKey               |
| setSigningKeyResolver       |
| setCompressionCodecResolver |
| base64UrlDecodeWith         |
| deserializeJsonWith         |



## JAVA - JWT

```java
public class JwtMain {

    public static void main(String args[]) {

        String jwt = Jwts.builder()
                .setSubject("test")
                .compact();

        Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(jwt);
        
        // Token 정보를 가져온다.
        System.out.println("token : " + jwt);
        // JWT Header 정보를 가져온다.
        System.out.println("JWT Header : " + headerClaimsJwt.getHeader());
        // JWT Body(Claims) 정보를 가져온다.
        System.out.println("JWT Claims : " + headerClaimsJwt.getBody());
    }
}
```

```
token : eyJhbGciOiJub25lIn0.eyJzdWIiOiJ0ZXN0In0.
JWT Header : {alg=none}
JWT Claims : {sub=test}
```