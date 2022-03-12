package com.prototypeinsingletonbenchmark.ok;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;

public class PrototypeInSingletonOk implements Runnable, Closeable {

    public static final String MARKER = "Benchmark app started";

    private ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();
        PrototypeInSingletonOk bean = new PrototypeInSingletonOk();
        bean.run();
        SingletonBean singletonBean = bean.context.getBean(SingletonBean.class);
        System.out.println(singletonBean.randomNumberFromPrototypeBean());
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
        this.context = create();
        System.err.println(MARKER);
    }

    private ConfigurableApplicationContext create() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        return context;
    }
}
