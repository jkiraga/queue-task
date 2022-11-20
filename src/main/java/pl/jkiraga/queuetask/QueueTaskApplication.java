package pl.jkiraga.queuetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.jkiraga.queuetask.configuration.QueueTaskConfigurationProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({QueueTaskConfigurationProperties.class})
public class QueueTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(QueueTaskApplication.class, args);
	}
}
