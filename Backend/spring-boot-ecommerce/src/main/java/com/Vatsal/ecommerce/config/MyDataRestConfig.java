package com.Vatsal.ecommerce.config;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.Vatsal.ecommerce.dao.ProductRepository;
import com.Vatsal.ecommerce.entity.Country;
import com.Vatsal.ecommerce.entity.Order;
import com.Vatsal.ecommerce.entity.Product;
import com.Vatsal.ecommerce.entity.State;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
    
    private EntityManager entityManager;

    public MyDataRestConfig(EntityManager theEntityManager)
    {
        entityManager = theEntityManager;
    }
    
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PATCH};

        disableHttpMethods(Product.class, config, theUnsupportedActions);  
        disableHttpMethods(ProductRepository.class, config, theUnsupportedActions); 
        disableHttpMethods(Country.class, config, theUnsupportedActions);  
        disableHttpMethods(State.class, config, theUnsupportedActions);   
        disableHttpMethods(Order.class, config, theUnsupportedActions);   


        // expose Id
        exposeIds(config);

        // configure cors mapping
        cors.addMapping(config.getBasePath() +  "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
        .forDomainType(theClass)
        .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
        .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config)
    {
        // expose entity ids


        // get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        
        // create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // get the entity types for the entitites
        for (EntityType tempEntityType : entities)
        {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
        
    }
    
}
