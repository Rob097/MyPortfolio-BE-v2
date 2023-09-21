package com.myprojects.myportfolio.clients.general.views;

import com.myprojects.myportfolio.clients.general.views.IView.View;

@View(name = Normal.name)
public class Normal implements IView {
	public static final String name = "normal";
	public static final Normal value = new Normal();
}
