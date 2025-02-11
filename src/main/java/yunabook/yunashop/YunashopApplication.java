package yunabook.yunashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;



@SpringBootApplication
public class YunashopApplication {

	public static void main(String[] args) {

		SpringApplication.run(YunashopApplication.class, args);
	}

	/**
	 * 역할
	 * 
	 * 1. 스프링 부트 애플리케이션의 진입점
	 * 2. 스프링 컨텍스트를 생성하고 관리
	 * 3. 애플리케이션 시작 시 필요한 설정 초기화
	 * 4. 애플리케이션 종료 시 필요한 정리 작업 수행
	 * 
	 * */ 
	@Bean
	Hibernate5JakartaModule hibernate5JakartaModule() {
		Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();

		// 엔티티를 조회할 때 지연 로딩 대상을 모두 조회하도록 설정 => 모든 엔티티의 쿼리가 조회되기 때문에 이 설정은 사용하면 안된다.
		// hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
		return hibernate5JakartaModule;
	}
}
