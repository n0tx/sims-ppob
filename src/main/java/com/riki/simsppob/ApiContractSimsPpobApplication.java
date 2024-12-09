package com.riki.simsppob;

import com.riki.simsppob.util.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiContractSimsPpobApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnv();
		SpringApplication.run(ApiContractSimsPpobApplication.class, args);
	}

}
