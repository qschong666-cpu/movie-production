package cinema_backend.api_movie_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiMovieSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMovieSystemApplication.class, args);

		// jdbc:mysql://localhost:3306/cinema_db?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
	}

}
