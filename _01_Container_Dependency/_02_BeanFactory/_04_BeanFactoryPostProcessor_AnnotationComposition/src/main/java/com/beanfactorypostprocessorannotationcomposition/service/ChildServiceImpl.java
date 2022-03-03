package com.beanfactorypostprocessorannotationcomposition.service;

import com.beanfactorypostprocessorannotationcomposition.model.Child;
import com.beanfactorypostprocessorannotationcomposition.model.Parent;
import com.beanfactorypostprocessorannotationcomposition.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildRepository childRepository;

    /**
     * Данный метод помечен @Transactional и в то же время в методе используется вызов save.
     * Это допустимо, т.к. ранее сущность не была получена.
     */
    @Override
    @Transactional
    public Child create(final Parent parent, final Child child) {
        parent.addChild(child);
        return childRepository.save(child);
    }

    /**
     * Данный метод помечен @Transactional, но вызова save нет.
     * Т.к. в этом методе сущность выгружается при помощи findById.
     */
    @Override
    @Transactional
    public Child updateOk(final Child child) {
        Child currentChild = childRepository.findById(child.getId())
                .orElseThrow();
        currentChild.setName(child.getName());

        // Неправильно, т.к. вызывает лишний раз
        // org.hibernate.event.internal.DefaultMergeEventListener.entityIsPersistent
//        return childRepository.save(currentChild);

        // Правильно, т.к. уже есть @Transactional
        return currentChild;
    }

    @Override
    @Transactional
    public Child updateWrong(final Child child) {
        Child currentChild = childRepository.findById(child.getId())
                .orElseThrow();
        currentChild.setName(child.getName());

        // Неправильно, т.к. вызывает лишний раз
        // org.hibernate.event.internal.DefaultMergeEventListener.entityIsPersistent
        return childRepository.save(currentChild);
    }
}
