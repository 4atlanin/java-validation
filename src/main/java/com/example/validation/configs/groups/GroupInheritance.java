package com.example.validation.configs.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

//@GroupSequence({  OrderedSequence.class})
// Аннотация вызовет - HV000047: Cyclic dependency in groups definition
// т.к. OrderedSequence включает в себя GroupInheritance
public interface GroupInheritance extends BaseGroup {
}
