package com.sava.playful.pursuits.hub.playfulpursuitshub;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlayfulPursuitsHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayfulPursuitsHubApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
