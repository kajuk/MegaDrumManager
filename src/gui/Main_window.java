package gui;

import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIDefaults;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
//import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SpringLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JProgressBar;
import javax.swing.JCheckBoxMenuItem;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.WindowFocusListener;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import com.jgoodies.forms.layout.Sizes;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

class CheckBoxWithState extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4271346265812705819L;

	public CheckBoxWithState(String string) {
		super(string);
		//setForeground(Color.red);
	}
	
	public void setSyncState(int state) {
		switch (state) {
			case Constants.SYNC_STATE_UNKNOWN:
				setForeground(Color.BLUE);
				break;
			case Constants.SYNC_STATE_SYNCED:
				setForeground(Color.BLACK);
				break;
			case Constants.SYNC_STATE_NOT_SYNCED:
				setForeground(Color.RED);
				break;
			default:
				setForeground(Color.BLACK);
				break;
		}
	}
	
	public void setSyncNotSync(boolean synced) {
		if (synced) {
			setSyncState(Constants.SYNC_STATE_SYNCED);
		} else {
			setSyncState(Constants.SYNC_STATE_NOT_SYNCED);
		}
	}
}

public class Main_window {

	private JFrame frmMegadrummanager;
	private JScrollPane main_scroller;
	private JPanel global_panel;
	private JPanel panel_main;
	private JPanel panel_top;
	private Options dialog_options;
	private Upgrade upgradeDialog;
	private Midi_handler midi_handler;
	private ControlsMisc controlsMisc;
	private ControlsPedal controlsPedal;
	private ControlsPads controlsPads;
	private ControlsPadsExtra controlsPadsExtra;
	private PanelMidiLog panelMidiLog;
	private FrameDetached [] framesDetached;
	private JPanel [] controlsPanels;
	private ViewMenu [] viewMenus;
	private ConfigFull configFull;
	private ConfigFull moduleConfigFull;
	private ConfigFull [] fullConfigs;
	private String [] configFileNames;
	protected ConfigOptions configOptions;
	private FileManager fileManager;
	//private int chainId;
	//private JMenuItem menuItem;
	private JMenu mnView;
	private JProgressBar progressBar;
	private JComboBoxCustom comboBox_inputsCount;
	private JSpinner spinnerLCDcontrast;
	private int spinnerLCDEventDisabled = 0;
	private int chckbxCustomPadsNamesEventDisabled = 0;
	private int chckbxConfignamesenEventDisabled = 0;
	private int chckbxMidi2ForSysexEventDisabled = 0;
	private boolean resizeWindow = true;
	private JToggleButton tglbtnMidi;
	private LabelWithState lblVersion, commsStateLabel, lblCfgSlotsNr, lblCfgCurrent, lblMCU;
	private LabelWithState lblMidi, lblFirmwareVer, lblMcu, lblConfigSlots, lblCurrent, lblInputs;
	private LabelWithState lblLCDcontrast, lblConfigName;
	private JToggleButton tglbtnLiveUpdates;
	private JComboBox<String> comboBoxCfg;
	private int comboBoxCfgNoActions = 0;
	private JCheckBox checkBoxAutoResize;
	private CheckBoxWithState chckbxConfignamesen;
	private CheckBoxWithState chckbxCustomPadsNames;
	private CheckBoxWithState chckbxMidi2ForSysex;
	private JButton btnSaveToSlot;
	private JButton btnLoadFromSlot;
	private JPopupMenu popupMenuSaveToSlot;
	private JPopupMenu popupMenuLoadFromSlot;
	private JMenuItem popupMenuItemsSaveToSlot[];
	private JMenuItem menuItemsSaveToSlot[];
	private JMenuItem popupMenuItemsLoadFromSlot[];
	private JMenuItem menuItemsLoadFromSlot[];
	private JMenu mntmSaveToMd;
	private JMenu mntmLoadFromMd;
	private JCheckBox checkBoxSyncronized;
	ActionListener saveToSlotAction;
	ActionListener loadFromSlotAction;
	private boolean LookAndFeelChanged = false;
	private boolean sendSysexEnabled = true;
	private boolean compareSysexToConfigIsOn = false;
	private boolean compareSysexToConfigLast = false;
	private boolean sysexTimedOut = false;
	//private boolean compareResultTimeoutsCombined = false;
	private boolean withReportInTask;
	private boolean delayedSaveToSlot = false;
	private boolean versionWarningAlreadyShown = false;
	private int delayedSaveToSlotNumber;
	private int compareResultCombined;
	private Timer sysexWaitTimer;
	private JTextField configNameTextField;
	private JLabel lblOk;
	//private int configPointer = 0;
	//private String [] configsStrings;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_window window = new Main_window();
					window.frmMegadrummanager.setVisible(true);
					window.dialog_options.setVisible(false);
					ToolTipManager.sharedInstance().setDismissDelay(20000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_window() {
		
		initialize();
		loadConfig();
		LookAndFeelChanged = true;
		resizeMainWindow();
	}
	
	private void open_options_dialog() {
		
		dialog_options.config_applied = false;
		dialog_options.setVisible(true);
		if (dialog_options.config_applied) {
			dialog_options.saveOptionsTo(configOptions);
			midi_handler.initPorts();
		}
		toggleMidiOpenButton();
	}
	
//	public static void show_error(String msg) {
//		JOptionPane.showMessageDialog(null,
//			    msg,
//			    "Error",
//			    JOptionPane.ERROR_MESSAGE);
//	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		configFull = new ConfigFull();
		moduleConfigFull = new ConfigFull();
		frmMegadrummanager = new JFrame();
		frmMegadrummanager.setIconImage(Toolkit.getDefaultToolkit().getImage(Main_window.class.getResource("/icons/megadrum-manager128.png")));
		frmMegadrummanager.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				saveAndExit();
			}
		});
		frmMegadrummanager.setTitle("MegaDrumManager");
		frmMegadrummanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		configOptions = new ConfigOptions(); // default options loaded with new
		fullConfigs = new ConfigFull[Constants.CONFIGS_COUNT];
		configFileNames = new String[Constants.CONFIGS_COUNT];
		for (Integer i = 1;i<=Constants.CONFIGS_COUNT;i++) {
			fullConfigs[i-1] = new ConfigFull();
			configFileNames[i-1] = new String();
		}
		
		fileManager = new FileManager(frmMegadrummanager);
		midi_handler = new Midi_handler(configOptions);
		dialog_options = new Options(midi_handler);
		SwingUtilities.updateComponentTreeUI(dialog_options);
		dialog_options.pack();
		dialog_options.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("UIchanged")) {
	        		try {
						UIManager.setLookAndFeel(dialog_options.lookAndFeel.getClassName());
						LookAndFeelChanged = true;
					} catch (Exception e) {
						Utils.show_error("Error setting LookAndFeel. Exiting.\n" +
								"(" + e.getMessage() + ")");
						System.exit(1);
					}
					resizeMainWindow();
					SwingUtilities.updateComponentTreeUI(dialog_options);
					dialog_options.pack();
				}
			}
		});

		
		upgradeDialog = new Upgrade(midi_handler, fileManager);
		midi_handler.chainId = 0;
		midi_handler.addMidiEventListener(new MidiEventListener() {
			@Override
			public void midiEventOccurred(MidiEvent evt) {
				if (!upgradeDialog.isVisible()) {
					sendSysexEnabled = false;
					midi_handler.getMidi();
					if (midi_handler.sysexReceived) {
						midi_handler.sysexReceived = false;
						if (compareSysexToConfigIsOn) {
							compareSysexToConfig(midi_handler.bufferIn);
						} else {
							decodeSysex(midi_handler.bufferIn);						
						}
					} else if (midi_handler.bufferIn != null) {
						decodeShortMidi(midi_handler.bufferIn);
					}
					midi_handler.bufferIn = null;
					sendSysexEnabled = true;
				}
			}
		});		
		
		JMenuBar menuBar = new JMenuBar();
		frmMegadrummanager.setJMenuBar(menuBar);
		
		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		JMenu mnLoad = new JMenu("All settings");
		mnMain.add(mnLoad);
		
		JMenuItem menuItemLoadFromMd = new JMenuItem("Get from MD");
		menuItemLoadFromMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				getAll();
			}
		});
		mnLoad.add(menuItemLoadFromMd);
		
		JMenuItem mntmSendToMd = new JMenuItem("Send to MD");
		mntmSendToMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				sendAll();
			}
		});
		mnLoad.add(mntmSendToMd);
		
		JMenuItem mntmLoadFromFile = new JMenuItem("Load from file");
		mntmLoadFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				load_all();
			}
		});
		mnLoad.add(mntmLoadFromFile);
		
		JMenuItem mntmSaveToFile = new JMenuItem("Save to file");
		mntmSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save_all();
			}
		});
		mnLoad.add(mntmSaveToFile);

		mntmLoadFromMd = new JMenu("Load from MD slot:");
		mnLoad.add(mntmLoadFromMd);

		mntmSaveToMd = new JMenu("Save to MD slot:");
		mnLoad.add(mntmSaveToMd);
		
		JMenu mnMiscSettings = new JMenu("Misc settings");
		mnMain.add(mnMiscSettings);
		
		JMenuItem mntmLoadFromMd_1 = new JMenuItem("Load from MD");
		mntmLoadFromMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getMisc();
			}
		});
		mnMiscSettings.add(mntmLoadFromMd_1);
		
		JMenuItem mntmSendToMd_1 = new JMenuItem("Send to MD");
		mntmSendToMd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendMisc(true);
			}
		});
		mnMiscSettings.add(mntmSendToMd_1);
		
		JMenu mnHihatPedalSettings = new JMenu("HiHat pedal settings");
		mnHihatPedalSettings.setToolTipText("");
		mnMain.add(mnHihatPedalSettings);
		
		JMenuItem mntmLoadFromMd_2 = new JMenuItem("Load from MD");
		mntmLoadFromMd_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				getPedal();
			}
		});
		mnHihatPedalSettings.add(mntmLoadFromMd_2);
		
		JMenuItem mntmSendToMd_2 = new JMenuItem("Send to MD");
		mntmSendToMd_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				sendPedal(true);
			}
		});
		mnHihatPedalSettings.add(mntmSendToMd_2);
		
		JMenu mnAllPadsSettings = new JMenu("All pads settings");
		mnMain.add(mnAllPadsSettings);
		
		JMenuItem mntmLoadFromMd_3 = new JMenuItem("Load from MD");
		mntmLoadFromMd_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				getAllPads();
			}
		});
		mnAllPadsSettings.add(mntmLoadFromMd_3);
		
		JMenuItem mntmSendToMd_3 = new JMenuItem("Send to MD");
		mntmSendToMd_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				sendAllPads(true);
			}
		});
		mnAllPadsSettings.add(mntmSendToMd_3);
		
		JMenu mnSelectedPadSettings = new JMenu("Selected pad settings");
		mnMain.add(mnSelectedPadSettings);
		
		JMenuItem mntmLoadFromMd_4 = new JMenuItem("Load from MD");
		mntmLoadFromMd_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				getPad(controlsPads.getPadPointer());
			}
		});
		mnSelectedPadSettings.add(mntmLoadFromMd_4);
		
		JMenuItem mntmSendToMd_4 = new JMenuItem("Send to MD");
		mntmSendToMd_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				sendPad(controlsPads.getPadPointer(), true);
			}
		});
		mnSelectedPadSettings.add(mntmSendToMd_4);
		
		JMenu mnCustomCurves = new JMenu("Custom curves");
		mnMain.add(mnCustomCurves);
		
		JMenuItem mntmLoadFromMd_5 = new JMenuItem("Load from MD");
		mntmLoadFromMd_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getAllCurves();
			}
		});
		mnCustomCurves.add(mntmLoadFromMd_5);
		
		JMenuItem mntmSendToMd_5 = new JMenuItem("Send to MD");
		mntmSendToMd_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendAllCurves(true);
			}
		});
		mnCustomCurves.add(mntmSendToMd_5);
		
		JSeparator separator_2 = new JSeparator();
		mnMain.add(separator_2);
		
		JMenuItem mntmFirmwareUpgrade = new JMenuItem("Firmware upgrade");
		mntmFirmwareUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				upgradeDialog.setConfigOptions(configOptions);
				midi_handler.getMidiBlocked = true;
				upgradeDialog.reOpenMidi = false;
				upgradeDialog.setVisible(true);
				midi_handler.getMidiBlocked = false;
				if (upgradeDialog.reOpenMidi) {
					toggleMidiOpenButton();
				}
			}
		});
		mnMain.add(mntmFirmwareUpgrade);
		
		JSeparator separator_1 = new JSeparator();
		mnMain.add(separator_1);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open_options_dialog();
			}
		});
		mnMain.add(mntmOptions);
		
		JSeparator separator = new JSeparator();
		mnMain.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAndExit();
			}
		});
		mnMain.add(mntmExit);
		
		mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane jp = JOptionPane();
				//jp.setFont(new Font("Tahoma", Font.PLAIN, 10));
				//jp.showMessageDialog(null,
				if (System.getProperty("os.name").startsWith("Mac")) {
					JOptionPane.showMessageDialog(null,
						    Constants.HELP_ABOUT+Constants.HELP_ABOUT_MMJ,
						    "About",
						    JOptionPane.INFORMATION_MESSAGE);
					
				} else {
					JOptionPane.showMessageDialog(null,
						    "<html><font size=5>"+Constants.HELP_ABOUT+"</font></html>",
						    "About",
						    JOptionPane.INFORMATION_MESSAGE);					
				}
			}
		});
		mnHelp.add(mntmAbout);
		
		
		panel_main = new JPanel();
		panel_main.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("pref:grow"),}));
