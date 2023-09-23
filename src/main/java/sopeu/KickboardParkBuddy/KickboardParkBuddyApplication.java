package sopeu.KickboardParkBuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;
@SpringBootApplication
@EnableJpaAuditing
public class KickboardParkBuddyApplication {

	public static void main(String[] args) {
//		int keyLength = 64; // 예: 32바이트 길이의 시크릿 키
//
//		// 시큐어 랜덤 생성
//		SecureRandom secureRandom = new SecureRandom();
//		byte[] secretKeyBytes = new byte[keyLength];
//		secureRandom.nextBytes(secretKeyBytes);
//
//		// 생성한 바이트 배열을 Base64 인코딩하여 시크릿 키로 사용
//		String secretKey = Base64.getEncoder().encodeToString(secretKeyBytes);
//
//		System.out.println("Generated Secret Key: " + secretKey);
		SpringApplication.run(KickboardParkBuddyApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
