package com.beanfactorypostprocessorannotationcomposition.repository;

import com.beanfactorypostprocessorannotationcomposition.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChildRepository extends JpaRepository<Child, UUID> {
}
