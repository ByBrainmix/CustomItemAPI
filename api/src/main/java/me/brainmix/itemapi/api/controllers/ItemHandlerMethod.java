package me.brainmix.itemapi.api.controllers;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.events.*;

public class ItemHandlerMethod {

    private Method method;
    private Class<?> type;
    private ItemHandler handlerAnnotation;
    private static final Set<Class> BLACKLIST = new HashSet<>(Arrays.asList(
            ItemDelayTickEvent.class,
            ItemThrowEvent.class,
            ItemFlyTickEvent.class,
            ItemHitEntityEvent.class,
            ItemHitPlayerEvent.class,
            ItemHitGroundEvent.class,
            ItemPickupEvent.class,
            ItemDespawnEvent.class,
            ItemDropEvent.class,
            ItemShootEvent.class,
            ItemShootArrowEvent.class,
            ItemProjectileFlyTickEvent.class,
            ItemProjectileHitEvent.class,
            ItemProjectileHitEvent.class,
            ItemProjectileHitPlayerEvent.class
    ));

    public ItemHandlerMethod(Method method, Class<?> type, ItemHandler handlerAnnotation) {
        this.method = method;
        this.type = type;
        this.handlerAnnotation = handlerAnnotation;
    }

    public <E extends ItemEvent> boolean invoke(CustomItem item, E event) {
        if(event.getTimeLeft() == -1 && handlerAnnotation.delay()  && !BLACKLIST.contains(type)) return false;
        if(event.getTimeLeft() != -1 && !handlerAnnotation.delay() && !BLACKLIST.contains(type)) return false;
        try {
            method.invoke(item, event);
            return true;
        } catch (IllegalAccessException | InvocationTargetException ignore) {}
        return false;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getType() {
        return type;
    }

    public ItemHandler getHandlerAnnotation() {
        return handlerAnnotation;
    }

}
