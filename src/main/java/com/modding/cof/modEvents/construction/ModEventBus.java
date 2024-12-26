package com.modding.cof.modEvents.construction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.modding.cof.annotations.ModEventHandler;
import com.modding.cof.skills.IBaseSkill;
import com.modding.cof.skills.LvlUpHeal;


public class ModEventBus {
    private final static Map<String, List<EventHandler>> eventHandlers = new HashMap<>();
    public static List<Class<? extends IBaseSkill>> eventClasses = new ArrayList<>(
        Arrays.asList(
            LvlUpHeal.class
        )
    );

    public static void registerAll() {
        for(Class<? extends IBaseSkill> SkillItem : eventClasses) {
            for(Method method : SkillItem.getMethods()) {
                for(Annotation annotation : method.getAnnotations()) {
                    if(annotation instanceof ModEventHandler specAnnotation) {
                        List<EventHandler> list = eventHandlers.get(specAnnotation.eventName());
                        if(list == null) {
                            list = new ArrayList<EventHandler>();
                        };
                        list.add(new EventHandler(
                            SkillItem,
                            method
                        ));
                        eventHandlers.put(specAnnotation.eventName(), list);
                        System.out.println(annotation.annotationType().getName());
                    };
                };
            };
        };
    };

    public static void emit(ModEvent event) {
        List<EventHandler> list = eventHandlers.get(event.getName());
        if(list == null) {
            return;
        };
        for(EventHandler item : list) {
            try {
                item.method.invoke(item.instance, event);
            } catch (Exception e) {
                e.printStackTrace();
            };
        };
    };

    private static class EventHandler {
        private final Class<? extends IBaseSkill> instance;
        private final Method method;

        public EventHandler(Class<? extends IBaseSkill> instance, Method method) {
            this.instance = instance;
            this.method = method;
        }
    };
}