//		FlowLayout flowLayout = (FlowLayout) panel_main.getLayout();
//		flowLayout.setAlignOnBaseline(true);
		
		controlsMisc = new ControlsMisc(configFull, moduleConfigFull, this);
		controlsMisc.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_MISC_SIZE];
				Utils.copyConfigMiscToSysex(configFull.configMisc, sysex, configOptions.chainId);
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsMisc.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_MISC_SIZE];
				Utils.copyConfigMiscToSysex(configFull.configMisc, sysex, configOptions.chainId);
				fileManager.loadSysex(sysex, configOptions);
				Utils.copySysexToConfigMisc(sysex, configFull.configMisc);
				controlsMisc.updateControls();
			}
		});
		controlsMisc.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("valueChanged")) {
						sysexTimedOut = false;
						sendMisc(true);
					}
				}
				if (arg0.getPropertyName().equals("octaveValueChanged")) {
					controlsPedal.updateControls();
					controlsPads.updateControls();
				}
			}
		});
		controlsMisc.setBorder(new TitledBorder(null, "Misc", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(controlsMisc, "1, 2, default, top");
		controlsMisc.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getMisc();
			}
		});
		controlsMisc.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendMisc(true);
			}
		});
		
		controlsPedal = new ControlsPedal(configFull, moduleConfigFull);
		controlsPedal.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(configFull.configPedal, sysex, configOptions.chainId);
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsPedal.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(configFull.configPedal, sysex, configOptions.chainId);
				fileManager.loadSysex(sysex, configOptions);
				Utils.copySysexToConfigPedal(sysex, configFull.configPedal);
				controlsPedal.updateControls();
			}
		});
		controlsPedal.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("valueChanged")) {
						sysexTimedOut = false;
						sendPedal(true);
					}
				}
			}
		});
		controlsPedal.setBorder(new TitledBorder(null, "HiHat Pedal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(controlsPedal, "2, 2, default, top");
		controlsPedal.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getPedal();
			}
		});
		controlsPedal.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendPedal(true);
			}
		});
		
		controlsPads = new ControlsPads(configFull, moduleConfigFull);
		controlsPads.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PAD_SIZE];
				byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];
				byte [] sysexPos = new byte[Constants.MD_SYSEX_POS_SIZE];
				int padId = controlsPads.getPadPointer();
				if (padId > 0 ) {
					byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + Constants.MD_SYSEX_POS_SIZE*2];
					Utils.copyConfigPadToSysex(configFull.configPads[padId], sysex, configOptions.chainId, padId);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[i] = sysex[i];
					}
					Utils.copyConfigPadToSysex(configFull.configPads[padId+1], sysex, configOptions.chainId, padId+1);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE + i] = sysex[i];
					}
					Utils.copyConfig3rdToSysex(configFull.config3rds[(padId-1)/2], sysex3rd, configOptions.chainId, (padId-1)/2);
					for (int i = 0; i<sysex3rd.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + i] = sysex3rd[i];
					}
					Utils.copyConfigPosToSysex(configFull.configPos[padId], sysexPos, configOptions.chainId, padId);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + i] = sysexPos[i];
					}
					Utils.copyConfigPosToSysex(configFull.configPos[padId+1], sysexPos, configOptions.chainId, padId+1);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + Constants.MD_SYSEX_POS_SIZE + i] = sysexPos[i];
					}
					fileManager.saveSysex(sysexPad, configOptions);
				} else {
					byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE + Constants.MD_SYSEX_POS_SIZE];
					Utils.copyConfigPadToSysex(configFull.configPads[0], sysex, configOptions.chainId, 0);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[i] = sysex[i];
					}					
					Utils.copyConfigPosToSysex(configFull.configPos[padId], sysexPos, configOptions.chainId, padId);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPad[i + Constants.MD_SYSEX_PAD_SIZE] = sysexPos[i];
					}
					fileManager.saveSysex(sysexPad, configOptions);
				}
			}
		});
		
		controlsPads.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PAD_SIZE];
				byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];
				byte [] sysexPos = new byte[Constants.MD_SYSEX_POS_SIZE];
				int padId = controlsPads.getPadPointer();
				if (padId > 0 ) {
					byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + Constants.MD_SYSEX_POS_SIZE*2];
					Utils.copyConfigPadToSysex(configFull.configPads[padId], sysex, configOptions.chainId, padId);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[i] = sysex[i];
					}
					Utils.copyConfigPadToSysex(configFull.configPads[padId+1], sysex, configOptions.chainId, padId+1);
					for (int i = 0; i<sysex.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE + i] = sysex[i];
					}
					Utils.copyConfig3rdToSysex(configFull.config3rds[(padId-1)/2], sysex3rd, configOptions.chainId, (padId-1)/2);
					for (int i = 0; i<sysex3rd.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + i] = sysex3rd[i];
					}
					Utils.copyConfigPosToSysex(configFull.configPos[padId], sysexPos, configOptions.chainId, padId);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + i] = sysexPos[i];
					}
					Utils.copyConfigPosToSysex(configFull.configPos[padId+1], sysexPos, configOptions.chainId, padId+1);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + Constants.MD_SYSEX_POS_SIZE + i] = sysexPos[i];
					}
					fileManager.loadSysex(sysexPad, configOptions);
					for (int i = 0; i<sysex.length;i++) {
						sysex[i] = sysexPad[i];
					}
					Utils.copySysexToConfigPad(sysex, configFull.configPads[padId]);
					for (int i = 0; i<sysex.length;i++) {
						sysex[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE + i];
					}
					Utils.copySysexToConfigPad(sysex, configFull.configPads[padId+1]);
					for (int i = 0; i<sysex3rd.length;i++) {
						sysex3rd[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + i];
					}
					Utils.copySysexToConfig3rd(sysex3rd, configFull.config3rds[(padId-1)/2]);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPos[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + i];
					}
					Utils.copySysexToConfigPos(sysexPos, configFull.configPos[padId]);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPos[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE*2 + Constants.MD_SYSEX_3RD_SIZE + Constants.MD_SYSEX_POS_SIZE + i];
					}
					Utils.copySysexToConfigPos(sysexPos, configFull.configPos[padId+1]);
					controlsPads.updateControls();
				} else {
					byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE + Constants.MD_SYSEX_POS_SIZE];
					Utils.copyConfigPadToSysex(configFull.configPads[0], sysex, configOptions.chainId, 0);
					System.arraycopy(sysex, 0, sysexPad, 0, sysex.length);
					Utils.copyConfigPosToSysex(configFull.configPos[0], sysexPos, configOptions.chainId, 0);
					System.arraycopy(sysexPos, 0, sysexPad, Constants.MD_SYSEX_PAD_SIZE, sysexPos.length);
					fileManager.loadSysex(sysexPad, configOptions);					
					for (int i = 0; i<sysex.length;i++) {
						sysex[i] = sysexPad[i];
					}
					Utils.copySysexToConfigPad(sysex, configFull.configPads[0]);
					for (int i = 0; i<sysexPos.length;i++) {
						sysexPos[i] = sysexPad[Constants.MD_SYSEX_PAD_SIZE + i];
					}
					Utils.copySysexToConfigPos(sysexPos, configFull.configPos[0]);
					controlsPads.updateControls();
				}
			}
		});
		
		controlsPedal.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(configFull.configPedal, sysex, configOptions.chainId);
				fileManager.loadSysex(sysex, configOptions);
				Utils.copySysexToConfigPedal(sysex, configFull.configPedal);
				controlsPedal.updateControls();
			}
		});

		controlsPads.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("inputsEnabledDisabled")) {
					sysexTimedOut = false;
					sendAllPads(true);
				}
				if ((configOptions != null) && configOptions.interactive && sendSysexEnabled) {
					if (arg0.getPropertyName().equals("headValueChanged")) {
						sysexTimedOut = false;
						sendPadOneZone(controlsPads.getPadPointer(), true);
					}
					if (arg0.getPropertyName().equals("rimValueChanged")) {
						sysexTimedOut = false;
						sendPadOneZone(controlsPads.getPadPointer() + 1, true);
					}
					if (arg0.getPropertyName().equals("thirdZoneValueChanged")) {
						sysexTimedOut = false;
						sendThirdZone(controlsPads.getPadPointer(), true);
					}
				}
			}
		});
		
//		controlsPads.addPropertyChangeListener("resize", new PropertyChangeListener() {
//			public void propertyChange(PropertyChangeEvent arg0) {
//				resizeMainWindow();
//			}
//		});
		controlsPads.getBtnSendall().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				sendAllPads(true);
			}
		});
		controlsPads.getBtnGetall().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				getAllPads();
			}
		});
		controlsPads.setBorder(new TitledBorder(null, "Pads", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(controlsPads, "3, 2, default, top");
		controlsPads.getBtnGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getPad(controlsPads.getPadPointer());
			}
		});
		controlsPads.getBtnSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendPad(controlsPads.getPadPointer(), true);
			}
		});

		global_panel = new JPanel();
		main_scroller = new JScrollPane(global_panel);
		frmMegadrummanager.getContentPane().add(main_scroller);
		
		global_panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("pref:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.PREF_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormSpecs.PREF_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormSpecs.PREF_ROWSPEC,}));
		
//		frmMegadrummanager.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
//				ColumnSpec.decode("pref:grow"),
//				FormSpecs.RELATED_GAP_COLSPEC,
//				FormSpecs.DEFAULT_COLSPEC,},
//			new RowSpec[] {
//				FormSpecs.PREF_ROWSPEC,
//				RowSpec.decode("1dlu"),
//				FormSpecs.PREF_ROWSPEC,
//				RowSpec.decode("1dlu"),
//				FormSpecs.PREF_ROWSPEC,}));
		
		panel_top = new JPanel();
//		frmMegadrummanager.getContentPane().add(panel_top, "1, 1, fill, fill");
		global_panel.add(panel_top, "1, 1, fill, fill");
		
//        mnSaveSimple = new JMenu("");
//		mnCopypadto.setFont(new Font("Segoe UI", Font.PLAIN, 10));
//		
//		mnSaveWithName = new JMenu("CopyHead");
//		mnCopyhead.setFont(new Font("Segoe UI", Font.PLAIN, 10));

		popupMenuLoadFromSlot = new JPopupMenu();
		addPopup(panel_top, popupMenuLoadFromSlot);
		loadFromSlotAction =  new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(((JMenuItem)arg0.getSource()).getName()) - 1;
				midi_handler.requestLoadFromSlot(id);
				if (checkBoxSyncronized.isSelected()) {
					delayMs(configOptions.sysexDelay);
					sysexTimedOut = false;
					getAll();
				}
				setConfigCurrent(id);
			}
		};
		
		popupMenuSaveToSlot = new JPopupMenu();
		addPopup(panel_top, popupMenuSaveToSlot);
		saveToSlotAction =  new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(((JMenuItem)arg0.getSource()).getName()) - 1;
				if (checkBoxSyncronized.isSelected()) {
					delayedSaveToSlot = true;
					delayedSaveToSlotNumber = id;
					sysexTimedOut = false;
					sendAll();
				} else {
					midi_handler.requestSaveToSlot(id);
					setConfigCurrent(id);
				}
