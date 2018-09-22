package com.eden.orchid.impl.themes.functions;

import com.eden.orchid.api.OrchidContext;
import com.eden.orchid.api.compilers.TemplateFunction;
import com.eden.orchid.api.converters.StringConverter;
import com.eden.orchid.api.options.annotations.BooleanDefault;
import com.eden.orchid.api.options.annotations.Description;
import com.eden.orchid.api.options.annotations.Option;
import com.eden.orchid.api.resources.resource.OrchidResource;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Description(value = "Load a resource's content into a String.", name = "Load")
public final class LoadFunction extends TemplateFunction {

    private final OrchidContext context;
    private final StringConverter converter;

    @Getter
    @Setter
    @Option
    @Description("The resource to lookup and render in-place.")
    private String resource;

    @Getter
    @Setter
    @Option
    @BooleanDefault(true)
    @Description("When true, only resources from local sources are considered, otherwise resources from all plugins " +
            "and from the current theme will also be considered."
    )
    private boolean localResourcesOnly;

    @Inject
    public LoadFunction(OrchidContext context, StringConverter converter) {
        super("load", true);
        this.context = context;
        this.converter = converter;
    }

    @Override
    public String[] parameters() {
        return new String[]{"resource", "localResourcesOnly"};
    }

    @Override
    public Object apply() {
        OrchidResource foundResource = localResourcesOnly ? context.getLocalResourceEntry(resource) : context.getResourceEntry(resource);

        if (foundResource != null) {
            return foundResource.compileContent(null);
        }

        return "";
    }

}