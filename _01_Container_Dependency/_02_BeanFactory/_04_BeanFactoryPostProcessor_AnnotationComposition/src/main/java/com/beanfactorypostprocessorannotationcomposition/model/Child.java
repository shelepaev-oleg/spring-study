package com.beanfactorypostprocessorannotationcomposition.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.UUID;

@Entity(name = "child")
@Table(name = "child")
@Getter
public class Child {

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;

    /**
     * equals и hashCode обязательны, т.к. данная сущность используется как @OneToMany
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child )) return false;
        return id != null && id.equals(((Child) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
