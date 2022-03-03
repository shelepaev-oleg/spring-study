package com.beanfactorypostprocessorannotationcomposition;

import com.beanfactorypostprocessorannotationcomposition.model.Child;
import com.beanfactorypostprocessorannotationcomposition.model.Parent;
import com.beanfactorypostprocessorannotationcomposition.model.ViewChild;
import com.beanfactorypostprocessorannotationcomposition.repository.ParentRepository;
import com.beanfactorypostprocessorannotationcomposition.repository.ViewChildRepository;
import com.beanfactorypostprocessorannotationcomposition.service.ChildService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ViewChildTest {

    @Autowired
    private ChildService childService;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ViewChildRepository viewChildRepository;

    private Parent initParent() {
        Parent parent = new Parent();
        parent.setName("parent");
        return parentRepository.save(parent);
    }

    private Child initChild() {
        Parent parent = initParent();

        Child child = new Child();
        child.setName("child");
        return childService.create(parent, child);
    }

    @Test
    public void findAll() {
        // given
        initChild();

        // then
        List<ViewChild> viewChildList = viewChildRepository.findAll();
        Assertions.assertFalse(viewChildList.isEmpty());
    }
}
