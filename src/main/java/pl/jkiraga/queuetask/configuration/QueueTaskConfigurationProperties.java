package pl.jkiraga.queuetask.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "settings")
@Getter
@Setter
@Validated
public class QueueTaskConfigurationProperties {

    @NotBlank
    private String downloadIntervalCron;

    @Min(value = 1)
    @Max(value = 100)
    @NotNull
    private Integer queueMaxCapacity;
}
