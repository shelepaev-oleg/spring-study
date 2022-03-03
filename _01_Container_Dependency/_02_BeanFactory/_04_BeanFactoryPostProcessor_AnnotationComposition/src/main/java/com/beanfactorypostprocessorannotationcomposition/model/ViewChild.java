package com.beanfactorypostprocessorannotationcomposition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
@Immutable
@NoArgsConstructor
@Subselect("select "
        + "c.ID "
        + "from child c")
@FieldNameConstants
public class ViewChild {

    @Id
    private UUID id;
}
