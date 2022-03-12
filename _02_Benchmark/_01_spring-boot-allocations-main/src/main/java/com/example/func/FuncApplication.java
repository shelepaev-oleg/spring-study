package com.example.func;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.config.BeanCountingApplicationListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.ResourceHandlerRegistrationCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.EnableWebFluxConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.WebFluxConfig;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxRegistrations;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessorRegistrar;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.support.HandlerFunctionAdapter;
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping;
import org.springframework.web.reactive.function.server.support.ServerResponseResultHandler;
import org.springframework.web.reactive.resource.ResourceUrlProvider;
import org.springframework.web.reactive.result.SimpleHandlerAdapter;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityResultHandler;
import org.springframework.web.reactive.result.view.ViewResolutionResultHandler;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.i18n.LocaleContextResolver;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Functional bean definitions. No Spring Boot.
 * 
 * @author Dave Syer
 *
 */
public class FuncApplication implements Runnable, Closeable,
		ApplicationContextInitializer<GenericApplicationContext> {

	public static final String MARKER = "Benchmark app started";

	private GenericApplicationContext context;

	@Bean
	public RouterFunction<?> userEndpoints() {
		return route(GET("/"), request -> ok().body(Mono.just("Hello"), String.class));
	}

	public static void main(String[] args) throws Exception {
		long t0 = System.currentTimeMillis();
		FuncApplication bean = new FuncApplication();
		bean.run();
		System.err.println(
				"Started HttpServer: " + (System.currentTimeMillis() - t0) + "ms");
		if (Boolean.getBoolean("demo.close")) {
			bean.close();
		}
	}

	@Override
	public void close() throws IOException {
		if (context != null) {
			context.close();
		}
	}

	@Override
	public void run() {
		ReactiveWebServerApplicationContext context = new ReactiveWebServerApplicationContext();
		initialize(context);
		context.refresh();
		System.err.println(MARKER);
		new BeanCountingApplicationListener().log(context);
	}

	@Override
	public void initialize(GenericApplicationContext context) {
		this.context = context;
		performPreinitialization();
		context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
		registerDemoApplication();
		registerWebServerFactoryCustomizerBeanPostProcessor();
		registerConfigurationProperties();
		// context.registerBean(LazyInitBeanFactoryPostProcessor.class);
		registerPropertyPlaceholderAutoConfiguration();
		registerReactiveWebServerFactoryAutoConfiguration();
		registerErrorWebFluxAutoConfiguration();
		registerWebFluxAutoConfiguration();
		registerHttpHandlerAutoConfiguration();
		registerGsonAutoConfiguration();
		registerHttpMessageConvertersAutoConfiguration();
		registerWebClientAutoConfiguration();
	}

	private void performPreinitialization() {
		try {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					runSafely(() -> new DefaultFormattingConversionService());
				}

				public void runSafely(Runnable runnable) {
					try {
						runnable.run();
					}
					catch (Throwable ex) {
						// Ignore
					}
				}

			}, "background-preinit");
			thread.start();
		}
		catch (Exception ex) {
		}
	}

	private void registerConfigurationProperties() {
		new ConfigurationPropertiesBindingPostProcessorRegistrar()
				.registerBeanDefinitions(null, context);
		context.registerBean(ServerProperties.class, () -> new ServerProperties());
		context.registerBean(ResourceProperties.class, () -> new ResourceProperties());
		context.registerBean(WebFluxProperties.class, () -> new WebFluxProperties());
		context.registerBean(GsonProperties.class, () -> new GsonProperties());
		context.registerBean(HttpProperties.class, () -> new HttpProperties());
	}

	private void registerWebServerFactoryCustomizerBeanPostProcessor() {
		context.registerBean("webServerFactoryCustomizerBeanPostProcessor",
				WebServerFactoryCustomizerBeanPostProcessor.class);
	}

	private void registerPropertyPlaceholderAutoConfiguration() {
		context.registerBean(PropertySourcesPlaceholderConfigurer.class,
				() -> PropertyPlaceholderAutoConfiguration
						.propertySourcesPlaceholderConfigurer());
	}

	private void registerReactiveWebServerFactoryAutoConfiguration() {
		ReactiveWebServerFactoryAutoConfiguration config = new ReactiveWebServerFactoryAutoConfiguration();
		context.registerBean(ReactiveWebServerFactoryCustomizer.class,
				() -> config.reactiveWebServerFactoryCustomizer(
						context.getBean(ServerProperties.class)));
		context.registerBean(NettyReactiveWebServerFactory.class,
				() -> new NettyReactiveWebServerFactory());
	}

	private void registerErrorWebFluxAutoConfiguration() {
		context.registerBean(ErrorAttributes.class, () -> new DefaultErrorAttributes(
				context.getBean(ServerProperties.class).getError().isIncludeException()));
		context.registerBean(ErrorWebExceptionHandler.class, () -> {
			return errorWebFluxAutoConfiguration().errorWebExceptionHandler(
					context.getBean(ErrorAttributes.class),
					context.getBean(ResourceProperties.class),
					context.getDefaultListableBeanFactory()
							.getBeanProvider(ResolvableType.forClassWithGenerics(
									List.class, ViewResolver.class)),
					context.getBean(ServerCodecConfigurer.class), context);
		});
	}

	private ErrorWebFluxAutoConfiguration errorWebFluxAutoConfiguration() {
		ServerProperties serverProperties = context.getBean(ServerProperties.class);
		return new ErrorWebFluxAutoConfiguration(serverProperties);
	}

	/**
	 * Trivial subclass to prevent <code>@Bean</code> configuration kicking in in
	 * {@link CuncApplication}.
	 *
	 */
	private static class NotEnableWebFluxConfiguration
			extends EnableWebFluxConfiguration {

		public NotEnableWebFluxConfiguration(WebFluxProperties webFluxProperties,
				ObjectProvider<WebFluxRegistrations> webFluxRegistrations) {
			super(webFluxProperties, webFluxRegistrations);
		}
	}

	private void registerWebFluxAutoConfiguration() {
		context.registerBean(NotEnableWebFluxConfiguration.class,
				() -> new NotEnableWebFluxConfiguration(
						context.getBean(WebFluxProperties.class),
						context.getBeanProvider(WebFluxRegistrations.class)));
		context.registerBean(HandlerFunctionAdapter.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).handlerFunctionAdapter());
		context.registerBean(WebHttpHandlerBuilder.LOCALE_CONTEXT_RESOLVER_BEAN_NAME,
				LocaleContextResolver.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.localeContextResolver());
		context.registerBean(RequestMappingHandlerAdapter.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.requestMappingHandlerAdapter(
								context.getBean(ReactiveAdapterRegistry.class),
								context.getBean(ServerCodecConfigurer.class),
								context.getBean(FormattingConversionService.class),
								context.getBean(Validator.class)));
		context.registerBean(RequestMappingHandlerMapping.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.requestMappingHandlerMapping(
								context.getBean(RequestedContentTypeResolver.class)));
		context.registerBean(ResourceUrlProvider.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).resourceUrlProvider());
		context.registerBean(HandlerMapping.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.resourceHandlerMapping(
								context.getBean(ResourceUrlProvider.class)));
		context.registerBean(ResponseBodyResultHandler.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.responseBodyResultHandler(
								context.getBean(ReactiveAdapterRegistry.class),
								context.getBean(ServerCodecConfigurer.class),
								context.getBean(RequestedContentTypeResolver.class)));
		context.registerBean(ResponseEntityResultHandler.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.responseEntityResultHandler(
								context.getBean(ReactiveAdapterRegistry.class),
								context.getBean(ServerCodecConfigurer.class),
								context.getBean(RequestedContentTypeResolver.class)));
		context.registerBean(WebExceptionHandler.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.responseStatusExceptionHandler());
		context.registerBean(RouterFunctionMapping.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.routerFunctionMapping(
								context.getBean(ServerCodecConfigurer.class)));
		context.registerBean(WebHttpHandlerBuilder.SERVER_CODEC_CONFIGURER_BEAN_NAME,
				ServerCodecConfigurer.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.serverCodecConfigurer());
		context.registerBean(ServerResponseResultHandler.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.serverResponseResultHandler(
								context.getBean(ServerCodecConfigurer.class)));
		context.registerBean(SimpleHandlerAdapter.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).simpleHandlerAdapter());
		context.registerBean(ViewResolutionResultHandler.class,
				() -> context.getBean(EnableWebFluxConfiguration.class)
						.viewResolutionResultHandler(
								context.getBean(ReactiveAdapterRegistry.class),
								context.getBean(RequestedContentTypeResolver.class)));
		context.registerBean(ReactiveAdapterRegistry.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).webFluxAdapterRegistry());
		context.registerBean(RequestedContentTypeResolver.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).webFluxContentTypeResolver());
		context.registerBean(FormattingConversionService.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).webFluxConversionService());
		context.registerBean(Validator.class, () -> context
				.getBean(EnableWebFluxConfiguration.class).webFluxValidator());
		context.registerBean(WebHttpHandlerBuilder.WEB_HANDLER_BEAN_NAME,
				DispatcherHandler.class,
				() -> context.getBean(EnableWebFluxConfiguration.class).webHandler());
		context.registerBean(WebFluxConfigurer.class,
				() -> new WebFluxConfig(context.getBean(ResourceProperties.class),
						context.getBean(WebFluxProperties.class), context,
						context.getBeanProvider(HandlerMethodArgumentResolver.class),
						context.getBeanProvider(CodecCustomizer.class),
						context.getBeanProvider(
								ResourceHandlerRegistrationCustomizer.class),
						context.getBeanProvider(ViewResolver.class)));
	}

	private void registerHttpHandlerAutoConfiguration() {
		context.registerBean(HttpHandler.class,
				() -> WebHttpHandlerBuilder.applicationContext(context).build());
	}

	private void registerDemoApplication() {
		context.registerBean(RouterFunction.class, () -> userEndpoints());
	}

	private void registerGsonAutoConfiguration() {
		GsonAutoConfiguration config = new GsonAutoConfiguration();
		context.registerBean(GsonBuilder.class, () -> config.gsonBuilder(new ArrayList<>(
				context.getBeansOfType(GsonBuilderCustomizer.class).values())));
		context.registerBean(Gson.class,
				() -> config.gson(context.getBean(GsonBuilder.class)));
		context.registerBean(GsonBuilderCustomizer.class, () -> config
				.standardGsonBuilderCustomizer(context.getBean(GsonProperties.class)));
	}

	private void registerHttpMessageConvertersAutoConfiguration() {
		context.registerBean(HttpMessageConverters.class, () -> {
			HttpMessageConvertersAutoConfiguration config = new HttpMessageConvertersAutoConfiguration();
			return config.messageConverters(context.getBeanProvider(ResolvableType
					.forClassWithGenerics(List.class, HttpMessageConverter.class)));
		});
		context.registerBean(StringHttpMessageConverter.class,
				this::stringHttpMessageConverter);
		context.registerBean(GsonHttpMessageConverter.class,
				() -> new GsonHttpMessageConverter(context.getBean(Gson.class)));
	}

	StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(
				context.getBean(HttpProperties.class).getEncoding().getCharset());
		converter.setWriteAcceptCharset(false);
		return converter;
	}

	private void registerWebClientAutoConfiguration() {
		context.registerBean(WebClient.Builder.class, () -> {
			WebClientAutoConfiguration config = new WebClientAutoConfiguration(
					context.getBeanProvider(ResolvableType.forClassWithGenerics(
							List.class, WebClientCustomizer.class)));
			return config.webClientBuilder();
		});
	}

}
