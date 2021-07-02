/*
 * ICP TS GmbH
 * Copyright (c) 2019 All Rights Reserved.
 */

package com.example.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Factory class for converting yaml files to {@link PropertySource}.
 * <p>
 * Yaml files are not supported by default for {@link org.springframework.context.annotation.PropertySource} annotation.
 *
 * @author mgudelj
 * @see PropertySource
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

	/**
	 * Reads yaml file from {@link EncodedResource} and converts it to {@link PropertySource}.
	 *
	 * @param name     the source file name, may be {@code null}.
	 * @param resource the file resource.
	 * @return the property source, contains parsed yaml file.
	 * @throws IOException in case of I/O exceptions.
	 */
	@Override
	public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
		Properties propertiesFromYaml = loadYamlIntoProperties(resource);
		String sourceName = name != null ? name : resource.getResource().getFilename();

		return new PropertiesPropertySource(sourceName, propertiesFromYaml);
	}

	private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
		try {
			YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
			factory.setResources(resource.getResource());
			factory.afterPropertiesSet();

			return factory.getObject();
		} catch (IllegalStateException e) {
			throw e;
		}
	}
}
