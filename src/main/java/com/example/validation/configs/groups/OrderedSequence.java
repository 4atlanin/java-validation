package com.example.validation.configs.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Если валидация однойгруппы вернула ошибки, то валидация другой группы не начинается.
 * Валидация группы с ошибками проходит до конца, т.е. в итоге валидатор может вернуть несколько ошибок.
 */
@GroupSequence({GroupInheritance.class, BaseGroup.class, Default.class})
public interface OrderedSequence {
}
