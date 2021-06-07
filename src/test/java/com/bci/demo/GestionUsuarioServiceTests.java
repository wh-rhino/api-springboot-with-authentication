package com.bci.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class GestionUsuarioServiceTests {

	@Value("${jwt.secret}")
	private String secret;

	@Test
	void testNewDate() {
		System.out.println(new Date(System.currentTimeMillis() + 200000));
	}

	@Test
	void testLocalTime() {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		LocalDateTime localDateTime = timestamp.toLocalDateTime();
		System.out.println(new java.sql.Date(localDateTime.plusMinutes(1).getMinute()));
	}

	@Test
	void testVerifyToken() {
		// create
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		final Algorithm algorithm = Algorithm.HMAC256(secret);

		final Date date = new Date();
		final String token = JWT.create()
				.withIssuer("auth0")
				.withSubject("test")
				.withIssuedAt(date)
				.withExpiresAt(new Date(System.currentTimeMillis() + 600000))
				.withClaim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		//verify
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("auth0")
				.withSubject("test")
				.build();

		DecodedJWT jwt = verifier.verify(token);

		// Algorithm hmac256 = Algorithm.HMAC256(secret);
		// hmac256.verify(JWT.decode(token.concat(" ")));

		System.out.println(jwt.getClaims());
	}

	@Test
	void testClaim() {
		// create
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		final Algorithm algorithm = Algorithm.HMAC256(secret);

		final Date date = new Date();
		final String token = JWT.create()
				.withIssuer("auth0")
				.withSubject("test")
				.withIssuedAt(date)
				.withExpiresAt(new Date(System.currentTimeMillis() + 600000))
				.withClaim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		//verify
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("auth0")
				.withSubject("test")
				.build();

		DecodedJWT jwt = verifier.verify(token);

		final Claim authorities = jwt.getClaims().get("authorities");

		System.out.println(authorities);
		System.out.println(jwt.getClaims());
		System.out.println(jwt.getClaims().values().stream().collect(Collectors.toList()));
		List<String> key = new ArrayList(jwt.getClaims().keySet());
		System.out.println(key);
		List<String> values = new ArrayList(jwt.getClaims().values());
		System.out.println(values);

		//grant.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()) : ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(null)

		System.out.println(authorities.asString());
		final List<String> strings = authorities.asList(String.class);
		System.out.println(strings.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	}

}