//				Timer configGlobalTimer = new Timer();
//				configGlobalTimer.schedule(new TimerTask() {
//					
//					@Override
//					public void run() {
//						getGlobalMisc();
//						Timer configNameTimer = new Timer();
//						configNameTimer.schedule(new TimerTask() {
//							
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								midi_handler.requestConfigConfigName(configFull.configCurrent);
//							}
//						}, 1000);
//					}
//				}, 1000);
			}
		};
		popupMenuItemsSaveToSlot = new JMenuItem[Constants.CONFIG_NAMES_MAX];
		menuItemsSaveToSlot = new JMenuItem[Constants.CONFIG_NAMES_MAX];
		popupMenuItemsLoadFromSlot = new JMenuItem[Constants.CONFIG_NAMES_MAX];
		menuItemsLoadFromSlot = new JMenuItem[Constants.CONFIG_NAMES_MAX];
		clearConfigSlotsNames();
		
		panel_top.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("40dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("80dlu"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("20dlu"),
				ColumnSpec.decode("2dlu"),
				FormSpecs.PREF_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormSpecs.PREF_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.MIN_ROWSPEC,}));
		
		JButton btnGetAll = new JButton("Get All");
		btnGetAll.setToolTipText("Get all settings from MegaDrum");
		btnGetAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGetAll.setMargin(new Insets(0, 1, 0, 1));
		btnGetAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				getAll();
			}
		});
		panel_top.add(btnGetAll, "2, 1");
		
		JButton btnSendAll = new JButton("Send All");
		btnSendAll.setToolTipText("Send all settings to MegaDrum");
		btnSendAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSendAll.setMargin(new Insets(0, 1, 0, 1));
		btnSendAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sysexTimedOut = false;
				sendAll();
			}
		});
		panel_top.add(btnSendAll, "4, 1");
		
		comboBoxCfg = new JComboBox<String>();
		comboBoxCfg.setMaximumRowCount(Constants.CONFIGS_COUNT);
		comboBoxCfg.setToolTipText("<html>Select the current full config to use.<br>\r\nMegaDrumManager can hold up to " + Integer.toString(Constants.CONFIGS_COUNT) + "<br>\r\nfull MegaDrum configs in memory<br>\r\nand you can quickly switch between them here<br>.\r\n</html>");
		comboBoxCfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxCfgNoActions == 0) {
					String text = comboBoxCfg.getSelectedItem().toString();
					if (comboBoxCfg.getSelectedIndex()<0) {
						configOptions.configFileNames[configOptions.lastConfig] = text;
						comboBoxCfg.setModel(new DefaultComboBoxModel<String>(configOptions.configFileNames));
						comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
					}					
				}
			}			
		});
		comboBoxCfg.setModel(new DefaultComboBoxModel<String>(configOptions.configFileNames));
		comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
		comboBoxCfg.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxCfgNoActions == 0) {
					if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					}
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						if (comboBoxCfg.getSelectedIndex()>-1) {
							//copyConfigToLastConfig();
							configOptions.lastConfig = comboBoxCfg.getSelectedIndex();
							fileManager.loadAllSilent(fullConfigs[configOptions.lastConfig], configOptions);
							loadAllFromConfigFull();
							//
						}
					}					
				}
			}
		});
		
		JButton btnSaveAll = new JButton("Save All");
		btnSaveAll.setToolTipText("Save all settings to a file");
		btnSaveAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSaveAll.setMargin(new Insets(0, 1, 0, 1));
		btnSaveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save_all();
			}
		});
		
		JButton btnLoadAll = new JButton("Load All");
		btnLoadAll.setToolTipText("Load settings from a file");
		btnLoadAll.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLoadAll.setMargin(new Insets(0, 1, 0, 1));
		btnLoadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load_all();
			}
		});
		
		btnLoadFromSlot = new JButton("Load from Slot");
		btnLoadFromSlot.setToolTipText("<html>Tell MegaDrum to load settings<br>\r\nfrom one of non-volatile memory slots.<br>\r\n<br>\r\n</html>");
		btnLoadFromSlot.setMargin(new Insets(0, 1, 0, 1));
		btnLoadFromSlot.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLoadFromSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				popupMenuLoadFromSlot.show(btnLoadFromSlot, btnLoadFromSlot.getWidth(),0);
			}
		});

		panel_top.add(btnLoadFromSlot, "6, 1");
		
		btnSaveToSlot = new JButton("Save To Slot");
		btnSaveToSlot.setToolTipText("<html>Tell MegaDrum to save settings<br>\r\nin one of non-volatile memory slots.<br>\r\n<br>\r\nIt also sets MegaDrum to load the saved settings<br>\r\nfrom that slot on power up.\r\n</html>");
		btnSaveToSlot.setMargin(new Insets(0, 1, 0, 1));
		btnSaveToSlot.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSaveToSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				popupMenuSaveToSlot.show(btnSaveToSlot, btnSaveToSlot.getWidth(),0);
			}
		});
		panel_top.add(btnSaveToSlot, "8, 1");
		
		checkBoxSyncronized = new JCheckBox("Synchronized");
		checkBoxSyncronized.setToolTipText("<html>Do GetAll/SendAll after/before Load from/Save to Slot </html>");
		checkBoxSyncronized.setSelected(true);
		checkBoxSyncronized.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel_top.add(checkBoxSyncronized, "10, 1");
		panel_top.add(btnLoadAll, "12, 1");
		panel_top.add(btnSaveAll, "14, 1");
		comboBoxCfg.setEditable(false);
		comboBoxCfg.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel_top.add(comboBoxCfg, "16, 1, fill, default");
		
		JButton btnPrevcfg = new JButton("prevCfg");
		btnPrevcfg.setToolTipText("<html>Switch to previous full MegaDrum config</html>");
		btnPrevcfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (configOptions.lastConfig>0) {
					comboBoxCfg.setSelectedIndex(configOptions.lastConfig - 1);
				}
			}
		});
		
		lblOk = new JLabel("Ok");
		lblOk.setOpaque(true);
		lblOk.setHorizontalAlignment(SwingConstants.CENTER);
		panel_top.add(lblOk, "18, 1");
		btnPrevcfg.setMargin(new Insets(0, 1, 0, 1));
		btnPrevcfg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_top.add(btnPrevcfg, "20, 1");
		
		JButton btnNextcfg = new JButton("nextCfg");
		btnNextcfg.setToolTipText("<html>Switch to next full MegaDrum config</html>");
		btnNextcfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (configOptions.lastConfig<(Constants.CONFIGS_COUNT-1)) {
					comboBoxCfg.setSelectedIndex(configOptions.lastConfig + 1);
				}
			}
		});
		btnNextcfg.setMargin(new Insets(0, 1, 0, 1));
		btnNextcfg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel_top.add(btnNextcfg, "22, 1");
		
		commsStateLabel = new LabelWithState(Constants.SYSEX_OK_TEXT);
		panel_top.add(commsStateLabel, "24, 1");
		commsStateLabel.setOpaque(true);
		commsStateLabel.setBackground(Color.GREEN);
		commsStateLabel.setVisible(false);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		frmMegadrummanager.getContentPane().add(panel, "1, 3, fill, fill");
		global_panel.add(panel, "1, 3, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("64px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.PREF_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("66dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("100dlu"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		lblMidi = new LabelWithState("MIDI :");
		lblMidi.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblMidi, "2, 1");
		
		tglbtnMidi = new JToggleButton("Open MIDI");
		tglbtnMidi.setToolTipText("<html>Open MIDI ports configured in Main->Options</html>");
		panel.add(tglbtnMidi, "4, 1");
		tglbtnMidi.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tglbtnMidi.setMargin(new Insets(1, 1, 1, 1));
		
		lblFirmwareVer = new LabelWithState("FW version:");
		lblFirmwareVer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblFirmwareVer, "6, 1");
		
		lblVersion = new LabelWithState("???????");
		panel.add(lblVersion, "8, 1");
		lblVersion.setToolTipText("<html>Shows the current firmware version<br>\r\nof the connected MegaDrum.\r\n</html>");
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVersion.setOpaque(true);
		lblVersion.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblVersion.setForeground(new Color(0, 0, 0));
		
		lblMcu = new LabelWithState("MCU:");
		lblMcu.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblMcu, "10, 1");
		
		lblMCU = new LabelWithState("Unknown");
		lblMCU.setToolTipText("<html>Shows the MCU type<br>\r\nof the connected MegaDrum.\r\n</html>");
		lblMCU.setHorizontalAlignment(SwingConstants.CENTER);
		lblMCU.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMCU.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblMCU.setForeground(new Color(0, 0, 0));
		panel.add(lblMCU, "12, 1");
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, "16, 1, fill, fill");
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("15dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("15dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblConfigSlots = new LabelWithState("SlotsCount:");
		panel_2.add(lblConfigSlots, "2, 2");
		lblConfigSlots.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		lblCfgSlotsNr = new LabelWithState("??");
		panel_2.add(lblCfgSlotsNr, "4, 2");
		lblCfgSlotsNr.setToolTipText("<html>Shows the total number of Config Slots<br>\r\non the connected MegaDrum.\r\n</html>");
		lblCfgSlotsNr.setHorizontalAlignment(SwingConstants.CENTER);
		lblCfgSlotsNr.setForeground(Color.BLACK);
		lblCfgSlotsNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCfgSlotsNr.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		lblCurrent = new LabelWithState("Current:");
		lblCurrent.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_2.add(lblCurrent, "6, 2");
		
		lblCfgCurrent = new LabelWithState("??");
		lblCfgCurrent.setToolTipText("<html>Shows the current Config Slot<br>\r\non the connected MegaDrum.\r\n</html>");
		lblCfgCurrent.setHorizontalAlignment(SwingConstants.CENTER);
		lblCfgCurrent.setForeground(Color.BLACK);
		lblCfgCurrent.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCfgCurrent.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.add(lblCfgCurrent, "8, 2");
		
		
		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel.add(progressBar, "20, 1");
		progressBar.setVisible(false);
		
		progressBar.setStringPainted(true);
		
		lblInputs = new LabelWithState("Inputs:");
		lblInputs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblInputs, "2, 3");
		
		comboBox_inputsCount = new JComboBoxCustom();
		comboBox_inputsCount.setToolTipText("<html>Select number of inputs used in MegaDrum.<br>\r\n<br>\r\nIt shoud match MaxInputs setting, which is only accessible<br>\r\nfrom MegaDrum menu.\r\n</html>");
		comboBox_inputsCount.setMaximumRowCount(20);
		panel.add(comboBox_inputsCount, "4, 3");
		comboBox_inputsCount.setFont(new Font("Tahoma", Font.PLAIN, 9));
		comboBox_inputsCount.removeAllItems();
		for (int i=0;i<((Constants.MAX_INPUTS-Constants.MIN_INPUTS)/2 + 1);i++) {
			comboBox_inputsCount.addItem((i*2) + Constants.MIN_INPUTS);
		}		
		
		checkBoxAutoResize = new JCheckBox("AutoResize");
		checkBoxAutoResize.setFont(new Font("Tahoma", Font.PLAIN, 9));
		//checkBoxAutoResize.setSelected(true);
		checkBoxAutoResize.setSelected(configOptions.autoResize);
		checkBoxAutoResize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				configOptions.autoResize = checkBoxAutoResize.isSelected();
			}
		});
		panel.add(checkBoxAutoResize, "14, 1");
				
		lblLCDcontrast = new LabelWithState("LCD contrast:");
		lblLCDcontrast.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblLCDcontrast, "6, 3");
		
		spinnerLCDcontrast = new JSpinner();
		spinnerLCDcontrast.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (spinnerLCDEventDisabled > 0 ) {
					spinnerLCDEventDisabled--;
				} else {
					configFull.configGlobalMisc.lcd_contrast = (Integer)spinnerLCDcontrast.getValue();
					if (configOptions.interactive) {
						sysexTimedOut = false;
						sendGlobalMisc(true);
					}
				}
				updateGlobalMiscSyncState();
			}
		});
		spinnerLCDcontrast.setFont(new Font("Tahoma", Font.PLAIN, 9));
		spinnerLCDcontrast.setModel(new SpinnerNumberModel(new Integer(50), new Integer(1), new Integer(100), new Integer(1)));
		panel.add(spinnerLCDcontrast, "8, 3, left, default");
		
		chckbxConfignamesen = new CheckBoxWithState("ConfigNamesEn");
		chckbxConfignamesen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxConfignamesenEventDisabled > 0) {
					chckbxConfignamesenEventDisabled--;
				} else {
					configFull.configGlobalMisc.config_names_en = chckbxConfignamesen.isSelected();					
					if (configOptions.interactive) {
						sysexTimedOut = false;
						sendGlobalMisc(true);
					}
				}
				if (configNameTextField != null) {
					configNameTextField.setEnabled(configFull.configGlobalMisc.config_names_en);						
				}
				updateGlobalMiscSyncState();
			}
		});
		chckbxConfignamesen.setSelected(false);
		chckbxConfignamesen.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(chckbxConfignamesen, "12, 3");
		
		chckbxCustomPadsNames = new CheckBoxWithState("Custom Pads Names");
		chckbxCustomPadsNames.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCustomPadsNamesEventDisabled > 0) {
					chckbxCustomPadsNamesEventDisabled--;
				} else {
					configFull.configGlobalMisc.custom_names_en = chckbxCustomPadsNames.isSelected();
					if (configOptions.interactive) {
						sysexTimedOut = false;
						sendGlobalMisc(true);
					}
				}
				updateGlobalMiscSyncState();
			}
		});
		chckbxCustomPadsNames.setSelected(false);
		chckbxCustomPadsNames.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(chckbxCustomPadsNames, "14, 3");
		
		chckbxMidi2ForSysex = new CheckBoxWithState("ConfigNamesEn");
		chckbxMidi2ForSysex.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxMidi2ForSysexEventDisabled > 0) {
					chckbxMidi2ForSysexEventDisabled--;
				} else {
					configFull.configGlobalMisc.midi2_for_sysex = chckbxMidi2ForSysex.isSelected();
					if (configOptions.interactive) {
						sysexTimedOut = false;
						sendGlobalMisc(true);
					}
				}
				updateGlobalMiscSyncState();
			}
		});
		chckbxMidi2ForSysex.setText("MIDI2 For Sysex Only");
		chckbxMidi2ForSysex.setSelected(false);
		chckbxMidi2ForSysex.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panel.add(chckbxMidi2ForSysex, "18, 1");
		
		
		tglbtnLiveUpdates = new JToggleButton("Live updates");
		tglbtnLiveUpdates.setToolTipText("<html>Enable live settingsupdates.<br>\r\n<br>\r\nWhen enabled, all changes to settings in MegaDrumManager<br>\r\nare sent to MegaDrum upon a change.\r\n</html>");
		tglbtnLiveUpdates.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tglbtnLiveUpdates.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				configOptions.interactive = tglbtnLiveUpdates.isSelected(); 
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, "16, 3, fill, fill");
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblConfigName = new LabelWithState("Slot Name:");
		lblConfigName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_3.add(lblConfigName, "2, 2, right, default");
		
		configNameTextField = new JTextField();
