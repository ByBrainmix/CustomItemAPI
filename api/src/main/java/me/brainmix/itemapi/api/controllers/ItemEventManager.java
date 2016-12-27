package me.brainmix.itemapi.api.controllers;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.events.ItemEvent;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.stream.Collectors;

public class ItemEventManager {

    private Map<String, Set<ItemHandlerMethod>> methods = new HashMap<>();

    public void register(CustomItem item) {
        methods.put(item.getId(), new HashSet<>());

        Arrays.asList(item.getClass().getMethods()).stream().filter(m -> m.isAnnotationPresent(ItemHandler.class)).filter(m -> m.getParameterTypes().length != 0).forEach(m -> {
            methods.get(item.getId()).add(new ItemHandlerMethod(m, m.getParameterTypes()[0], m.getAnnotation(ItemHandler.class)));
        });
    }

    public <T extends ItemEvent> boolean callEvent(CustomItem item, T event) {
        boolean called = false;
        if(methods.containsKey(item.getId())) {
            for(ItemHandlerMethod m : methods.get(item.getId()).stream().filter(mt -> mt.getType() == event.getClass()).collect(Collectors.toList())) {
                boolean c = m.invoke(item, event);
                if(!called) called = c;
            }
        }
        return called;
    }

    public <T extends ItemEvent> boolean hasWith(String id, Class<T> eventType) {
        return methods.containsKey(id) && !methods.get(id).stream().filter(m -> m.getType() == eventType).collect(Collectors.toSet()).isEmpty();
    }

    public <T extends ItemEvent> boolean hasWithoutDelay(String id, Class<T> eventType) {
        return methods.containsKey(id) && !methods.get(id).stream().filter(m -> m.getType() == eventType).filter(m -> !m.getHandlerAnnotation().delay()).collect(Collectors.toSet()).isEmpty();
    }


}
