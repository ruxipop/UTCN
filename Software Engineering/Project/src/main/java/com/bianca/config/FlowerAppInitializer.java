package com.bianca.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class FlowerAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        Class[] configFiles = {FlowerAppConfig.class};
        return configFiles;
    }

    @Override
    protected String[] getServletMappings() {
        String[] mappings = {"/"}; // handle every URL with "/"
        return mappings;
    }

}
