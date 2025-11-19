package com.challenge.wazejob.entities;

import com.challenge.wazejob.enums.Interest;
import com.challenge.wazejob.enums.Seniority;
import com.challenge.wazejob.enums.Skill;
import com.challenge.wazejob.validation.GithubUrl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID profileId;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @GithubUrl
    private String github;

    @Column(length = 1000)
    private String bio;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profile_skill",
            joinColumns = @JoinColumn(name = "profile_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "skill", length = 100)
    private Set<Skill> skillSet = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profile_interest",
            joinColumns = @JoinColumn(name = "profile_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "interest", length = 100)
    private Set<Interest> interests = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "seniority", length = 50)
    private Seniority seniority;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
