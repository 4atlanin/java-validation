package com.example.validation.extractors;

import com.example.validation.extractors.entities.TestExtractorEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ExtractorService
{
    public Set<ConstraintViolation<TestExtractorEntity>> testExtractor( TestExtractorEntity tee )
    {

        //Экстрактор нужно зарегестрировать.
        //Так
        Validator localValidator = Validation
            .byDefaultProvider()
            .configure()
            .addValueExtractor( new GenericContainerKeyExtractor() )
            .addValueExtractor( new GenericContainerValueExtractor() )
            .addValueExtractor( new NonGenericContainerExtractor() )
            .buildValidatorFactory().getValidator();

        // или прописать их в файл
        //смотри META-INF/services/javax.validation.valueextraction.ValueExtractor

        return localValidator.validate( tee );
    }
}
