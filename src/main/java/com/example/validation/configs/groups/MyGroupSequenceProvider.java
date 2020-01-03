package com.example.validation.configs.groups;

import com.example.validation.validation_groups.domain.GroupSequenceProviderEntity;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

public class MyGroupSequenceProvider implements DefaultGroupSequenceProvider<GroupSequenceProviderEntity> {
    @Override
    public List<Class<?>> getValidationGroups(GroupSequenceProviderEntity object) {
        List<Class<?>> groups = new ArrayList<>();

        groups.add(GroupSequenceProviderEntity.class);

        if (object == null) {
            return groups;
        }

        if(object.isGroupFlag()) {
            groups.add(BaseGroup.class);   // Default сюда нельзя сувать
        } else {
            groups.add(GroupInheritance.class);
        }

        return groups;
    }
}
