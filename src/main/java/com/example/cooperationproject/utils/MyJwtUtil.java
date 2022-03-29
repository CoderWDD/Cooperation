package com.example.cooperationproject.utils;

import com.example.cooperationproject.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class MyJwtUtil implements Serializable {
    // 设置token有效期为一周
    private static final Long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60L;

    // 设置密钥明文
    private static String secretKey = "CoderWdd";

    // 设置加密算法
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 设置签发者
    private static String issuer = "CoderWdd";


    /**
     *
     * @param map token中存放的数据，Map格式
     * @return 返回生成的token
     */
    public static String generateToken(Map<String,Object> map,String username){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expMillis = nowMillis + JWT_TOKEN_VALIDITY;

        Date expDate = new Date(expMillis);

        SecretKey mySecretKey = decodeSecret();

        return Jwts
                .builder()
                .setClaims(map)
                .setSubject(username)
                .setIssuer(issuer)     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm,mySecretKey)
                .setExpiration(expDate)
                .compact();
    }

    /**
     *
     * @param token
     * @return 返回存放数据的Claims
     */
    public static Claims parseToken(String token){
        SecretKey mySecretKey = decodeSecret();
        return Jwts
                .parser()
                .setSigningKey(mySecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        Claims claims = parseToken(token);
        String username = claims.getSubject();
        return username;
    }

    /**
     * 从token中获取password
     * @param token
     * @return
     */
    public String getPasswordFromToken(String token){
        Claims claims = parseToken(token);
        String password = (String) claims.get("password");
        return password;
    }

    /**
     * 从token中获取用户id
     * @param token
     * @return
     */
    public int getUserIdFromToken(String token){
        Claims claims = parseToken(token);
        int userId = (int) claims.get("userId");
        return userId;
    }

    /**
     * 判断token是否已经过期
     * @param token
     * @return true : 过期  false : 没过期
     */
    public static boolean isTokenExpired(String token){
        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 判断token是否有效
     * @param token
     * @param user
     * @return
     */
    public static boolean isTokenValidate(String token, User user){
        Claims claims = MyJwtUtil.parseToken(token);
        String username = claims.getSubject();
        return username.equals(user.getUserName()) && !isTokenExpired(token);
    }

    /**
     * 生成加密后的密钥
     * @return
     */
    private static SecretKey decodeSecret(){
        byte[] encodedKey = Base64.getDecoder().decode(secretKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
