package common;

import java.awt.Component;

/**
 * A factory for creating desired GUI components.
 * Two types of components can be created by this factory, which may be added to the local GUI: 
 * ScrollableComponent and NonScrollableComponent 
 */
public interface IComponentFactory {
	
	/**
	 * Make a scrollable component
	 * These components that are added are considered by the system to be just fancy displays of message contents and in such, 
	 * just like text messages, are desired to be placed on some sort of scrolling display.
	 * @return a scrollable component
	 */
	public Component makeScrollableComponent();

	/**
	 * Make a NonScrollable component
	 * Some components that are added are actually static modifications of the local GUI to present additional user interaction capabilities.
	 * It would be undesireable for these components to scroll off the screen as other messages arrive;
	 * they should have a fixed location on the local GUI.
	 * @return a NonScrollable component
	 */
	public Component makeNonScrollableComponent();
}
