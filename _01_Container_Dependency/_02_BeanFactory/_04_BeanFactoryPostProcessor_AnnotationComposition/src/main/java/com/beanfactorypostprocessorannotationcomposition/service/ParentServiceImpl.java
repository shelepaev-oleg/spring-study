package com.beanfactorypostprocessorannotationcomposition.service;

import com.beanfactorypostprocessorannotationcomposition.model.Parent;
import com.beanfactorypostprocessorannotationcomposition.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    @Transactional
    public Parent save(final Parent parent) {
        return parentRepository.save(parent);
    }
}
