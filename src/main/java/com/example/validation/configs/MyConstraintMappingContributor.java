package com.example.validation.configs;

import com.example.validation.validators.domain.TestListOfAnnotations;
import org.hibernate.validator.cfg.defs.NotNullDef;
import org.hibernate.validator.spi.cfg.ConstraintMappingContributor;

import java.lang.annotation.ElementType;

//Если добавить пропертю
// hibernate.validator.constraint_mapping_contributors=com.example.validation.configs.MyConstraintMappingContributor
// в validation.xml c именем этого класса, то эти констрэйнты будут в дефолтном валидаторе. стр 164
public class MyConstraintMappingContributor implements ConstraintMappingContributor {
    @Override
    public void createConstraintMappings(ConstraintMappingBuilder builder) {
        builder.addConstraintMapping()
                .type( TestListOfAnnotations.class )
                    .property("listOfAnnotations", ElementType.FIELD)
                        .ignoreAnnotations(true)
                        .constraint( new NotNullDef() )
                    .property( "justTwoAnnotations", ElementType.FIELD )
                        .ignoreAnnotations( true )
                        .constraint( new NotNullDef() );
    }
}
