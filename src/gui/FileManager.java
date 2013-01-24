package gui;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

class ConfigFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".mds");
    }
    
    public String getDescription() {
        return "MegaDrum full config files (*.mds)";
    }
}

class SysexFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".syx");
    }
    
    public String getDescription() {
        return "Sysex files (*.syx)";
    }
}

class BinFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bin");
    }
    
    public String getDescription() {
        return "Firmware files (*.bin)";
    }
}

class BinFileFilterSTM32a extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || (f.getName().toLowerCase().endsWith(".bin") && (f.getName().toLowerCase().contains("megadrumstm32a_")));
    }
    
    public String getDescription() {
        return "STM32a firmware files (*.bin)";
    }
}

class BinFileFilterSTM32b extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || (f.getName().toLowerCase().endsWith(".bin") && (f.getName().toLowerCase().contains("megadrumstm32b_")));
    }
    
    public String getDescription() {
        return "STM32b firmware files (*.bin)";
    }
}

public class FileManager {
	private JFileChooser fileChooser;
	private JFrame parent;
	private File file = null;
	private ConfigFileFilter configFileFilter;
	private SysexFileFilter sysexFileFilter;
	//private BinFileFilter binFileFilter;
	//private Properties prop;
	private PropertiesConfiguration fullConfig;
	//private CombinedConfiguration cc;
	
	public FileManager (JFrame parentFrame) {
		fileChooser = new JFileChooser();
		parent = parentFrame;
		configFileFilter = new ConfigFileFilter();
		sysexFileFilter = new SysexFileFilter();
		//binFileFilter = new BinFileFilter();
		//prop = new Properties();
		//fullConfig = new PropertiesConfiguration();
	}

	public void saveConfigFull(ConfigFull config, File file) {
		if (file.exists()) {
			file.delete();
		}
		fullConfig = new PropertiesConfiguration();
		PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(fullConfig);
		layout.setHeaderComment("MegaDrum config");
		fullConfig.setLayout(layout);
		config.copyToPropertiesConfiguration(fullConfig,layout);

		try {
			fullConfig.save(file);
		} catch (ConfigurationException e) {
			//e.printStackTrace();
			Utils.show_error("Error saving all settings to:\n" +
					file.getAbsolutePath()+"\n"+"("+e.getMessage()+")");
		}
		
	}
	
	public void save_all(ConfigFull config, ConfigOptions options) {
		int returnVal;
		fileChooser.setFileFilter(configFileFilter);
		if (!options.lastFullPathConfig.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathConfig));
		}
		returnVal = fileChooser.showSaveDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (!(file.getName().toLowerCase().endsWith(".mds"))) {
				file = new File(file.getAbsolutePath() + ".mds");
			}
			options.lastFullPathConfig = file.getAbsolutePath();
			if (file.exists()) {
				file.delete();
			}
			saveConfigFull(config, file);
		}
	}

	public void loadConfigFull(ConfigFull config, File file) {
		fullConfig = new PropertiesConfiguration();
		try {
			fullConfig.load(file);
			try {
				config.copyFromPropertiesConfiguration(fullConfig);
			} catch (ConversionException e ) {
				Utils.show_error("Error parsing settings from:\n" +
						file.getAbsolutePath()+"\n"+"("+e.getMessage()+")");
			}

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utils.show_error("Error loading all settings from:\n" +
					file.getAbsolutePath()+"\n"+"("+e.getMessage()+")");
		}
	}	

	public void load_all(ConfigFull config, ConfigOptions options) {
		int returnVal;
		if (!options.lastFullPathConfig.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathConfig));
		}
		fileChooser.setFileFilter(configFileFilter);
		returnVal = fileChooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			options.lastFullPathConfig = file.getAbsolutePath();
			if (file.exists()) {
				loadConfigFull(config,file);
			}
		}
	}
	
	public File selectFirmwareFile(ConfigOptions options) {
		int returnVal;
		file = null;
		if (!options.lastFullPathFirmware.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathFirmware));
		}
		fileChooser.setFileFilter(new BinFileFilter());
		if (options.mcuType == 4) {
			fileChooser.setFileFilter(new BinFileFilterSTM32a());
		}
		if (options.mcuType == 5) {
			fileChooser.setFileFilter(new BinFileFilterSTM32b());
		}
		returnVal = fileChooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			options.lastFullPathFirmware = file.getAbsolutePath();
		}
		return file;
	}

	public void loadAllSilent(ConfigFull config, ConfigOptions options) {
		file = new File(options.lastFullPathConfig);
		options.lastFullPathConfig = file.getAbsolutePath();
		if (file.exists()) {
			loadConfigFull(config,file);
		}
	}

	public ConfigOptions loadLastOptions(ConfigOptions config) {
		file = new File(Constants.MD_MANAGER_CONFIG);
		if (file.exists()) {
			PropertiesConfiguration prop = new PropertiesConfiguration();
			try {
				prop.load(file);
				try {
					config.copyFromPropertiesConfiguration(prop);
				} catch (ConversionException e) {
					// TODO Auto-generated catch block
					Utils.show_error("Error parsing MegaDrum options from file:\n" +
							file.getAbsolutePath() + "\n"
							+"(" + e.getMessage() + ")");
				}
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				saveLastOptions(config);
			}
		} else {
			saveLastOptions(config);
		}
		return config;
	}
	
	public void saveLastOptions(ConfigOptions config) {
		file = new File(Constants.MD_MANAGER_CONFIG);
		PropertiesConfiguration prop = new PropertiesConfiguration();
		config.copyToPropertiesConfiguration(prop);
		try {
			prop.save(file);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utils.show_error("Error saving MegaDrum options to file:\n" +
					file.getAbsolutePath() + "\n"
					+"(" + e.getMessage() + ")");
		}		
		
	}
		
	public void saveSysex(byte [] sysex, ConfigOptions options) {
		int returnVal;
		fileChooser.setFileFilter(sysexFileFilter);
		if (!options.lastFullPathSysex.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathSysex));
		}
		returnVal = fileChooser.showSaveDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (!(file.getName().toLowerCase().endsWith(".syx"))) {
				file = new File(file.getAbsolutePath() + ".syx");
			}
			options.lastFullPathSysex = file.getAbsolutePath();
			if (file.exists()) {
				file.delete();
			}
			try {
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(sysex);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Utils.show_error("Error saving Sysex to file:\n" +
						file.getAbsolutePath() + "\n"
						+"(" + e.getMessage() + ")");

			}
		}
	}

	public void loadSysex(byte [] sysex, ConfigOptions options) {
		int returnVal;
		if (!options.lastFullPathSysex.equals("")) {
			fileChooser.setCurrentDirectory(new File(options.lastFullPathSysex));
		}
		fileChooser.setFileFilter(sysexFileFilter);
		returnVal = fileChooser.showOpenDialog(parent);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			options.lastFullPathSysex = file.getAbsolutePath();
			if (file.exists()) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					fis.read(sysex);
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					Utils.show_error("Error loading Sysex from file:\n" +
							file.getAbsolutePath() + "\n"
							+"(" + e.getMessage() + ")");
				}
			}
		}
	}
}