//		configNameTextField.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				configFull.configNameChanged = true;
//				configFull.configConfigNames[configFull.configCurrent].name = configNameTextField.getText();
//			}
//		});
		configNameTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  updateConfigName();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  updateConfigName();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  updateConfigName();
			  }

			  public void updateConfigName() {
				  int id = configFull.configCurrent;
				  configFull.configNameChanged = true;
				  configFull.configConfigNames[id].name = configNameTextField.getText();
				  menuItemsSaveToSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
				  popupMenuItemsSaveToSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
				  menuItemsLoadFromSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
				  popupMenuItemsLoadFromSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);				  
			  }
			});
		configNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_3.add(configNameTextField, "4, 2, fill, default");
		configNameTextField.setColumns(12);
		configNameTextField.setEnabled(false);
		
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "18, 3, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("pref:grow"),
				ColumnSpec.decode("24dlu"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JButton btnGet = new JButton("Get");
		panel_1.add(btnGet, "1, 1");
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getGlobalMisc();
			}
		});
		btnGet.setToolTipText("Get global misc settings (number of inputs, LCD contrast)");
		btnGet.setMargin(new Insets(1, 0, 1, 0));
		btnGet.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		JButton btnSend = new JButton("Send");
		panel_1.add(btnSend, "3, 1");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendGlobalMisc(true);
			}
		});
		btnSend.setToolTipText("Send global misc settings (number of inputs, LCD contrast)");
		btnSend.setMargin(new Insets(1, 0, 1, 0));
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tglbtnLiveUpdates.setMargin(new Insets(1, 1, 1, 1));
		panel.add(tglbtnLiveUpdates, "20, 3, left, default");
		comboBox_inputsCount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_inputsCount.selectEventsDisabled > 0) {
						comboBox_inputsCount.selectEventsDisabled--;
					} else {
			        	configFull.configGlobalMisc.inputs_count = ((comboBox_inputsCount.getSelectedIndex()*2) + Constants.MIN_INPUTS);
			        	updateGlobalMiscControls();
			        	//configFull.resetSyncState();
						if (configOptions.interactive) {
							sysexTimedOut = false;
							sendGlobalMisc(true);
						}
					}
		        }
		        updateGlobalMiscSyncState();
			}
		});
		tglbtnMidi.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					midi_handler.closeAllPorts();
					lblVersion.setText("????????");
					lblVersion.setBackground(UIManager.getColor ("lblVersion.background"));
					lblMCU.setText("Unknown");
					configOptions.mcuType = 0;
					configOptions.version = 0;
					commsStateLabel.setVisible(false);
					lblCfgSlotsNr.setText("??");
					lblCfgCurrent.setText("??");
					clearConfigSlotsNames();
				}
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					showMidiWarningIfNeeded();
					midi_handler.initPorts();
					commsStateLabel.setVisible(true);
					commsStateLabel.setText("MIDI Init Error");
					commsStateLabel.setBackground(Color.RED);
				}
				toggleMidiOpenButton();
			}
		});
//		frmMegadrummanager.getContentPane().add(panel_main, "1, 5, 3, 1, left, fill");
		global_panel.add(panel_main, "1, 5, 3, 1, left, fill");
		
		controlsPadsExtra = new ControlsPadsExtra(configFull, moduleConfigFull);
		controlsPadsExtra.getBtnCurveSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_CURVE_SIZE];
				Utils.copyConfigCurveToSysex(configFull.configCurves[controlsPadsExtra.getCurvePointer()], sysex, configOptions.chainId, controlsPadsExtra.getCurvePointer());
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsPadsExtra.getBtnCurveLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_CURVE_SIZE];
				Utils.copyConfigCurveToSysex(configFull.configCurves[controlsPadsExtra.getCurvePointer()], sysex, configOptions.chainId, controlsPadsExtra.getCurvePointer());
				fileManager.loadSysex(sysex, configOptions);
				Utils.copySysexToConfigCurve(sysex, configFull.configCurves[controlsPadsExtra.getCurvePointer()]);
				controlsPadsExtra.updateControls();
			}
		});
