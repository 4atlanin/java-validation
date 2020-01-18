##### Validator notes
 - The generated ValidatorFactory and Validator instances are thread-safe
and can be cached
 - Если несколько реализации валидатора используется, то buildDefaultValidatorFactory() не гарантирует 
 порядок или выбор какойто определённой, возьмёт что первое попалось.
 
 - Выбор специфичной релизации

``` 
   ValidatorFactory validatorFactory = Validation
                        .byProvider( HibernateValidator.class ) //тут указывем
                        .configure()
                        .buildValidatorFactory();
  Validator validator = validatorFactory.getValidator(); 
```  
 - Выбор дефолтной реализации
 ```
....
Validation.byDefaultProvider()....
....
```
 - ValidatorFactory нужно закрывать через ValidatorFactory#close()