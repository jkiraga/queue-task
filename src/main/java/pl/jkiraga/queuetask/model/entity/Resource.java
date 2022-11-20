package pl.jkiraga.queuetask.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ResourceDownloadRequest resourceDownloadRequest;

    @Column(name = "content")
    private String content;
}
