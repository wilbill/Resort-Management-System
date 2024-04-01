package org.billy.resortmanagementsystem.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import org.billy.resortmanagementsystem.services.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Data
@Embeddable
public class AuditData {

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            createdBy = ((CustomUserDetails) auth.getPrincipal()).getUsername();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            updatedBy = ((CustomUserDetails) auth.getPrincipal()).getUsername();
    }
}
