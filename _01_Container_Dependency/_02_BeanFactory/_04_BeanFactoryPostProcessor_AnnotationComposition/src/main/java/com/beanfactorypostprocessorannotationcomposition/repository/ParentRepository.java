package com.beanfactorypostprocessorannotationcomposition.repository;

import com.beanfactorypostprocessorannotationcomposition.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParentRepository extends JpaRepository<Parent, UUID> {
}
