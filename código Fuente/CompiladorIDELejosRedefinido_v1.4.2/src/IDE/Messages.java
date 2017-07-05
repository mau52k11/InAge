package IDE;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	////////////////////////////////////////////////////////////////////////////
	String BUNDLE_NAME = "IDE.messages_es_MX"; //$NON-NLS-1$
	ResourceBundle RESOURCE_BUNDLE = loadBundle();
	ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
//	public void setBundle(String idioma){
//		this.BUNDLE_NAME = idioma;
//	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	////////////////////////////////////////////////////////////////////////////
	public String getString(String key) {
		try {
			//ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			ResourceBundle bundle2 = ResourceBundle.getBundle(BUNDLE_NAME);
			System.out.println(bundle2.getString(key));
			return bundle2.getString(key);
		} catch (MissingResourceException e) {
			return " ";
		}
	}
}
