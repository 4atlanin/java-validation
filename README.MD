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
 - Если Hibernate Validation есть в classpath, то он включён по умолчанию.
 Чтобы выключить - `javax.persistence.validation.mode=none`
 - Если валидация включена, но энтитя не помечена ограниеними, то нет 
 потерь на производительность - стр 146
 - Хибер Валидатору можно указать failFastMode HibernateValidatorConfiguration#failFast, т.е. проверка 
 прервётся на первой же ошибке валидации. Порядок проверок неопределён.
 #### Валидационные ограничения над энтитями и их полями.
  - по умолчанию ограничения учитываются при генерации DDL. 
  Например, если @NotNull над полем, то и колонка в бд сгенерится как not null.
  Отключить всё можно пропертей `hibernate.validator.apply_to_ddl=false` или 
  только определённые группы `org.hibernate.validator.group.ddl=список классов групп чеез запятую`
 #### BeanValidationEventListener
 - Этот лисенер работает по `PreInsertEvent`, `PreUpdateEvent` or `PreDeleteEvent` событиям
 которые кидаются перед соответствующей операцией с базой. 
 ВАЖНО!!! `PreDeleteEvent` не кидается по умолчанию, нужно включать. 
 Можно пропертей указывать `javax.persistence.validation.group.pre-remove` указывать список групп 
 для которых нужно проверять констрейнты при возникновении соответствующего эвента...
 #### Добавление ограничений программно
 - Добавить ограничение и валидатор к нему можно так:
 
 ```
constraintMapping
    .constraintDefinition( ValidPassengerCount.class )
    .validatedBy( ValidPassengerCountValidator.class );
```
или
```
constraintMapping
    .constraintDefinition( ValidPassengerCount.class )
    .validateType( Bus.class )
    .with( b -> b.getSeatCount() >= b.getPassengers().size() );
```

Прикол в том, что таким способом можно переопределять валидаторы для дефолтных аннотаций.
#### Чтобы валидация геттеров сработала, они должн удовлетворять след условиям 
• the method name starts with get , it has a non-void return type and has no parameters;
• the method name starts with is , has a return type of boolean and has no parameters;
• the method name starts with has , has a return type of boolean and has no parameters (this rule
is specific to Hibernate Validator and is not mandated by the JavaBeans specification)

Смотри пример реализации стратегии FluentGetterSelectionPropertyStrategy.
#### Настройка имён полей которые будут использоваться валидатором, показваться в сообщениях и violation.getPropertyPath().toString()
 - стр 180, 
    12.16. Customizing the property name resolution for constraint violations.
    
#### Порядок вызова валидаций не определён. Чтобы гарантировать порялок - юзай GroupSequences
 - стр 158, 
    12.2. Fail fast mode

 
 
  