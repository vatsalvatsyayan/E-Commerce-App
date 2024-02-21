package com.Vatsal.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.Vatsal.ecommerce.dao.ProductRepository;
import com.Vatsal.ecommerce.entity.Product;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.POST};

        config.getExposureConfiguration()
              .forDomainType(Product.class)
              .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
              .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    
        config.getExposureConfiguration()
        .forDomainType(ProductRepository.class)
        .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
        .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));  
    }
    
}
