package pl.jkiraga.queuetask.model.entity;

import lombok.Getter;
import lombok.Setter;
import pl.jkiraga.queuetask.model.dict.RequestStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="resource_download_request")
public class ResourceDownloadRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "url")
    private String url;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
