package com.example.validation.validation_groups;

import com.example.validation.configs.groups.BaseGroup;
import com.example.validation.configs.groups.GroupInheritance;
import com.example.validation.configs.groups.OrderedSequence;
import com.example.validation.validation_groups.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupValidationTest {

    @Autowired
    private GroupValidationService groupValidationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testImplicitDefaultGroupSimple() {
        List<String> messages = groupValidationService
                .simpleExampleGroupValidation(new BaseEntity(null, false))
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(1, messages.size());
        assertTrue( messages.contains("parent string must not be null"));
    }

    @Test
    void testExplicitDefaultGroupSimple() {
        List<String> messages = groupValidationService
                .simpleExampleGroupValidation(new BaseEntity(null, false), Default.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(1, messages.size());
        assertTrue( messages.contains("parent string must not be null"));
    }

    @Test
    void testNotDefaultGroupSimple() {
        List<String> messages = groupValidationService
                .simpleExampleGroupValidation(new BaseEntity(null, false), BaseGroup.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(1, messages.size());
        assertTrue( messages.contains("must be true"));
    }

    @Test
    void testFewGroupsSimple() {
        List<String> messages = groupValidationService
                .simpleExampleGroupValidation(new BaseEntity(null, false), Default.class, BaseGroup.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(2, messages.size());
        assertTrue( messages.contains("must be true"));
        assertTrue( messages.contains("parent string must not be null"));
    }

    @Test
    void testGroupInheritance() {

        //Ошибки не дублируются, несмотря на то, что указано
        List<String> messages = groupValidationService
                .inheritanceExampleGroupValidation(new DelegateBaseEntity(
                        new BaseEntity(null, false), 4), GroupInheritance.class, BaseGroup.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(2, messages.size());
        assertTrue( messages.contains("must be true"));
        assertTrue( messages.contains("must be null"));
    }

    @Test
    void testOrderedChecks() {

        //Если поменять в OrderSequence порядок классов, то ошибки будут другие.
        List<String> messages = groupValidationService
                .orderedChecks(new DelegateBaseEntity(
                        new BaseEntity(null, false), 4), OrderedSequence.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        // Ошибки 2, т.к. группа проверяется до конца. А уже следующие игнорируются
        assertEquals(2, messages.size());
        assertTrue( messages.contains("must be true"));
        assertTrue( messages.contains("must be null"));
    }


    @Test
    void testDefaultRedefining() {

        List<String> messages = groupValidationService
                //независимо от того какую группу мы передаём в валидатор(или вообще не передём), будет
                // отрботана та(те) которая указана в аннотации @GroupSequence над классом.
                .defaultRedefining(new RedefineDefault(null, 11, ""))
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(1, messages.size());
        //т.е. валидатор  по умолчаию проверяет только BaseGroup,
        assertTrue( messages.contains("size must be between 10 and 2147483647"));
    }

    @Test
    void testGroupValidationInController() throws Exception
    {
        String response = mockMvc.perform( post( "/group-validated" )
                .content( "{\"baseEntity\" : {\"boolValue\" : \"false\"}, \"nullValue\" : 4}" ).contentType( MediaType.APPLICATION_JSON ) )
                .andDo( print() )
                .andExpect( status().isOk() )
        .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains("\"defaultMessage\":\"must be true\""));
        assertEquals(497, response.length());
    }
    @Test
    void testGroupSequenceProviderTest() throws Exception
    {
        List<String> messages = groupValidationService
                // по флагу true в MyGroupSequenceProvider будет выбрана группа для валидации
                .groupSequenceProvider(new GroupSequenceProviderEntity(true, null, null))
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(1, messages.size());
        assertTrue( messages.contains("integer must be not null"));

        messages = groupValidationService
                // по флагу false в MyGroupSequenceProvider будет выбрана группа для валидации
                .groupSequenceProvider(new GroupSequenceProviderEntity(false, null, null))
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(2, messages.size());
        assertTrue( messages.contains("string must be not null"));
        assertTrue( messages.contains("integer must be not null"));
    }

    @Test
    void testConvertGroupTest() throws Exception
    {
        List<String> messages = groupValidationService
                // Сконвертирует Default в BaseGroup, т.е. сработают 2 валидации
                .groupConversion(new ConvertGroupEntity(new BaseEntity(null, false), null), Default.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(2, messages.size());
        assertTrue( messages.contains("must be true"));
        assertTrue( messages.contains("child string must not be null"));

        messages = groupValidationService
                // Нет конверсии, т.к. используется группа BaseGroup, а не Default
                .groupConversion(new ConvertGroupEntity(new BaseEntity(null, false), null), BaseGroup.class)
                .stream()
                .map( ConstraintViolation::getMessage )
                .collect( Collectors.toList() );

        assertEquals(1, messages.size());
        assertTrue( messages.contains("must be true"));

    }
}
