package com.myprojects.myportfolio.clients.general.views;

import com.myprojects.myportfolio.clients.general.views.IView.View;

@View(name = Verbose.name)
public class Verbose extends Normal {
	public static final String name = "verbose";
	public static final Verbose value = new Verbose();
}
