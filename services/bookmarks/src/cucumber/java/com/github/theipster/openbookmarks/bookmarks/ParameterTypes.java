package com.github.theipster.openbookmarks.bookmarks;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;

import static java.util.Locale.ENGLISH;

public class ParameterTypes implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		typeRegistry.defineParameterType(
			new ParameterType<>(
				"url",
				"'(.*)'",
				Optional.class,
				(String spec) -> {
					try {
						return Optional.of(new URL(spec));
					} catch (MalformedURLException exception) {
						return Optional.empty();
					}
				}
			)
		);
	}
}
