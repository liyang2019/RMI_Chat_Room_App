package common;

/**
 * This adapter is for the externally-sourced commands to  interact with the local model
 */
public interface ICmd2ModelAdapter {
	
	/**
	 * Append some message to the model, allowing the model to process the message by itself.
	 * Typically the model just appends to the view.
	 * @param text The message
	 */
	public void appendMsg(String text);
	
	/**
	 * Give the GUI a factory that creates the desired component to add.
	 * Two types of components can be created by this factory, ScrollableComponent and NonScrollableComponent.
	 * @param fac the factory to create the desired component
	 */
	void buildComponent(IComponentFactory fac);
}
