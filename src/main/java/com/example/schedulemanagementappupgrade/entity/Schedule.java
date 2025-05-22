package com.example.schedulemanagementappupgrade.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    public Schedule() {}

    public Schedule(User user, String userName, String title, String contents) {
        this.user = user;
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }

    public Schedule(Long id, User user, String userName, String title, String contents) {
        this.id = id;
        this.user = user;
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }

}
