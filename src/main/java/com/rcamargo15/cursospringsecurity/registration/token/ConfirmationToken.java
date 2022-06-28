package com.rcamargo15.cursospringsecurity.registration.token;

import com.rcamargo15.cursospringsecurity.profile.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(name = "confirmation_token_sequence", sequenceName = "confirmatio_token_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
    private Long id;
    @Column(nullable = false) //variavel nulabble nao pode ser nula
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "profile_id")
    private Profile profile;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Profile profile) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.profile = profile;
    }
}
