package com.example.validation.configs;

import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

/**
 * Пример, как можно проверять проперти из файла, получаемые с помощью аннотации @ConfigurationProperties
 */

@Configuration
@Validated     //required
@Setter        //required
@ConfigurationProperties( prefix = "test.value" )
public class ValidationConfigTest
{
    // Удивительно, но @Valid тут не нужен, Проверка выполняется и каскадируется без него.
    // Да, я разносил классы по разным файлам, всё ещё работает. Т.е. Это не из-за того что все классы в одном файле.
    private PropertiesHolder holder;

    @Bean
    public String getLongString()
    {
        return "123456789" + holder.getField();
    }
}

@Data
class PropertiesHolder
{
    // @Valid Проверка выполняется и каскадируется без него, что странно
    private InternalClass internalClass;

    @Size( max = 100 )    //  если уменьшить max, то получим ошибку валидации
    private String field;
}

@Data
class InternalClass
{

    @Size( max = 20 )           //  если уменьшить max, то получим ошибку валидации
    private String field2;
}


