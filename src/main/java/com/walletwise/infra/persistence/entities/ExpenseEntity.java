package com.walletwise.infra.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "expense_type")
@Table(name = "t_expense", schema = "walletwise")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ExpenseEntity {

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private int dueDay;

    @Column(nullable = false)
    private String paymentFrequency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private boolean active;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public ExpenseEntity(UUID id, String description, String category, double amount, int dueDay, String paymentFrequency, UserEntity user) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.dueDay = dueDay;
        this.paymentFrequency = paymentFrequency;
        this.user = user;
    }
}