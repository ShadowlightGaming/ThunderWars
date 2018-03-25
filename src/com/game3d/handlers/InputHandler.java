package com.game3d.handlers;

import java.util.logging.Logger;

import com.game3d.Game;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;

public class InputHandler extends Game implements AnalogListener, ActionListener {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());

	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		switch(binding) {
		}
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		switch(name) {
		}
	}
}
