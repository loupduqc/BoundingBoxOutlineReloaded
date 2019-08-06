package com.irtimaled.bbor.client.keyboard;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Key extends KeyBinding {
    private InputUtil.KeyCode input;
    private KeyHandler onKeyPress;
    private KeyHandler onLongKeyPress;
    private int longPressDuration;

    Key(String description, int keyCode, String category) {
        super(description, keyCode, category);
    }

    public Key onKeyPressHandler(KeyHandler onKeyPress) {
        this.onKeyPress = onKeyPress;
        return this;
    }

    public Key onLongKeyPressHandler(int duration, KeyHandler onLongKeyPress) {
        this.longPressDuration = duration;
        this.onLongKeyPress = onLongKeyPress;
        return this;
    }

    InputUtil.KeyCode getInput() {
        if (input == null)
            return getDefaultKeyCode();
        return input;
    }


    @Override
    public void setKeyCode(InputUtil.KeyCode input) {
        this.input = input;
        super.setKeyCode(input);
    }


    private int pressDuration = 0;

    @Override
    public boolean isPressed() {
        return pressDuration == 1;
    }

    void release() {
        if (onKeyPress != null && (onLongKeyPress == null || pressDuration < longPressDuration)) {
            onKeyPress.handle();
        }

        pressDuration = 0;
    }

    void repeat() {
        if (onLongKeyPress == null) return;

        if (pressDuration <= longPressDuration) {
            pressDuration++;
        }

        if (pressDuration == longPressDuration) {
            onLongKeyPress.handle();
        }
    }

    void press() {
        pressDuration++;
    }
}
