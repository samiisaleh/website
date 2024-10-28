package com.samisaleh.website.repository;

import com.samisaleh.website.model.ProjectEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntry, Long> {
    List<ProjectEntry> findAllByOrderByCreatedAtDesc();
    List<ProjectEntry> findByCreatedBy_UsernameOrderByCreatedAtDesc(String username);
}