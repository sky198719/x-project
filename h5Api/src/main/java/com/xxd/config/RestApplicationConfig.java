package com.xxd.config;

import com.xxd.common.extension.jaxrs.CustomJacksonContextResolver;
import com.xxd.common.extension.jaxrs.NotFoundExceptionMapper;
import com.xxd.common.extension.jaxrs.ParameterMissingExceptionMapper;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Info;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;

/**
 * Registers the components to be used by the JAX-RS application.
 */
public class RestApplicationConfig extends ResourceConfig {

    public RestApplicationConfig() {

        // 禁用jersey自动生成WADL(Web Application Description language)的功能
        property(ServerProperties.WADL_FEATURE_DISABLE, true);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        packages("com.xxd.h5.web");

        register(NotFoundExceptionMapper.class);
        register(ParameterMissingExceptionMapper.class);

		// register features
        register(JacksonFeature.class);
        register(CustomJacksonContextResolver.class);
        EncodingFilter.enableFor(this, GZipEncoder.class);
        register(MultiPartFeature.class);

        // register swagger
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.AcceptHeaderApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        BeanConfig beanConfig = new BeanConfig();
        Info info = new Info();
        beanConfig.setTitle("H5接口交互在线文档");
        beanConfig.setInfo(info);
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setBasePath("/apih5/api");
        beanConfig.setResourcePackage("com.xxd.h5.web");
        beanConfig.setScan(true);
    }
}
