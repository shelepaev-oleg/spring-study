package com.prototypeinsingletonbenchmark;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.Closeable;
import java.io.IOException;

@SpringBootApplication(proxyBeanMethods = false)
public class DemoApplication implements Runnable, Closeable {

	private ConfigurableApplicationContext context;

	public static void main(String[] args) throws Exception {
		DemoApplication last = new DemoApplication();
		last.run();
		if (Boolean.getBoolean("demo.close")) {
			last.close();
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
		context = new SpringApplicationBuilder(DemoApplication.class)
				.properties("--server.port=0", "--spring.jmx.enabled=false").run();
	}

}
