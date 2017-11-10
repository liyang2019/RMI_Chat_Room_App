package common;

/**
 * This adapter is for the externally-sourced commands to  interact with the local model
 */
public interface ICmd2ModelAdapter {
	
	/**
	 * Append some message to the model, allowing the model to process the message by itself.
	 * Typically the model just appends to the view.
	 * @param text The message
	 * @param name The name of the person sending the text 
	 */
	public void appendMsg(String text, String name);
	
	/**
	 * Give the GUI a factory that creates a scrollable component component to add.
	 * Some components that are added are considered by the system to be just fancy displays of message contents and in such, 
	 * just like text messages, are desired to be placed on some sort of scrolling display.
	 * @param fac The factory to create the desired component
	 * @param label The identifying label for the scrollable component on the GUI
	 */
	public void buildScrollableComponent(IComponentFactory fac, String label);
	
	/**
	 * Give the GUI a factory that creates a non-scrollable component component to add.
	 * Some components that are added are actually static modifications of the local GUI to present additional user interaction capabilities.
	 * It would be undesireable for these components to scroll off the screen as other messages arrive;
	 * they should have a fixed location on the local GUI.
	 * @param fac the factory to create the desired component
	 * @param label The identifying label for the non-scrollable component on the GUI
	 */
	public void buildNonScrollableComponent(IComponentFactory fac, String label);
}
