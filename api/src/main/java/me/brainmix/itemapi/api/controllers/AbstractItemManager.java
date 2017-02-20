package me.brainmix.itemapi.api.controllers;

import me.brainmix.itemapi.api.ItemRegister;

public abstract class AbstractItemManager {

    private ItemRegister register;

    protected AbstractItemManager(ItemRegister register) {
        this.register = register;
    }

    protected ItemRegister getRegister() {
        return register;
    }

}
