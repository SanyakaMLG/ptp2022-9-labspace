package com.example.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "package"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Package {
    @Id
    @SequenceGenerator(
            name = "package_sequence",
            sequenceName = "package_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "package_sequence"
    )
    @Column(
            name = "package_id"
    )
    private Long packageId;

    @Column(
            name = "source"
    )
    private String source;

    @Column(
            name = "send_date"
    )
    private Date sendDate = new Date(System.currentTimeMillis());

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "student_id"
    )
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "lab_info_id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LabInfo labInfo;
}
