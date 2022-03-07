package com.prototypeinsingletonok;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class PrototypeInSingletonTest {

    @Autowired
    SingletonBean singletonBean;

    @Test
    public void testScopes() throws Exception {
        long first = singletonBean.randomNumberFromPrototypeBean();
        long second = singletonBean.randomNumberFromPrototypeBean();
        assertNotEquals(first, second);
    }
}
