package com.beanfactorypostprocessorannotationcomposition.service;

import com.beanfactorypostprocessorannotationcomposition.model.Child;
import com.beanfactorypostprocessorannotationcomposition.model.Parent;

public interface ChildService {

    Child create(Parent parent, Child child);

    Child updateOk(Child child);

    Child updateWrong(Child child);
}
