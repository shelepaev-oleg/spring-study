package com.scopetest;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;

/**
 * Скоуп с временем жизни больше 5 секунд
 */
public class PeriodicalScopeConfig implements Scope {

    Map<String, AbstractMap.SimpleEntry<LocalTime, Object>> map = new HashMap<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if (map.containsKey(s)) {
            AbstractMap.SimpleEntry<LocalTime, Object> pair = map.get(s);
            int last = now().getSecond() - pair.getKey().getSecond();
            if (last > 5) {
                map.put(s, new AbstractMap.SimpleEntry(now(), objectFactory.getObject()));
            }
        } else {
            map.put(s, new AbstractMap.SimpleEntry(now(), objectFactory.getObject()));
        }
        return map.get(s).getValue();
    }

    @Override
    public Object remove(String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
