package com.beanfactorypostprocessorannotationcomposition.repository;

import com.beanfactorypostprocessorannotationcomposition.model.ViewChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViewChildRepository extends JpaRepository<ViewChild, UUID> {
}
