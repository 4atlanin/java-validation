package com.example.validation.configs;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;
import java.util.*;

public class MyValidationProviderResolver implements ValidationProviderResolver {
    @Override
    public List<ValidationProvider<?>> getValidationProviders() {
        return loadProviders(Thread.currentThread().getContextClassLoader());
    }

    //копипаста из либы
    private List<ValidationProvider<?>> loadProviders(ClassLoader classloader) {
        ServiceLoader<ValidationProvider> loader = ServiceLoader.load( ValidationProvider.class, classloader );
        Iterator<ValidationProvider> providerIterator = loader.iterator();
        List<ValidationProvider<?>> validationProviderList = new ArrayList<>();
        while ( providerIterator.hasNext() ) {
            try {
                validationProviderList.add( providerIterator.next() );
            }
            catch ( ServiceConfigurationError e ) {
                // ignore, because it can happen when multiple
                // providers are present and some of them are not class loader
                // compatible with our API.
            }
        }
        return validationProviderList;
    }
}
