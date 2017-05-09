package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Options extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4800659190252147998L;

	private final JPanel contentPanel = new JPanel();
	
	private Midi_handler midi_handler;
	private JCheckBox chckbx_samePort;
	private JComboBox<String> comboBox_MIDI_In;
	private JComboBox<String> comboBox_MIDI_Out;
	private JComboBox<Integer> comboBox_chainId;
	private JCheckBox checkBox_Thru;
	public JComboBox<String> comboBox_MIDI_Thru;
	private JCheckBox checkBox_saveOnClose;
	private ConfigOptions configOptions;
	private JCheckBox checkBox_autoOpen;
	private JSpinner spinner_sysexDelay;
	private JComboBox<String> comboBox_lookAndFeel;
	private LookAndFeelInfo[] lookAndFeelArray;
	public LookAndFeelInfo lookAndFeel;
	public int midi_port_out;
	public int midi_port_in;
	public boolean config_applied;
	//private JFrame topFrame;
	//private Options thisFrame;

	/**
	 * Create the dialog.
	 */
	public Options(Midi_handler mh) {
		midi_handler = mh;
		lookAndFeelArray = UIManager.getInstalledLookAndFeels();
		configOptions = new ConfigOptions();
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setName("dialog_options");
		setTitle("Options");
		setBounds(100, 100, 542, 347);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("276px:grow"),}));
		
		JPanel panel_midi = new JPanel();
		panel_midi.setBorder(new TitledBorder(null, "MIDI Ports", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel_midi, "1, 1, fill, fill");
		panel_midi.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("left:max(117dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default"),}));

		JPanel panel_misc = new JPanel();
		panel_misc.setBorder(new TitledBorder(null, "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel_misc, "3, 1, fill, fill");
		panel_misc.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:default"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("left:default:grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),
				RowSpec.decode("fill:default"),}));
		
		comboBox_MIDI_Out = new JComboBox<String>();
		comboBox_MIDI_Out.setMaximumRowCount(16);
		comboBox_MIDI_Out.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBox_MIDI_Out.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				midi_port_out = comboBox_MIDI_Out.getSelectedIndex();
			}
		});
		
		JLabel lblUseSameInout = new JLabel("Use same In/Out");
		lblUseSameInout.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblUseSameInout, "1, 1");
		
		chckbx_samePort = new JCheckBox("");
		chckbx_samePort.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_MIDI_Out.setEnabled(!chckbx_samePort.isSelected());
			}
		});
		panel_midi.add(chckbx_samePort, "3, 1, default, fill");
		chckbx_samePort.setHorizontalTextPosition(SwingConstants.LEADING);
		chckbx_samePort.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel_midi.add(comboBox_MIDI_Out, "3, 3, fill, fill");
		
		comboBox_MIDI_In = new JComboBox<String>();
		comboBox_MIDI_In.setMaximumRowCount(16);
		comboBox_MIDI_In.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBox_MIDI_In.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				midi_port_in = comboBox_MIDI_In.getSelectedIndex();
			}
		});
		panel_midi.add(comboBox_MIDI_In, "3, 2, fill, fill");
		
		JLabel lblMidiIn = new JLabel("Midi In");
		lblMidiIn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMidiIn, "1, 2, default, center");
		
		JLabel lblMidiOut = new JLabel("Midi Out");
		lblMidiOut.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMidiOut, "1, 3, default, center");
		
		JLabel lblMegadrumChainid = new JLabel("MegaDrum ChainID");
		lblMegadrumChainid.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMegadrumChainid, "1, 4");
		
		comboBox_chainId = new JComboBox<Integer>();
		for (int i = 0;i<4;i++) {
			comboBox_chainId.addItem(i);
		}
		comboBox_chainId.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_midi.add(comboBox_chainId, "3, 4, fill, default");
		
		JLabel lblEnableMidiThru = new JLabel("Enable MIDI Thru");
		lblEnableMidiThru.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblEnableMidiThru, "1, 5");
		
		checkBox_Thru = new JCheckBox("");
		checkBox_Thru.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox_MIDI_Thru.setEnabled(checkBox_Thru.isSelected());
			}
		});
		checkBox_Thru.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_midi.add(checkBox_Thru, "3, 5, default, fill");
		
		JLabel lblMidiThru = new JLabel("Midi Thru");
		lblMidiThru.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblMidiThru, "1, 6, right, default");
		
		comboBox_MIDI_Thru = new JComboBox<String>();
		comboBox_MIDI_Thru.setMaximumRowCount(16);
		comboBox_MIDI_Thru.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_midi.add(comboBox_MIDI_Thru, "3, 6, fill, fill");
		
		JLabel lblSaveOptionsOn = new JLabel("Save Options on Exit");
		lblSaveOptionsOn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblSaveOptionsOn, "1, 1");
		
		checkBox_saveOnClose = new JCheckBox("");
		checkBox_saveOnClose.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				checkBox_autoOpen.setEnabled(checkBox_saveOnClose.isSelected());
			}
		});
		checkBox_saveOnClose.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_misc.add(checkBox_saveOnClose, "3, 1");
		
		JLabel lblLooknfeel = new JLabel("LookAndFeel");
		lblLooknfeel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblLooknfeel, "1, 2");
		
		comboBox_lookAndFeel = new JComboBox<String>();
		comboBox_lookAndFeel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBox_lookAndFeel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
			        for (UIManager.LookAndFeelInfo lf : lookAndFeelArray) {
			        	if (lf.getName().equals(comboBox_lookAndFeel.getSelectedItem().toString())){
			        		lookAndFeel = lf;
			        		firePropertyChange("UIchanged", false, true);
			        	}
			        }
				}
			}
		});
        for (UIManager.LookAndFeelInfo lf : lookAndFeelArray) {
        	comboBox_lookAndFeel.addItem(lf.getName());
        	/*
            System.out.println("***"
                + " " + lf.getName()
                + " " + lf.getClassName()
                + " " + uid.size() + " entries");
            */
        }

		panel_misc.add(comboBox_lookAndFeel, "3, 2, fill, default");
		
		JLabel lblOpenOnStartup = new JLabel("Init Ports on Startup");
		lblOpenOnStartup.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblOpenOnStartup, "1, 7");
		
		checkBox_autoOpen = new JCheckBox("");
		checkBox_autoOpen.setHorizontalTextPosition(SwingConstants.LEADING);
		panel_midi.add(checkBox_autoOpen, "3, 7");
		
		JLabel lblSysexDelay = new JLabel("SysEx delay");
		lblSysexDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(lblSysexDelay, "1, 8");
		
		JPanel panel = new JPanel();
		panel_midi.add(panel, "3, 8, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("46px"),},
			new RowSpec[] {
				RowSpec.decode("fill:default"),}));
		
		spinner_sysexDelay = new JSpinner();
		spinner_sysexDelay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner_sysexDelay.setModel(new SpinnerNumberModel(10, 10, 1000, 1));
		panel.add(spinner_sysexDelay, "1, 1, fill, fill");
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblMs, "3, 1, left, center");
		
		JButton btnRescanMidiPorts = new JButton("Rescan MIDI ports");
		btnRescanMidiPorts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rescanMidiPorts();
			}
		});
		btnRescanMidiPorts.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_midi.add(btnRescanMidiPorts, "3, 11, default, fill");
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						config_applied = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void updateConfig() {
		configOptions.useSamePort = chckbx_samePort.isSelected();
		configOptions.MidiInName = (String)comboBox_MIDI_In.getSelectedItem();
		if (chckbx_samePort.isSelected()) {
			configOptions.MidiOutName = (String)comboBox_MIDI_In.getSelectedItem();			
		} else {
			configOptions.MidiOutName = (String)comboBox_MIDI_Out.getSelectedItem();
		}
		configOptions.chainId = comboBox_chainId.getSelectedIndex();
		configOptions.MidiThruName = (String)comboBox_MIDI_Thru.getSelectedItem();
		configOptions.useThruPort = checkBox_Thru.isSelected();
		configOptions.saveOnExit = checkBox_saveOnClose.isSelected();
		configOptions.autoOpenPorts = checkBox_autoOpen.isSelected();
		configOptions.sysexDelay = (Integer)spinner_sysexDelay.getValue();
		configOptions.LookAndFeelName = comboBox_lookAndFeel.getSelectedItem().toString();
	}
	
	private void updateControls() {
		chckbx_samePort.setSelected(configOptions.useSamePort);
		comboBox_MIDI_In.setSelectedItem(configOptions.MidiInName);
		comboBox_MIDI_Out.setSelectedItem(configOptions.MidiOutName);
		comboBox_chainId.setSelectedIndex(configOptions.chainId);
		comboBox_MIDI_Thru.setSelectedItem(configOptions.MidiThruName);
		comboBox_MIDI_Out.setEnabled(!configOptions.useSamePort);
		comboBox_MIDI_Thru.setEnabled(configOptions.useThruPort);
		checkBox_Thru.setSelected(configOptions.useThruPort);
		checkBox_saveOnClose.setSelected(configOptions.saveOnExit);
		checkBox_autoOpen.setSelected(configOptions.autoOpenPorts);
		spinner_sysexDelay.setValue(configOptions.sysexDelay);
		comboBox_lookAndFeel.setSelectedItem(configOptions.LookAndFeelName);
	}
	
	public void loadOptionsFrom(ConfigOptions options) {
		configOptions = options;
		updateControls();
	}
	
	public void saveOptionsTo(ConfigOptions options) {
		updateConfig();
	}
	
	public void fillInPorts(String[] list) {
		comboBox_MIDI_In.removeAllItems();
		for (String string : list) {
			comboBox_MIDI_In.addItem(string);
		}		
	}

	public void fillOutPorts(String[] list) {
		comboBox_MIDI_Out.removeAllItems();	
		for (String string : list) {
			comboBox_MIDI_Out.addItem(string);
		}		
	}

	public void fillThruPorts(String[] list) {
		comboBox_MIDI_Thru.removeAllItems();
		for (String string : list) {
			comboBox_MIDI_Thru.addItem(string);
		}		
	}
	
	public void rescanMidiPorts() {
		//midi_handler.closeAllPorts();
		//midi_handler.init();
		//midi_handler.initPorts();
		fillInPorts(midi_handler.getMidiInList());
		fillOutPorts(midi_handler.getMidiOutList());
		fillThruPorts(midi_handler.getMidiOutList());	
		updateControls();
	}

}
