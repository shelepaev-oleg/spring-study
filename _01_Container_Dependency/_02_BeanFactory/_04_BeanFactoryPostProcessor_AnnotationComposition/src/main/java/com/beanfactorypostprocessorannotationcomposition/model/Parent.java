package com.beanfactorypostprocessorannotationcomposition.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "parent")
@Table(name = "parent")
@Getter
public class Parent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Setter
    @Column(name = "name")
    private String name;

    /**
     * Оптимистичная блокировка
     */
    @Version
    private Long version;

    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Child> childList = new ArrayList<>();

    public void addChild(final Child child) {
        this.childList.add(child);
        child.setParent(this);
    }
    public void removeChild(final Child child) {
        childList.remove(child);
        child.setParent(null);
    }
}
