package com.example.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(
        name = "student"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(
                name = "student_id"
        )
        private Long id;

        @Column(
                name = "email",
                unique = true
        )
        @Email
        private String email;

        @Column(
                name = "first_name"
        )
        private String firstName;

        @Column(
                name = "last_name"
        )
        private String lastName;

        @Column(
                name = "password"
        )
        private String password;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "groupp_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Groupp groupp;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "role_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Role role;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Groupp getGroupp() {
                return groupp;
        }

        public void setGroupp(Groupp groupp) {
                this.groupp = groupp;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }
}
