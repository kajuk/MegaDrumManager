package gui;

import java.io.IOException;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ConfigGlobalMisc {
	
	public boolean changed; 
	
	public int lcd_contrast = 50;
	public int inputs_count = 18;
	public boolean custom_names_en = false;
	public boolean config_names_en = false;

	public ConfigGlobalMisc (){
	}
	
	public void copyToPropertiesConfiguration(PropertiesConfiguration prop, PropertiesConfigurationLayout layout, String prefix) {
		layout.setComment(prefix+"lcd_contrast", "Global Misc settings");
		prop.setProperty(prefix+"lcd_contrast", lcd_contrast);
		prop.setProperty(prefix+"inputs_count", inputs_count);
		prop.setProperty(prefix+"custom_names_en", custom_names_en);
		prop.setProperty(prefix+"config_names_en", config_names_en);
	}

	public void copyFromPropertiesConfiguration(PropertiesConfiguration prop, String prefix) {
		lcd_contrast = Utils.validateInt(prop.getInt(prefix+"lcd_contrast", lcd_contrast),1,100,lcd_contrast);
		inputs_count = Utils.validateInt(prop.getInt(prefix+"inputs_count", inputs_count),18,56,inputs_count);
		custom_names_en = prop.getBoolean(prefix+"custom_names_en", custom_names_en);
		config_names_en = prop.getBoolean(prefix+"config_names_en", config_names_en);
	}
}
