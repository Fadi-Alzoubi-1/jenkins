package com.fa.store.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.fa.store.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String secretKey;

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return Jwts.builder().claims().add(claims).subject(user.getUsername()).issuer("Bearer")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)).and().signWith(generateKey())
				.compact();

	}

	private SecretKey generateKey() {
		byte[] decode = Decoders.BASE64.decode(getSeceretKey());
		return Keys.hmacShaKeyFor(decode);
	}

	public String getSeceretKey() {
		secretKey = "6d549b5ddee7d8cd3d0fb46b08e61ae036de62c64282f4fa87959e9f2c590d61f22cd6698c28a2db30d9dd000936c36d18df12b3b9ba3dffe8b149809cdd5de1";
		return secretKey;
	}

	public String extractUserName(String token) {

		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {

		Claims claims = extractClaims(token);

		return claimResolver.apply(claims);
	}

	private Claims extractClaims(String token) {
		return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();

	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {

		return extractClaims(token, Claims::getExpiration);
	}

}
