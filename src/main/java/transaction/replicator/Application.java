package transaction.replicator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run();
    }

    public void run(String... args) throws Exception {
        // TO-DO - Bootstrap Events
    }
}
