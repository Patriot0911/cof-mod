package com.modding.cof.modEvents.construction;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.modding.cof.annotations.ModEventHandler;

public class ModEventBus {
    private final Map<Class<?>, List<EventHandler>> eventHandlers = new HashMap<>();

    public static void registerAll() {
        // for (Method method : listener.getClass().getDeclaredMethods()) {
        //     if (method.isAnnotationPresent(SubscribeCustomEvent.class)) {
        //         Class<?> eventType = method.getAnnotation(SubscribeCustomEvent.class).value();
        //         eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>())
        //                 .add(new EventHandler(listener, method));
        //     }
        // }
        Reflections reflections = new Reflections("com.modding.cof");
        Set<Method> methods = reflections.getMethodsAnnotatedWith(ModEventHandler.class);

        for (Method method : methods) {
            try {
                System.out.println(method.getParameterAnnotations());
                // register(method);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static class EventHandler {
        private final Object instance;
        private final Method method;

        public EventHandler(Object instance, Method method) {
            this.instance = instance;
            this.method = method;
        }
    };
}