/*
 		controlsPedal.getBtnSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(fullConfigs[configOptions.lastConfig].configPedal, sysex, configOptions.chainId);
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsPedal.getBtnLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(fullConfigs[configOptions.lastConfig].configPedal, sysex, configOptions.chainId);
				fileManager.loadSysex(sysex, configOptions);
				Utils.copySysexToConfigPedal(sysex, fullConfigs[configOptions.lastConfig].configPedal);
				controlsPedal.updateControls();
			}
		});

 */
		controlsPadsExtra.getButton_customNameSave().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_CUSTOM_NAME_SIZE];
				Utils.copyConfigCustomNameToSysex(configFull.configCustomNames[controlsPadsExtra.getCustomNamePointer()], sysex, configOptions.chainId, controlsPadsExtra.getCustomNamePointer());
				fileManager.saveSysex(sysex, configOptions);
			}
		});
		controlsPadsExtra.getButton_customNameLoad().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte [] sysex = new byte[Constants.MD_SYSEX_CUSTOM_NAME_SIZE];
				Utils.copyConfigCustomNameToSysex(configFull.configCustomNames[controlsPadsExtra.getCustomNamePointer()], sysex, configOptions.chainId, controlsPadsExtra.getCustomNamePointer());
				fileManager.loadSysex(sysex, configOptions);					
				Utils.copySysexToConfigCustomName(sysex, configFull.configCustomNames[controlsPadsExtra.getCustomNamePointer()]);
				controlsPadsExtra.updateControls();
			}
		});
		controlsPadsExtra.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if ((configOptions != null) && configOptions.interactive) {
					if (arg0.getPropertyName().equals("valueCurveChanged")) {
						sysexTimedOut = false;
						sendCurve(controlsPadsExtra.getCurvePointer(),true);
					}
					if (arg0.getPropertyName().equals("valueCustomNameChanged")) {
						sysexTimedOut = false;
						sendCustomName(controlsPadsExtra.getCustomNamePointer(), true);
					}
				}
				if (arg0.getPropertyName().equals("CustomNamesChanged")) {
					updateCustomNamesControls();
				}
			}
		});
		controlsPadsExtra.setBorder(new TitledBorder(null, "Pads Extra Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		controlsPadsExtra.getButton_curveGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getCurve(controlsPadsExtra.getCurvePointer());
			}
		});
		controlsPadsExtra.getButton_curveSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendCurve(controlsPadsExtra.getCurvePointer(), true);
			}
		});
		controlsPadsExtra.getButton_curveGetAll().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getAllCurves();
			}
		});
		controlsPadsExtra.getButton_curveSendAll().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendAllCurves(true);
			}
		});
		controlsPadsExtra.getButton_customNameGet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getCustomName(controlsPadsExtra.getCustomNamePointer());
			}
		});
		controlsPadsExtra.getButton_customNameSend().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendCustomName(controlsPadsExtra.getCustomNamePointer(), true);
			}
		});
		controlsPadsExtra.getButton_customNamesGetAll().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				getAllCustomNames();
			}
		});
		controlsPadsExtra.getButton_customNamesSendAll().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sysexTimedOut = false;
				sendAllCustomNames(true);
			}
		});
		panel_main.add(controlsPadsExtra, "4, 2, fill, top");
		
		panelMidiLog = new PanelMidiLog(16);
		panelMidiLog.setBorder(new TitledBorder(null, "MIDI Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_main.add(panelMidiLog, "5, 2, fill, fill");

		// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Pads Extra
		framesDetached = new FrameDetached[Constants.PANELS_COUNT];
		controlsPanels = new JPanel[Constants.PANELS_COUNT];
		viewMenus = new ViewMenu[Constants.PANELS_COUNT];
		controlsPanels[0] = controlsMisc;
		controlsPanels[1] = controlsPedal;
		controlsPanels[2] = controlsPads;
		controlsPanels[3] = controlsPadsExtra;
		controlsPanels[4] = panelMidiLog;
		
		for (int i=0;i<Constants.PANELS_COUNT;i++) {
			controlsPanels[i].addPropertyChangeListener("resize", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					resizeMainWindow();
				}
			});			
			viewMenus[i] = new ViewMenu(Constants.PANELS_NAMES[i], i);
			viewMenus[i].addPropertyChangeListener("resize", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					resizeMainWindow();
				}
			});
			mnView.add(viewMenus[i]);
			framesDetached[i] = new FrameDetached(i);
			framesDetached[i].setTitle(Constants.PANELS_NAMES[i]);
			framesDetached[i].setIconImage(Toolkit.getDefaultToolkit().getImage(Main_window.class.getResource("/icons/megadrum-manager128.png")));
			framesDetached[i].addWindowListener(new WindowAdapter() {
				@Override
				public void windowActivated(WindowEvent arg0) {
					((FrameDetached)arg0.getSource()).pack();
				}
				@Override
				public void windowDeactivated (WindowEvent arg0) {
					int id = ((FrameDetached)arg0.getSource()).controlsId;
					if (!framesDetached[id].isVisible()) {
						configOptions.showPanels[id] = Constants.PANEL_HIDE;
						viewMenus[id].updateControls();
						resizeMainWindow();
					}
				}
			});
		}
		controlsPads.updateCustomNamesList();
		updateAllControls();

	}
	
	class sysexTimerTask extends TimerTask {
	    public void run() {
	    	// Timer expired and Sysex has not been received
			commsStateLabel.setBackground(Color.RED);
			commsStateLabel.setText(Constants.SYSEX_TIMEOUT_TEXT);
			sysexWaitTimer.cancel();
			compareSysexToConfigIsOn = false;
			compareResultCombined = 1;
	    }
	}
	
	private void setSysexOk() {
		commsStateLabel.setVisible(true);
		commsStateLabel.setBackground(Color.GREEN);
		commsStateLabel.setText(Constants.SYSEX_OK_TEXT);					
	}
	
	private void startSysexWaitTimer(int ms) {
		compareSysexToConfigIsOn = true;
		if (sysexWaitTimer != null) {
			sysexWaitTimer.cancel();
		}
		sysexWaitTimer = new Timer();
		sysexWaitTimer.schedule(new sysexTimerTask(), ms);
	}
	
	private void delayMs(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Utils.show_error("Unrecoverable delayMs (timer) error. Exiting.\n" +
					"(" + e.getMessage() + ")");
			System.exit(1);
		}
	}
	private void getTimedOut() {
		commsStateLabel.setVisible(true);
		commsStateLabel.setBackground(Color.RED);
		commsStateLabel.setText(Constants.SYSEX_TIMEOUT_TEXT);
		sysexTimedOut = true;
	}
	
	private void midiIsNotOpen() {
		commsStateLabel.setVisible(true);
		commsStateLabel.setBackground(Color.RED);
		commsStateLabel.setText(Constants.MIDI_IS_NOT_OPEN);
	}
	
	private void getPedal() {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configPedal.sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigPedal();
				while ((delayCounter > 0) && (!configFull.configPedal.sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configPedal.sysexReceived) getTimedOut();
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void sendWithReport(boolean withReport) {
		if (withReport) {
			compareResultCombined = 0;
			compareSysexToConfigLast = true;
			commsStateLabel.setBackground(Color.YELLOW);
			commsStateLabel.setText("SysEx Wait");			
		}
		//startSysexWaitTimer(configOptions.sysexDelay*15);		
		startSysexWaitTimer(configOptions.sysexDelay*3);		
	}
	
	private void sendPedal(boolean withReport) {
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				byte [] sysexPedal = new byte[Constants.MD_SYSEX_PEDAL_SIZE];
				Utils.copyConfigPedalToSysex(configFull.configPedal, sysexPedal, configOptions.chainId);
				midi_handler.sendSysex(sysexPedal);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_PEDAL_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfigPedal();
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
				getPedal();
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void getGlobalMisc() {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configGlobalMisc.sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigGlobalMisc();					
				while ((delayCounter > 0) && (!configFull.configGlobalMisc.sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configGlobalMisc.sysexReceived) {
					getTimedOut();
				} else {
					getReadOnlyData();
					if (configFull.configGlobalMisc.config_names_en) {
						compareSysexToConfigIsOn = false;
						configFull.configNameSysexReceived = false;
						delayCounter = configOptions.sysexDelay;
						midi_handler.requestConfigConfigName(configFull.configCurrent);								
						while ((delayCounter > 0) && (!configFull.configNameSysexReceived)) {
							delayMs(1);
							delayCounter--;
						}
						if (!configFull.configNameSysexReceived) getTimedOut();
					}
				}
			} else {
				midiIsNotOpen();
			}
		}
	}


	private void getReadOnlyData() {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configCountSysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigCount();					
				while ((delayCounter > 0) && (!configFull.configCountSysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configCountSysexReceived) {
					getTimedOut();
				} else {
					compareSysexToConfigIsOn = false;
					configFull.configCurrentSysexReceived = false;
					delayCounter = configOptions.sysexDelay;
					midi_handler.requestConfigCurrent();					
					while ((delayCounter > 0) && (!configFull.configCurrentSysexReceived)) {
						delayMs(1);
						delayCounter--;
					}
					if (!configFull.configCurrentSysexReceived) getTimedOut();
				}		
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void sendGlobalMisc(boolean withReport) {
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				byte [] sysexGlobalMisc = new byte[Constants.MD_SYSEX_GLOBAL_MISC_SIZE];
				Utils.copyConfigGlobalMiscToSysex(configFull.configGlobalMisc, sysexGlobalMisc, configOptions.chainId);
				midi_handler.sendSysex(sysexGlobalMisc);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_GLOBAL_MISC_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfigGlobalMisc();
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		    	if (configFull.configNameChanged) {
		    		configFull.configNameChanged = false;
		    		sendConfigName(configFull.configCurrent, withReport);
		    	}
		    	getGlobalMisc();
		    	getReadOnlyData();
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void getMisc() {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configMisc.sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigMisc();					
				while ((delayCounter > 0) && (!configFull.configMisc.sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configMisc.sysexReceived) getTimedOut();
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void sendMisc(boolean withReport) {
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				byte [] sysexMisc = new byte[Constants.MD_SYSEX_MISC_SIZE];
				Utils.copyConfigMiscToSysex(configFull.configMisc, sysexMisc, configOptions.chainId);
				midi_handler.sendSysex(sysexMisc);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_MISC_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfigMisc();
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		    	getMisc();
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void getPad(int pad_id) {
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				int delayCounter;
				if ( pad_id > 0 ) {
					configFull.configPads[pad_id].sysexReceived = false;
					delayCounter = configOptions.sysexDelay;
					midi_handler.requestConfigPad(pad_id + 1);
					while ((delayCounter > 0) && (!configFull.configPads[pad_id].sysexReceived)) {
						delayMs(1);
						delayCounter--;
					}
					if (!configFull.configPads[pad_id].sysexReceived) {
						getTimedOut();
					} else {
						configFull.configPads[pad_id + 1].sysexReceived = false;
						delayCounter = configOptions.sysexDelay;
						midi_handler.requestConfigPad(pad_id + 2);
						while ((delayCounter > 0) && (!configFull.configPads[pad_id + 1].sysexReceived)) {
							delayMs(1);
							delayCounter--;
						}
						if (!configFull.configPads[pad_id + 1].sysexReceived) {
							getTimedOut();
						} else {
							configFull.configPos[pad_id].sysexReceived = false;
							delayCounter = configOptions.sysexDelay;
							midi_handler.requestConfigPos(pad_id);
							while ((delayCounter > 0) && (!configFull.configPos[pad_id].sysexReceived)) {
								delayMs(1);
								delayCounter--;
							}
							if (!configFull.configPos[pad_id].sysexReceived) {
								getTimedOut();
							} else {
								configFull.configPos[pad_id + 1].sysexReceived = false;
								delayCounter = configOptions.sysexDelay;
								midi_handler.requestConfigPos(pad_id + 1);
								while ((delayCounter > 0) && (!configFull.configPos[pad_id + 1].sysexReceived)) {
									delayMs(1);
									delayCounter--;
								}
								if (!configFull.configPos[pad_id + 1].sysexReceived) {
									getTimedOut();
								} else {
									configFull.config3rds[(pad_id - 1)/2].sysexReceived = false;
									delayCounter = configOptions.sysexDelay;
									midi_handler.requestConfig3rd((pad_id - 1)/2);
									while ((delayCounter > 0) && (!configFull.config3rds[(pad_id - 1)/2].sysexReceived)) {
										delayMs(1);
										delayCounter--;
									}			
									if (!configFull.config3rds[(pad_id - 1)/2].sysexReceived) getTimedOut();
								}					
							}				
						}						
					}					
				} else {
					configFull.configPads[0].sysexReceived = false;
					delayCounter = configOptions.sysexDelay;
					midi_handler.requestConfigPad(1);
					while ((delayCounter > 0) && (!configFull.configPads[0].sysexReceived)) {
						delayMs(1);
						delayCounter--;
					}
					if (!configFull.configPads[0].sysexReceived) {
						getTimedOut();
					} else {
						configFull.configPos[0].sysexReceived = false;
						delayCounter = configOptions.sysexDelay;
						midi_handler.requestConfigPos(0);
						while ((delayCounter > 0) && (!configFull.configPos[0].sysexReceived)) {
							delayMs(1);
							delayCounter--;
						}
						if (!configFull.configPos[0].sysexReceived) getTimedOut();
					}					
				}
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void getPadOneZone(int pad_id) {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configPads[pad_id].sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigPad(pad_id + 1);
				while ((delayCounter > 0) && (!configFull.configPads[pad_id].sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configPads[pad_id].sysexReceived) {
					getTimedOut();
				} else {
					configFull.configPos[pad_id].sysexReceived = false;
					delayCounter = configOptions.sysexDelay;
					midi_handler.requestConfigPos(pad_id);
					while ((delayCounter > 0) && (!configFull.configPos[pad_id].sysexReceived)) {
						delayMs(1);
						delayCounter--;
					}
					if (!configFull.configPos[pad_id].sysexReceived) getTimedOut();
				}		
			} else {
				midiIsNotOpen();
			}
		}
	}

	
	private void getThirdZone(int pad_id) {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.config3rds[pad_id].sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfig3rd(pad_id);
				while ((delayCounter > 0) && (!configFull.config3rds[pad_id].sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.config3rds[pad_id].sysexReceived) getTimedOut();
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void sendPadOneZone(int pad_id, boolean withReport) {
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				byte [] sysexPad = new byte[Constants.MD_SYSEX_PAD_SIZE];
				byte [] sysexPos = new byte[Constants.MD_SYSEX_POS_SIZE];
				
				Utils.copyConfigPadToSysex(configFull.configPads[pad_id], sysexPad, configOptions.chainId, pad_id);
				midi_handler.sendSysex(sysexPad);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_PAD_SIZE/3);		
				sendWithReport(withReport);
				midi_handler.requestConfigPad(pad_id + 1);
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		
		    	if (configOptions.mcuType == 0) return;	// Unknown MCU so it's not clear how to handle positional sysex
				int id = pad_id;
				if (configOptions.mcuType < 3){
					//Atmega MCU, 8 (Atmega1284) or 4 (Atmega644) head/bow inputs with positional sensing starting from Snare
					if (id < 3) return;
					id = id - 3;
					if ((id&1) > 0) return;
					id = id/2;
					if (id > 7) return;
					if ((configOptions.mcuType < 2) && (id > 3)) return;
				}
				Utils.copyConfigPosToSysex(configFull.configPos[pad_id], sysexPos, configOptions.chainId, pad_id);
				sysexPos[4] = (byte)id; 
				midi_handler.sendSysex(sysexPos);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_POS_SIZE/3);	
				sendWithReport(withReport);
				midi_handler.requestConfigPos(pad_id);
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		    	getPadOneZone(pad_id);
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void sendThirdZone(int pad_id, boolean withReport) {
		byte [] sysex3rd = new byte[Constants.MD_SYSEX_3RD_SIZE];
		
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				pad_id = (pad_id - 1)/2;
				Utils.copyConfig3rdToSysex(configFull.config3rds[pad_id], sysex3rd, configOptions.chainId, pad_id);
				midi_handler.sendSysex(sysex3rd);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_3RD_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfig3rd(pad_id);
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		    	getThirdZone(pad_id);
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void sendPad(int pad_id, boolean withReport) {
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				if (withReport) {
					compareResultCombined = 0;
					compareSysexToConfigLast = false;
					commsStateLabel.setBackground(Color.YELLOW);
					commsStateLabel.setText("SysEx Wait");
				}
				if (pad_id == 0) {
					compareSysexToConfigLast = withReport;			
				}
				sendPadOneZone(pad_id, false);
				if (pad_id > 0 ) {
					sendPadOneZone(pad_id + 1, false);
					compareSysexToConfigLast = withReport;
					sendThirdZone(pad_id, false);
				}
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void getAllPads() {
		if (midi_handler.isMidiOpen()) {
			compareSysexToConfigIsOn = false;
			progressBar.setVisible(true);
			Thread t = new Thread(new Runnable() {
	            public void run() {
	                // since we're not on the EDT,
	                // let's put the setVisible-code
	                // into the Event Dispatching Queue
	                SwingUtilities.invokeLater( new Runnable() {
	                    public void run() {
	                		int i;
	                        resizeWindow = false;
	                		progressBar.setMinimum(0);
	                		progressBar.setMaximum(configFull.configGlobalMisc.inputs_count - 3);
	                		for (i = 0; i<(configFull.configGlobalMisc.inputs_count - 2); i++) {
	                			progressBar.setValue(i);
	                			Rectangle progressRect = progressBar.getBounds();
	                			progressRect.x = 0;
	                			progressRect.y = 0;
	                			progressBar.paintImmediately( progressRect );
	                			getPad(i);
	                			if (i>0) {
	                				i++;
	                			}
	                		}
	                		progressBar.setVisible(false);
	                        resizeWindow = true;
	                        resizeMainWindow();
	                   }
	                });
	            }
	
			});
	        t.setPriority( Thread.NORM_PRIORITY );
	        t.run();
		} else {
			midiIsNotOpen();
		}
	}
		
	private void sendAllPadsInThisThread(boolean withReport) {
		if (midi_handler.isMidiOpen()) {
			progressBar.setVisible(true);
			sendWithReport(withReport);
			if (withReport) {
				compareResultCombined = 0;
				compareSysexToConfigLast = false;
				commsStateLabel.setBackground(Color.YELLOW);
				commsStateLabel.setText("SysEx Wait");
			}
			withReportInTask = withReport;
			int i;
	        resizeWindow = false;
			progressBar.setMinimum(0);
			progressBar.setMaximum(configFull.configGlobalMisc.inputs_count - 3);
			for (i = 0; i < (configFull.configGlobalMisc.inputs_count - 4); i++) {
				progressBar.setValue(i);
				Rectangle progressRect = progressBar.getBounds();
				progressRect.x = 0;
				progressRect.y = 0;
				progressBar.paintImmediately( progressRect );
				sendPad(i, false);
				if (i>0) {
					i++;
				}
			}
			compareSysexToConfigLast = withReportInTask;
			sendPad(configFull.configGlobalMisc.inputs_count - 3, false);
			progressBar.setVisible(false);
	        resizeWindow = true;
	        resizeMainWindow();
		} else {
			midiIsNotOpen();
		}
	}

	private void sendAllPads(boolean withReport) {
		if (midi_handler.isMidiOpen()) {
			//progressBar.setVisible(true);
			sendWithReport(withReport);
			if (withReport) {
				compareResultCombined = 0;
				compareSysexToConfigLast = false;
				commsStateLabel.setBackground(Color.YELLOW);
				commsStateLabel.setText("SysEx Wait");
			}
			withReportInTask = withReport;
			Thread t = new Thread(new Runnable() {
	            public void run() {
	                // since we're not on the EDT,
	                // let's put the setVisible-code
	                // into the Event Dispatching Queue
	                SwingUtilities.invokeLater( new Runnable() {
	                    public void run() {
	                    	sendAllPadsInThisThread(withReportInTask);
	                   }
	                });
	            }
	
			});
	        t.setPriority( Thread.NORM_PRIORITY );
	        t.run();
		} else {
			midiIsNotOpen();
		}
	}

	private void getCustomName(int name_id) {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configCustomNames[name_id].sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigCustomName(name_id);
				while ((delayCounter > 0) && (!configFull.configCustomNames[name_id].sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configCustomNames[name_id].sysexReceived) getTimedOut();
			} else {
				midiIsNotOpen();
			}
		}
	}
		
	private void getConfigName(int name_id) {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configConfigNames[name_id].sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigConfigName(name_id);
				while ((delayCounter > 0) && (!configFull.configConfigNames[name_id].sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configConfigNames[name_id].sysexReceived) getTimedOut();
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void sendCustomName(int name_id, boolean withReport) {
		byte [] sysexCustomName = new byte[Constants.MD_SYSEX_CUSTOM_NAME_SIZE];
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				Utils.copyConfigCustomNameToSysex(configFull.configCustomNames[name_id], sysexCustomName, configOptions.chainId, name_id);
				midi_handler.sendSysex(sysexCustomName);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_CUSTOM_NAME_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfigCustomName(name_id);
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		    	getCustomName(name_id);
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void sendConfigName(int name_id, boolean withReport) {
		byte [] sysexConfigName = new byte[Constants.MD_SYSEX_CONFIG_NAME_SIZE];
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				Utils.copyConfigConfigNameToSysex(configFull.configConfigNames[name_id], sysexConfigName, configOptions.chainId, name_id);
				midi_handler.sendSysex(sysexConfigName);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_CONFIG_NAME_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfigConfigName(name_id);
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void getAllCustomNames() {
		if (midi_handler.isMidiOpen()) {
			compareSysexToConfigIsOn = false;
			for (int i = 0; i < configFull.customNamesCount; i++) {
				getCustomName(i);
			}
		} else {
			midiIsNotOpen();
		}
	}
		
	private void getAllConfigNames() {
		if (midi_handler.isMidiOpen()) {
			if (configFull.configGlobalMisc.config_names_en) {
				compareSysexToConfigIsOn = false;
				for (int i = 0; i < configFull.configNamesCount; i++) {
					getConfigName(i);
				}			
			}
		} else {
			midiIsNotOpen();
		}
	}

	private void sendAllCustomNamesInThisThread(boolean withReport) {
		if (midi_handler.isMidiOpen()) {
			for (int i = 0; i < (configFull.customNamesCount - 1); i++) {
				sendCustomName(i, false);
			}
			compareSysexToConfigLast = withReportInTask;
			sendCustomName(configFull.customNamesCount - 1, false);
		} else {
			midiIsNotOpen();
		}
	}
	
	private void sendAllCustomNames(boolean withReport) {
		if (midi_handler.isMidiOpen()) {
			if (withReport) {
				compareResultCombined = 0;
				compareSysexToConfigLast = false;
				commsStateLabel.setBackground(Color.YELLOW);
				commsStateLabel.setText("SysEx Wait");
			}
			withReportInTask = withReport;
			Thread t = new Thread(new Runnable() {
	            public void run() {
	                // since we're not on the EDT,
	                // let's put the setVisible-code
	                // into the Event Dispatching Queue
	                SwingUtilities.invokeLater( new Runnable() {
	                    public void run() {
	                    	sendAllCustomNamesInThisThread(withReportInTask);
	                   }
	                });
	            }
	
			});
	        t.setPriority( Thread.NORM_PRIORITY );
	        t.run();
		} else {
			midiIsNotOpen();
		}
	}
	private void getCurve(int curve_id) {
		int delayCounter;
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				compareSysexToConfigIsOn = false;
				configFull.configCurves[curve_id].sysexReceived = false;
				delayCounter = configOptions.sysexDelay;
				midi_handler.requestConfigCurve(curve_id);
				while ((delayCounter > 0) && (!configFull.configCurves[curve_id].sysexReceived)) {
					delayMs(1);
					delayCounter--;
				}
				if (!configFull.configCurves[curve_id].sysexReceived) getTimedOut();
			} else {
				midiIsNotOpen();
			}
		}
	}

	private void sendCurve(int curve_id, boolean withReport) {
		byte [] sysexCurve = new byte[Constants.MD_SYSEX_CURVE_SIZE];
		if (!sysexTimedOut) {
			if (midi_handler.isMidiOpen()) {
				Utils.copyConfigCurveToSysex(configFull.configCurves[curve_id], sysexCurve, configOptions.chainId, curve_id);
				midi_handler.sendSysex(sysexCurve);
				//delayMs(configOptions.sysexDelay);
				//delayMs(Constants.MD_SYSEX_CURVE_SIZE/3);
				sendWithReport(withReport);
				midi_handler.requestConfigCurve(curve_id);
		    	while (compareSysexToConfigIsOn) {
		    		delayMs(2);
		    	}
		    	getCurve(curve_id);
			} else {
				midiIsNotOpen();
			}
		}
	}
	
	private void getAllCurves() {
		if (midi_handler.isMidiOpen()) {
			compareSysexToConfigIsOn = false;
			for (int i = 0; i<Constants.CURVES_COUNT; i++) {
				getCurve(i);
	    		//delayMs(5);
			}
		} else {
			midiIsNotOpen();
		}
	}
		
	private void sendAllCurvesInThisThread(boolean withReport) {
		if (midi_handler.isMidiOpen()) {
			for (int i = 0; i < (Constants.CURVES_COUNT - 1); i++) {
				sendCurve(i, false);
			}
			compareSysexToConfigLast = withReportInTask;
			sendCurve(Constants.CURVES_COUNT - 1, false);
		} else {
			midiIsNotOpen();
		}
	}
	
	private void sendAllCurves(boolean withReport) {
		if (midi_handler.isMidiOpen()) {
			if (withReport) {
				compareResultCombined = 0;
				compareSysexToConfigLast = false;
				commsStateLabel.setBackground(Color.YELLOW);
				commsStateLabel.setText("SysEx Wait");
			}
			withReportInTask = withReport;
			Thread t = new Thread(new Runnable() {
	            public void run() {
	                // since we're not on the EDT,
	                // let's put the setVisible-code
	                // into the Event Dispatching Queue
	                SwingUtilities.invokeLater( new Runnable() {
	                    public void run() {
	                    	sendAllCurvesInThisThread(withReportInTask);
	                   }
	                });
	            }
	
			});
	        t.setPriority( Thread.NORM_PRIORITY );
	        t.run();
		} else {
			midiIsNotOpen();
		}
	}

	private void getAll() {
		if (midi_handler.isMidiOpen()) {
			compareSysexToConfigIsOn = false;
			getGlobalMisc();
			getMisc();
			getPedal();
			getAllPads();
			getAllCurves();
			getAllCustomNames();
			getAllConfigNames();
		} else {
			midiIsNotOpen();
		}
	}
	
	private void sendAll() {
		if (midi_handler.isMidiOpen()) {
			compareSysexToConfigIsOn = true;
			compareResultCombined = 0;
			compareSysexToConfigLast = false;
			commsStateLabel.setBackground(Color.YELLOW);
			commsStateLabel.setText("SysEx Wait");
	
			Thread t = new Thread(new Runnable() {
	            public void run() {
	                // since we're not on the EDT,
	                // let's put the setVisible-code
	                // into the Event Dispatching Queue
	                SwingUtilities.invokeLater( new Runnable() {
	                    public void run() {
	                		sendMisc(false);
	                    	while (compareSysexToConfigIsOn) {
	                    		delayMs(2);
	                    	}
	                		sendGlobalMisc(false);
	                    	while (compareSysexToConfigIsOn) {
	                    		delayMs(2);
	                    	}
	                		sendAllPadsInThisThread(false);
	                		sendAllCurvesInThisThread(false);
	                		sendAllCustomNamesInThisThread(false);
	                		compareSysexToConfigLast = true;
	                		sendPedal(false);
	                    	while (compareSysexToConfigIsOn) {
	                    		delayMs(2);
	                    	}
	                    	if (delayedSaveToSlot) {
	                    		midi_handler.requestSaveToSlot(delayedSaveToSlotNumber);
	                    		setConfigCurrent(delayedSaveToSlotNumber);
	                    		//
	                    		delayedSaveToSlot = false;
	                    	}
	
	                   }
	                });
	            }
	
			});
	        t.setPriority( Thread.NORM_PRIORITY );
	        t.run();
		} else {
			midiIsNotOpen();
		}
	}

	private void copyConfigToLastConfigNotNeeded() {
		byte [] sysex = new byte[256];

		Utils.copyConfigGlobalMiscToSysex(configFull.configGlobalMisc, sysex, configOptions.chainId);
		Utils.copySysexToConfigGlobalMisc(sysex, fullConfigs[configOptions.lastConfig].configGlobalMisc);

		Utils.copyConfigMiscToSysex(configFull.configMisc, sysex, configOptions.chainId);
		Utils.copySysexToConfigMisc(sysex, fullConfigs[configOptions.lastConfig].configMisc);

		Utils.copyConfigPedalToSysex(configFull.configPedal, sysex, configOptions.chainId);
		Utils.copySysexToConfigPedal(sysex, fullConfigs[configOptions.lastConfig].configPedal);		

		for (int i=0; i < (Constants.MAX_INPUTS - 1); i++) {
			Utils.copyConfigPadToSysex(configFull.configPads[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigPad(sysex, fullConfigs[configOptions.lastConfig].configPads[i]);					
			fullConfigs[configOptions.lastConfig].configPads[i].altNote_linked = configFull.configPads[i].altNote_linked;
			fullConfigs[configOptions.lastConfig].configPads[i].pressrollNote_linked = configFull.configPads[i].pressrollNote_linked;
			Utils.copyConfigPosToSysex(configFull.configPos[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigPos(sysex, fullConfigs[configOptions.lastConfig].configPos[i]);					
		}
		
		for (int i=0; i < ((Constants.MAX_INPUTS/2) - 1); i++) {
			Utils.copyConfig3rdToSysex(configFull.config3rds[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfig3rd(sysex, fullConfigs[configOptions.lastConfig].config3rds[i]);					
			fullConfigs[configOptions.lastConfig].config3rds[i].altNote_linked = configFull.config3rds[i].altNote_linked;
			fullConfigs[configOptions.lastConfig].config3rds[i].pressrollNote_linked = configFull.config3rds[i].pressrollNote_linked;
		}

		for (int i=0; i < (Constants.CURVES_COUNT); i++) {
			Utils.copyConfigCurveToSysex(configFull.configCurves[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigCurve(sysex, fullConfigs[configOptions.lastConfig].configCurves[i]);					
		}
		for (int i=0; i < (Constants.CUSTOM_NAMES_MAX); i++) {
			Utils.copyConfigCustomNameToSysex(configFull.configCustomNames[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigCustomName(sysex, fullConfigs[configOptions.lastConfig].configCustomNames[i]);					
		}
	}

	private void updateAllControls() {
		controlsMisc.updateControls();
		controlsPedal.updateControls();
		int p = controlsPads.getPadPointer();
		controlsPads.setPadPointer(1);
		controlsPads.updateControls();
		controlsPads.setPadPointer(p);
		controlsPads.updateControls();
		controlsPadsExtra.updateControls();		
		updateGlobalMiscControls();
	}
	private void loadAllFromConfigFull() {
		byte [] sysex = new byte[256];

		Utils.copyConfigGlobalMiscToSysex(fullConfigs[configOptions.lastConfig].configGlobalMisc, sysex, configOptions.chainId);
		Utils.copySysexToConfigGlobalMisc(sysex, configFull.configGlobalMisc);

		Utils.copyConfigMiscToSysex(fullConfigs[configOptions.lastConfig].configMisc, sysex, configOptions.chainId);
		Utils.copySysexToConfigMisc(sysex, configFull.configMisc);

		Utils.copyConfigPedalToSysex(fullConfigs[configOptions.lastConfig].configPedal, sysex, configOptions.chainId);
		Utils.copySysexToConfigPedal(sysex, configFull.configPedal);		

		for (int i=0; i < (Constants.MAX_INPUTS - 1); i++) {
			Utils.copyConfigPadToSysex(fullConfigs[configOptions.lastConfig].configPads[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigPad(sysex, configFull.configPads[i]);
			configFull.configPads[i].altNote_linked = fullConfigs[configOptions.lastConfig].configPads[i].altNote_linked;
			configFull.configPads[i].pressrollNote_linked = fullConfigs[configOptions.lastConfig].configPads[i].pressrollNote_linked;
			Utils.copyConfigPosToSysex(fullConfigs[configOptions.lastConfig].configPos[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigPos(sysex, configFull.configPos[i]);
		}
		for (int i=0; i < ((Constants.MAX_INPUTS/2) - 1); i++) {
			Utils.copyConfig3rdToSysex(fullConfigs[configOptions.lastConfig].config3rds[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfig3rd(sysex, configFull.config3rds[i]);
			configFull.config3rds[i].altNote_linked = fullConfigs[configOptions.lastConfig].config3rds[i].altNote_linked;
			configFull.config3rds[i].pressrollNote_linked = fullConfigs[configOptions.lastConfig].config3rds[i].pressrollNote_linked;
		}				
		
		for (int i=0; i < (Constants.CURVES_COUNT); i++) {
			Utils.copyConfigCurveToSysex(fullConfigs[configOptions.lastConfig].configCurves[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigCurve(sysex, configFull.configCurves[i]);					
		}
		for (int i=0; i < (Constants.CUSTOM_NAMES_MAX); i++) {
			Utils.copyConfigCustomNameToSysex(fullConfigs[configOptions.lastConfig].configCustomNames[i], sysex, configOptions.chainId, i);
			Utils.copySysexToConfigCustomName(sysex, configFull.configCustomNames[i]);					
		}
		updateAllControls();
	}
	
	private void load_all() {
		fileManager.load_all(fullConfigs[configOptions.lastConfig], configOptions);
		loadAllFromConfigFull();
	}

	private void save_all() {
		fileManager.save_all(configFull, configOptions);
		updateGlobalMiscControls();
	}
	
	private void loadConfig() {
		//copyAllToConfigFull();
		configOptions  = fileManager.loadLastOptions(configOptions);
		showChangeNotificationIfNeeded();
		comboBoxCfg.setModel(new DefaultComboBoxModel<String>(configOptions.configFileNames));
		comboBoxCfg.setSelectedIndex(configOptions.lastConfig);
		dialog_options.fillInPorts(midi_handler.getMidiInList());
		dialog_options.fillOutPorts(midi_handler.getMidiOutList());
		dialog_options.fillThruPorts(midi_handler.getMidiOutList());
		dialog_options.loadOptionsFrom(configOptions);
		showMidiWarningIfNeeded();
		if (configOptions.autoOpenPorts) {
			midi_handler.initPorts();
			tglbtnMidi.setSelected(midi_handler.isMidiOpen());
		}
		midi_handler.chainId = configOptions.chainId;
		//comboBox_inputsCount.setSelectedIndex((fullConfigs[configOptions.lastConfig].configGlobalMisc.inputs_count - Constants.MIN_INPUTS)/2);
		//updateInputsCountControls();
		if (!configOptions.configFullPaths[configOptions.lastConfig].equals("")) {
			fileManager.loadAllSilent(fullConfigs[configOptions.lastConfig], configOptions);
			loadAllFromConfigFull();
		}
		updateGlobalMiscControls();
		frmMegadrummanager.setLocation(configOptions.mainWindowPosition);
		for (int i = 0;i<Constants.PANELS_COUNT;i++) {
			framesDetached[i].setLocation(configOptions.framesPositions[i]);
			viewMenus[i].setConfigOptions(configOptions);
		}
		tglbtnLiveUpdates.setSelected(configOptions.interactive);
		checkBoxAutoResize.setSelected(configOptions.autoResize);
	}
	
	private void saveAndExit() {
		midi_handler.closeAllPorts();
		configOptions.mainWindowPosition = frmMegadrummanager.getLocation();
		for (int i = 0;i<Constants.PANELS_COUNT;i++) {
			configOptions.framesPositions[i] = framesDetached[i].getLocation(); 
		}
		dialog_options.saveOptionsTo(configOptions);
		if (configOptions.saveOnExit) {
			fileManager.saveLastOptions(configOptions);
		}
		System.exit(0);
	}
	
	private void updateGlobalMiscControls() {
//		comboBox_inputsCount.setSelectedIndex((configFull.configGlobalMisc.inputs_count - Constants.MIN_INPUTS)/2);
		if (configFull != null) {
			comboBox_inputsCount.setSelectedIndexWithoutEvent((configFull.configGlobalMisc.inputs_count - Constants.MIN_INPUTS)/2);
			if ((Integer)spinnerLCDcontrast.getValue() != configFull.configGlobalMisc.lcd_contrast) {
				spinnerLCDEventDisabled = 1;
				spinnerLCDcontrast.setValue(configFull.configGlobalMisc.lcd_contrast);
			}
			controlsPedal.updateInputCountsControls(configFull.configGlobalMisc.inputs_count);
			controlsPads.updateInputCountsControls(configFull.configGlobalMisc.inputs_count);
			chckbxConfignamesen.setSelected(configFull.configGlobalMisc.config_names_en);
			chckbxCustomPadsNames.setSelected(configFull.configGlobalMisc.custom_names_en);
			chckbxMidi2ForSysex.setSelected(configFull.configGlobalMisc.midi2_for_sysex);
			int s = comboBoxCfg.getSelectedIndex();
			comboBoxCfgNoActions = 1;
			comboBoxCfg.setModel(new DefaultComboBoxModel<String>(configOptions.configFileNames));
			comboBoxCfg.setSelectedIndex(s);
			comboBoxCfgNoActions = 0;
		}
		if (configOptions.configLoaded[configOptions.lastConfig]) {
			lblOk.setBackground(Color.GREEN);
			lblOk.setText("Ok");
		} else {
			lblOk.setBackground(Color.RED);
			lblOk.setText("??");
		}
		//updateGlobalMiscSyncState();
	}
	
	private void resizeMainWindow() {
		if (checkBoxAutoResize.isSelected()) {
			// Show panels. 0 - Misc, 1 - Pedal, 2 - Pads, 3 - Pads Extra, 4 - MIDI Log
			for (int i = 0; i< Constants.PANELS_COUNT; i++) {
				if (LookAndFeelChanged) {
					SwingUtilities.updateComponentTreeUI(framesDetached[i]);
				}
				controlsPanels[i].setVisible(configOptions.showPanels[i] != Constants.PANEL_HIDE);
				if (i == 3) {
					// Pause, start live MIDI log
					panelMidiLog.pauseLiveScroll(!panelMidiLog.isVisible());
				}
				if (configOptions.showPanels[i] == Constants.PANEL_DETACH) {
					if (!framesDetached[i].isDetached) {
						framesDetached[i].isDetached = true;
						framesDetached[i].getContentPane().add(controlsPanels[i], "1, 1, fill, top");
						framesDetached[i].setVisible(true);
					}
				} else {
					if (framesDetached[i].isDetached) {
						framesDetached[i].isDetached = false;
						framesDetached[i].setVisible(false);
						panel_main.add(controlsPanels[i], ((Integer)(i+1)).toString() +", 2, default, top");
					}
				}
				framesDetached[i].pack();
			}
			if (LookAndFeelChanged) {
				SwingUtilities.updateComponentTreeUI(frmMegadrummanager);
			}
			LookAndFeelChanged = false;
			if (resizeWindow) {
				frmMegadrummanager.pack();
			}			
		}
	}
	
	private void toggleMidiOpenButton() {
		if (midi_handler.isMidiOpen()) {
			sysexTimedOut = false;
			tglbtnMidi.setText("Close MIDI");
			tglbtnMidi.setSelected(true);
			versionWarningAlreadyShown = false;
			midi_handler.requestVersionAndMcu();
		} else {
			tglbtnMidi.setText("Open MIDI");
			tglbtnMidi.setSelected(false);
			popupMenuLoadFromSlot.removeAll();
			mntmLoadFromMd.removeAll();
			popupMenuSaveToSlot.removeAll();
			mntmSaveToMd.removeAll();
			configFull.resetSyncState();
			updateAllControls();
			updateGlobalMiscSyncState();
		}
	}
	
	private void compareSysexToConfig(byte [] buffer) {
		int compareResult = 1; // 0 means Ok, otherwise Error
		if (buffer[1] == Constants.MD_SYSEX) {
			if (buffer[2] == (byte) configOptions.chainId) {
				switch (buffer[3]) {
					case Constants.MD_SYSEX_MISC:
						compareResult = Utils.compareSysexToConfigMisc(midi_handler.bufferIn, configFull.configMisc);
						break;
					case Constants.MD_SYSEX_PEDAL:
						compareResult = Utils.compareSysexToConfigPedal(midi_handler.bufferIn, configFull.configPedal);
						break;
					case Constants.MD_SYSEX_PAD:
						compareResult = Utils.compareSysexToConfigPad(midi_handler.bufferIn, configFull.configPads[buffer[4] - 1]);
						break;
					case Constants.MD_SYSEX_POS:
						if (configOptions.mcuType == 0) break;	// Unknown MCU so it's not clear how to handle positional sysex
						int id = buffer[4];
						if (configOptions.mcuType < 3) {
							//Atmega MCU, 8 (Atmega1284) or 4 (Atmega644) head/bow inputs with positional sensing starting from Snare
							id = (id*2) + 3;
						}
						compareResult = Utils.compareSysexToConfigPos(midi_handler.bufferIn, configFull.configPos[id]);
						break;
					case Constants.MD_SYSEX_3RD:
						compareResult = Utils.compareSysexToConfig3rd(midi_handler.bufferIn, configFull.config3rds[buffer[4]]);
						break;
					case Constants.MD_SYSEX_CURVE:
						compareResult = Utils.compareSysexToConfigCurve(midi_handler.bufferIn, configFull.configCurves[buffer[4]]);
						break;
					case Constants.MD_SYSEX_CUSTOM_NAME:
						compareResult = Utils.compareSysexToConfigCustomName(midi_handler.bufferIn, configFull.configCustomNames[buffer[4]]);
						break;
					case Constants.MD_SYSEX_CONFIG_NAME:
						compareResult = Utils.compareSysexToConfigConfigName(midi_handler.bufferIn, configFull.configConfigNames[buffer[4]]);
						break;
					case Constants.MD_SYSEX_GLOBAL_MISC:
						compareResult = Utils.compareSysexToConfigGlobalMisc(midi_handler.bufferIn, configFull.configGlobalMisc);
						break;
					default:
						break;
				}
			}
			if (compareResult != 0) {
				compareResultCombined = compareResult;
			}
			if (compareSysexToConfigLast) {
			//if (buffer[3] == Constants.MD_SYSEX_MISC) {
				if (compareResultCombined != 0) {
					commsStateLabel.setBackground(Color.RED);
					commsStateLabel.setText(Constants.SYSEX_TIMEOUT_TEXT);
				} else {
					commsStateLabel.setBackground(Color.GREEN);
					commsStateLabel.setText(Constants.SYSEX_OK_TEXT);					
				}
			}	
			// Simulate failure of one input
//			if ((buffer[3] == Constants.MD_SYSEX_PAD) && (buffer[4] == 11)) {
//				return;
//			}
			sysexWaitTimer.cancel();
			compareSysexToConfigIsOn = false;
		}
	}
	
	private void decodeSysex(byte [] buffer) {
		if (sysexTimedOut) return;
		if (buffer[1] == Constants.MD_SYSEX) {
			if (buffer[2] == (byte) configOptions.chainId) {
				switch (buffer[3]) {
					case Constants.MD_SYSEX_MISC:
						Utils.copySysexToConfigMisc(midi_handler.bufferIn, configFull.configMisc);
						Utils.copySysexToConfigMisc(midi_handler.bufferIn, moduleConfigFull.configMisc);
						configFull.configMisc.syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.configMisc.sysexReceived = true;
						setSysexOk();
						controlsMisc.updateControls();
						break;
					case Constants.MD_SYSEX_PEDAL:
						Utils.copySysexToConfigPedal(midi_handler.bufferIn, configFull.configPedal);
						Utils.copySysexToConfigPedal(midi_handler.bufferIn, moduleConfigFull.configPedal);
						configFull.configPedal.syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.configPedal.sysexReceived = true;
						setSysexOk();
						controlsPedal.updateControls();
						break;
					case Constants.MD_SYSEX_PAD:
						Utils.copySysexToConfigPad(midi_handler.bufferIn, configFull.configPads[buffer[4] - 1]);
						Utils.copySysexToConfigPad(midi_handler.bufferIn, moduleConfigFull.configPads[buffer[4] - 1]);
						configFull.configPads[buffer[4] - 1].syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.configPads[buffer[4] - 1].sysexReceived = true;
						setSysexOk();
						controlsPads.updateControls();
						break;
					case Constants.MD_SYSEX_POS:
						if (configOptions.mcuType == 0) break;	// Unknown MCU so it's not clear how to handle positional sysex
						int id = buffer[4];
						if (configOptions.mcuType < 3) {
							//Atmega MCU, 8 (Atmega1284) or 4 (Atmega644) head/bow inputs with positional sensing starting from Snare
							id = (id*2) + 3;
						}
						Utils.copySysexToConfigPos(midi_handler.bufferIn, configFull.configPos[id]);
						Utils.copySysexToConfigPos(midi_handler.bufferIn, moduleConfigFull.configPos[id]);
						configFull.configPos[id].sysexReceived = true;
						setSysexOk();
						controlsPads.updateControls();
						break;
					case Constants.MD_SYSEX_3RD:
						Utils.copySysexToConfig3rd(midi_handler.bufferIn, configFull.config3rds[buffer[4]]);
						Utils.copySysexToConfig3rd(midi_handler.bufferIn, moduleConfigFull.config3rds[buffer[4]]);
						configFull.config3rds[buffer[4]].syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.config3rds[buffer[4]].sysexReceived = true;
						setSysexOk();
						controlsPads.updateControls();
						break;
					case Constants.MD_SYSEX_VERSION:
						int ver = 0;
						if (buffer.length >= Constants.MD_SYSEX_VERSION_SIZE) {
							int b;
							for (int i=0;i<4;i++) {
								b = (int)(buffer[i*2 + 4]<<4);
								b |= (int)buffer[i*2 + 5];
								ver += b<<(8*i);
							}
							configOptions.version = ver;
							lblVersion.setText(((Integer)ver).toString());
							setSysexOk();
							if (ver < Constants.MD_MINIMUM_VERSION) {
								if (!versionWarningAlreadyShown) {
									versionWarningAlreadyShown = true;
									lblVersion.setBackground(Color.RED);
									Timer warning_timer = new Timer();
									warning_timer.schedule(new TimerTask() {
										
										@Override
										public void run() {
											JOptionPane.showMessageDialog(null,
												    "<html><font size=5>"+Constants.WARNING_VERSION+"</font></html>",
												    "Warning",
												    JOptionPane.WARNING_MESSAGE);
										}
									}, 200);
								}
							} else {
								lblVersion.setBackground(Color.GREEN);
							}
						}
						break;
					case Constants.MD_SYSEX_CURVE:
						Utils.copySysexToConfigCurve(midi_handler.bufferIn, configFull.configCurves[buffer[4]]);
						Utils.copySysexToConfigCurve(midi_handler.bufferIn, moduleConfigFull.configCurves[buffer[4]]);
						configFull.configCurves[buffer[4]].syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.configCurves[buffer[4]].sysexReceived = true;
						setSysexOk();
						controlsPadsExtra.updateControls();
						break;
					case Constants.MD_SYSEX_CUSTOM_NAME:
						Utils.copySysexToConfigCustomName(midi_handler.bufferIn, configFull.configCustomNames[buffer[4]]);
						Utils.copySysexToConfigCustomName(midi_handler.bufferIn, moduleConfigFull.configCustomNames[buffer[4]]);
						configFull.configCustomNames[buffer[4]].syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.configCustomNames[buffer[4]].sysexReceived = true;
						setSysexOk();
						controlsPadsExtra.updateControls();
						break;
					case Constants.MD_SYSEX_CONFIG_NAME:
						Utils.copySysexToConfigConfigName(midi_handler.bufferIn, configFull.configConfigNames[buffer[4]]);
						Utils.copySysexToConfigConfigName(midi_handler.bufferIn, moduleConfigFull.configConfigNames[buffer[4]]);
						configFull.configConfigNames[buffer[4]].sysexReceived = true;
						setSysexOk();
						if (buffer[4] < configFull.configNamesCount) {
							menuItemsSaveToSlot[buffer[4]].setText(((Integer)(buffer[4]+1)).toString() + " " + configFull.configConfigNames[buffer[4]].name);
							popupMenuItemsSaveToSlot[buffer[4]].setText(((Integer)(buffer[4]+1)).toString() + " " + configFull.configConfigNames[buffer[4]].name);
							menuItemsLoadFromSlot[buffer[4]].setText(((Integer)(buffer[4]+1)).toString() + " " + configFull.configConfigNames[buffer[4]].name);
							popupMenuItemsLoadFromSlot[buffer[4]].setText(((Integer)(buffer[4]+1)).toString() + " " + configFull.configConfigNames[buffer[4]].name);
						}
						if (configFull.configCurrent == buffer[4]) {
							configNameTextField.setText(configFull.configConfigNames[buffer[4]].name.trim());
						}
						break;
					case Constants.MD_SYSEX_GLOBAL_MISC:
						Utils.copySysexToConfigGlobalMisc(midi_handler.bufferIn, configFull.configGlobalMisc);
						Utils.copySysexToConfigGlobalMisc(midi_handler.bufferIn, moduleConfigFull.configGlobalMisc);
						configFull.configGlobalMisc.syncState = Constants.SYNC_STATE_RECEIVED;
						configFull.configGlobalMisc.sysexReceived = true;
						setSysexOk();
						int c = comboBox_inputsCount.getSelectedIndex();
						comboBox_inputsCount.setSelectedIndexWithoutEvent((configFull.configGlobalMisc.inputs_count - Constants.MIN_INPUTS)/2);
						if (comboBox_inputsCount.getSelectedIndex() != c) {
							updateGlobalMiscControls();
						}
						if ((Integer)spinnerLCDcontrast.getValue() != configFull.configGlobalMisc.lcd_contrast) {
							spinnerLCDEventDisabled = 1;
							spinnerLCDcontrast.setValue(configFull.configGlobalMisc.lcd_contrast);
						}
						if (chckbxCustomPadsNames.isSelected() != configFull.configGlobalMisc.custom_names_en) {
							chckbxCustomPadsNamesEventDisabled = 1;
							chckbxCustomPadsNames.setSelected(configFull.configGlobalMisc.custom_names_en);							
						}
						if (chckbxConfignamesen.isSelected() != configFull.configGlobalMisc.config_names_en) {
							chckbxConfignamesenEventDisabled = 1;
							chckbxConfignamesen.setSelected(configFull.configGlobalMisc.config_names_en);
							configNameTextField.setEnabled(configFull.configGlobalMisc.config_names_en);
						}
						if (chckbxMidi2ForSysex.isSelected() != configFull.configGlobalMisc.midi2_for_sysex) {
							chckbxMidi2ForSysexEventDisabled = 1;
							chckbxMidi2ForSysex.setSelected(configFull.configGlobalMisc.midi2_for_sysex);							
						}
						updateGlobalMiscSyncState();
						break;
					case Constants.MD_SYSEX_MCU_TYPE:
						if (buffer.length >= Constants.MD_SYSEX_MCU_TYPE_SIZE) {
							configOptions.mcuType = (int)(buffer[4]<<4);
							configOptions.mcuType |= (int)buffer[5];
							if (configOptions.mcuType < Constants.MCU_TYPES.length ) {
								lblMCU.setText(Constants.MCU_TYPES[configOptions.mcuType]);								
							}
							setSysexOk();
						}
						break;
					case Constants.MD_SYSEX_CONFIG_COUNT:
						if (buffer.length >= Constants.MD_SYSEX_CONFIG_COUNT_SIZE) {
							int b;
							b = (int)buffer[4];
							lblCfgSlotsNr.setText(((Integer)b).toString());
							configFull.configCountSysexReceived = true;
							setSysexOk();
							popupMenuSaveToSlot.removeAll();
							mntmSaveToMd.removeAll();
							popupMenuLoadFromSlot.removeAll();
							mntmLoadFromMd.removeAll();
							for (int i = 0; i < b; i++) {
								popupMenuSaveToSlot.add(popupMenuItemsSaveToSlot[i]);
								mntmSaveToMd.add(menuItemsSaveToSlot[i]);
								popupMenuLoadFromSlot.add(popupMenuItemsLoadFromSlot[i]);
								mntmLoadFromMd.add(menuItemsLoadFromSlot[i]);
							}
						}
						break;
					case Constants.MD_SYSEX_CONFIG_CURRENT:
						if (buffer.length >= Constants.MD_SYSEX_CONFIG_CURRENT_SIZE) {
							int b;
							b = (int)buffer[4];
							setConfigCurrent(b);
							configFull.configCurrentSysexReceived = true;
							setSysexOk();
							//popupMenuSaveToSlot.removeAll();
						}
						break;
					default:
						break;
				}
			}
		}		
	}
	
	private void updateGlobalMiscSyncState() {
		if (configFull.configGlobalMisc.syncState == Constants.SYNC_STATE_UNKNOWN ) {
			chckbxConfignamesen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			chckbxCustomPadsNames.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			chckbxMidi2ForSysex.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblLCDcontrast.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblInputs.setSyncState(Constants.SYNC_STATE_UNKNOWN);
		} else {
			chckbxConfignamesen.setSyncNotSync(configFull.configGlobalMisc.config_names_en == moduleConfigFull.configGlobalMisc.config_names_en);
			chckbxCustomPadsNames.setSyncNotSync(configFull.configGlobalMisc.custom_names_en == moduleConfigFull.configGlobalMisc.custom_names_en);
			chckbxMidi2ForSysex.setSyncNotSync(configFull.configGlobalMisc.midi2_for_sysex == moduleConfigFull.configGlobalMisc.midi2_for_sysex);
			lblLCDcontrast.setSyncNotSync(configFull.configGlobalMisc.lcd_contrast == moduleConfigFull.configGlobalMisc.lcd_contrast);
			lblInputs.setSyncNotSync(configFull.configGlobalMisc.inputs_count == moduleConfigFull.configGlobalMisc.inputs_count);
		}
	}
	
	private void setConfigCurrent(int id) {
		lblCfgCurrent.setText(((Integer)(id + 1)).toString());
		configFull.configCurrent = id;		
		configFull.configNameChanged = false;
		configNameTextField.setText(configFull.configConfigNames[id].name.trim());
		menuItemsSaveToSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
		popupMenuItemsSaveToSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
		menuItemsLoadFromSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
		popupMenuItemsLoadFromSlot[id].setText(((Integer)(id+1)).toString() + " " + configFull.configConfigNames[id].name);
	}
	
	private void clearConfigSlotsNames() {
		for (int i =0; i < Constants.CONFIG_NAMES_MAX; i++) {
			menuItemsSaveToSlot[i] = new JMenuItem();
			menuItemsSaveToSlot[i].setText(((Integer)(i+1)).toString());
			menuItemsSaveToSlot[i].setName(((Integer)(i+1)).toString());
			menuItemsSaveToSlot[i].addActionListener(saveToSlotAction);
			popupMenuItemsSaveToSlot[i] = new JMenuItem();
			popupMenuItemsSaveToSlot[i].setText(((Integer)(i+1)).toString());
			popupMenuItemsSaveToSlot[i].setName(((Integer)(i+1)).toString());
			popupMenuItemsSaveToSlot[i].addActionListener(saveToSlotAction);
			menuItemsLoadFromSlot[i] = new JMenuItem();
			menuItemsLoadFromSlot[i].setText(((Integer)(i+1)).toString());
			menuItemsLoadFromSlot[i].setName(((Integer)(i+1)).toString());
			menuItemsLoadFromSlot[i].addActionListener(loadFromSlotAction);
			popupMenuItemsLoadFromSlot[i] = new JMenuItem();
			popupMenuItemsLoadFromSlot[i].setText(((Integer)(i+1)).toString());
			popupMenuItemsLoadFromSlot[i].setName(((Integer)(i+1)).toString());
			popupMenuItemsLoadFromSlot[i].addActionListener(loadFromSlotAction);
		}	
	}
	
	public void decodeShortMidi (byte [] buffer) {
		Color color;
		panelMidiLog.addRawMidi(buffer);
		switch (buffer.length) {
		case 1:
			//shortMessage.setMessage(buf[0]);
			break;
		case 2:
			//shortMessage.setMessage(buf[0], buf[1],0);
			break;
		default:
			//shortMessage.setMessage(buf[0], buf[1],buf[2]);
			if (((buffer[0]&0xf0) == 0x90) && (buffer[2] > 0)) {
				color = Constants.MD_UNKNOWN_COLOR;
				for (int i = 0; i< configFull.configPads.length;i++) {
					if ((buffer[1]==configFull.configPads[i].note) ||
							(buffer[1]==configFull.configPads[i].altNote) ||
							(buffer[1]==configFull.configPads[i].pressrollNote)) {
						if (i==0) {
							color = Constants.MD_HEAD_COLOR;
						} else {
							color = ((i&0x01)==1)?Constants.MD_HEAD_COLOR:Constants.MD_RIM_COLOR;
						}
					}
					if ((i&0x01) == 1) {
						if ((buffer[1]==configFull.config3rds[(i-1)/2].note) ||
								(buffer[1]==configFull.config3rds[(i-1)/2].altNote) ||
								(buffer[1]==configFull.config3rds[(i-1)/2].pressrollNote) ||
								(buffer[1]==configFull.config3rds[(i-1)/2].dampenedNote)) {
									color = Constants.MD_3RD_COLOR;
						}						
					}
				}
				panelMidiLog.showNewHit(buffer[1], buffer[2], color);
			}
			if ((buffer[0]&0xf0) == 0xa0) {
				panelMidiLog.showNewHit(buffer[1], 127,(buffer[2]>0)?Constants.MD_AFTERTOUCH_ON_COLOR:Constants.MD_AFTERTOUCH_OFF_COLOR);
			}
			if (((buffer[0]&0xf0) == 0xb0) && (buffer[1] == 0x04)) {
				panelMidiLog.showHiHatLevel(127 - buffer[2], Constants.MD_HIHAT_COLOR);
			}
			if (((buffer[0]&0xf0) == 0xb0) && (buffer[1] == 0x10)) {
				panelMidiLog.showNewPositional(127 - buffer[2]);
			}
			if (((buffer[0]&0xf0) == 0xb0) && (buffer[1] == 0x13)) {
				int id = buffer[2];
				if (id > 0x3f) {
					id = (id - 0x40)*2 + 1; 
				} else {
					id--;
				}
				if (id > 0) {
					id = (id - 1)/2 + 1;
				} else {
					id = 0;
				}
				if (configFull.configMisc.send_triggered_in) {
					controlsPads.switchAndShowPad(id);
				}
			}
			break;
		}
	
	}
	
	private void showMidiWarningIfNeeded() {
		if (configOptions.MidiInName.equals("") || configOptions.MidiOutName.equals("")) {
			JOptionPane.showMessageDialog(null,
				    Constants.MIDI_PORTS_WARNING,
				    "Warning",
				    JOptionPane.INFORMATION_MESSAGE);
		}		
	}
	
	private void showChangeNotificationIfNeeded() {
		if (!configOptions.changeNotified) {
			int reply = JOptionPane.showConfirmDialog(null,
				    Constants.CHNAGE_WARNING,
				    "Warning",
				    JOptionPane.YES_NO_OPTION);
			if (reply != JOptionPane.YES_OPTION) {
				configOptions.changeNotified = true;				
			}
		}
	}
	
	private void updateCustomNamesControls() {
		controlsPads.updateCustomNamesList();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

