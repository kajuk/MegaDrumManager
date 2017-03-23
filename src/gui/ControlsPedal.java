package gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.Component;
import javax.swing.Box;
//import javax.swing.JLabel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeEvent;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

public class ControlsPedal extends JPanel implements ValueChangedListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9188782839444156764L;

	//private Boolean changeEventsAllowed = false;
	private Boolean controlsInited = false;
	private int prevInputsCount;

	private JButton btnGet;
	private JButton btnSend;
	private JComboBoxCustom comboBox_type;
	private JComboBoxCustom comboBox_curve;
	private JComboBoxCustom comboBox_chickCurve;
	private JComboBoxCustom comboBox_input;
	private JCheckBoxCustom checkBox_altInput;
	private JCheckBoxCustom checkBox_reverseLevels;
	private JCheckBoxCustom checkBox_softChicks;
	private JCheckBoxCustom checkBox_autoLevels;
	private JCheckBoxCustom checkBox_new_algorithm;
	private Spin127Control spin127Control_chickDelay;
	private Spin127Control spin127Control_cc;
	private Spin127Control spin127Control_ccRdcLvl;
	private Spin1023Control spin1023Control_lowLevel;
	private Spin1023Control spin1023Control_highLevel;
	private Spin1023Control spin1023Control_chickMinVelocity;
	private Spin1023Control spin1023Control_chickMaxVelocity;
	private Spin1023Control spin1023Control_chickDeadPeriod;
	private Spin127Control spin127Control_openLevel;
	private Spin127Control spin127Control_semiOpen;
	private Spin127Control spin127Control_halfOpen;
	private Spin127Control spin127Control_semiOpen2;
	private Spin127Control spin127Control_halfOpen2;
	private Spin127Control spin127Control_closed;
	private Spin127Control spin127Control_shortThres;
	private Spin127Control spin127Control_longThres;
	private Spin127Control spin127Control_chickThres;
	private NoteSpinControl noteSpinControl_bowSemiOpen;
	private NoteSpinControl noteSpinControl_edgeSemiOpen;
	private NoteSpinControl noteSpinControl_bellSemiOpen;
	private NoteSpinControl noteSpinControl_bowSemiOpen2;
	private NoteSpinControl noteSpinControl_edgeSemiOpen2;
	private NoteSpinControl noteSpinControl_bellSemiOpen2;
	private NoteSpinControl noteSpinControl_bowHalfOpen;
	private NoteSpinControl noteSpinControl_edgeHalfOpen;
	private NoteSpinControl noteSpinControl_bellHalfOpen;
	private NoteSpinControl noteSpinControl_bowHalfOpen2;
	private NoteSpinControl noteSpinControl_edgeHalfOpen2;
	private NoteSpinControl noteSpinControl_bellHalfOpen2;
	private NoteSpinControl noteSpinControl_bowSemiClosed;
	private NoteSpinControl noteSpinControl_edgeSemiClosed;
	private NoteSpinControl noteSpinControl_bellSemiClosed;
	private NoteSpinControl noteSpinControl_bowClosed;
	private NoteSpinControl noteSpinControl_edgeClosed;
	private NoteSpinControl noteSpinControl_bellClosed;
	private NoteSpinControl noteSpinControl_chick;
	private NoteSpinControl noteSpinControl_splash;
	
	private ConfigFull configFull;
	private ConfigFull moduleConfigFull;
	private JButton btnLoad;
	private JButton btnSave;
	private LabelWithState lblType;

	private LabelWithState lblNewAlgorithm;

	private LabelWithState lblChickCurve;

	private LabelWithState lblHihatInput;

	private LabelWithState lblAltInput;

	private LabelWithState lblReverseLevels;

	private LabelWithState lblSoftChicks;

	private LabelWithState lblAutoLevels;

	private LabelWithState lblChickDelay;

	private LabelWithState lblCcNumber;

	private LabelWithState lblCcRdcLvl;

	private LabelWithState lblLow;

	private LabelWithState lblHigh;

	private LabelWithState lblOpen;

	private LabelWithState lblSemiOpen;

	private LabelWithState lblSemiOpen2;

	private LabelWithState lblHalfopen;

	private LabelWithState lblHalfopen2;

	private LabelWithState lblClosed;

	private LabelWithState lblChickthresh;

	private LabelWithState lblCurve;

	private LabelWithState lblShortThresh;

	private LabelWithState lblLongchickthresh;

	private LabelWithState lblChickMinVelocity;

	private LabelWithState lblChickMaxVelocity;

	private LabelWithState lblChickDeadPeriod;

	private LabelWithState lblBowSemiopen;

	private LabelWithState lblEdgeSemiopen;

	private LabelWithState lblBellSemiopen;

	private LabelWithState lblBowSemiopen2;

	private LabelWithState lblEdgeSemiopen2;

	private LabelWithState lblBellSemiopen2;

	private LabelWithState lblBowHalfopen;

	private LabelWithState lblEdgeHalfopen;

	private LabelWithState lblBellHalfopen;

	private LabelWithState lblBowHalfopen2;

	private LabelWithState lblEdgeHalfopen2;

	private LabelWithState lblBellHalfopen2;

	private LabelWithState lblBowSemiclosed;

	private LabelWithState lblEdgeSemiclosed;

	private LabelWithState lblBellSemiclosed;

	private LabelWithState lblBowClosed;

	private LabelWithState lblEdgeClosed;

	private LabelWithState lblBellClosed;

	private LabelWithState lblChick;

	private LabelWithState lblSplash;

	/**
	 * Create the panel.
	 */
	public ControlsPedal(ConfigFull config, ConfigFull module) {
		configFull = config;
		moduleConfigFull = module;
		setLayout(new GridLayout(1, 0, 0, 0));		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.MIN_ROWSPEC,}));
		
		JPanel panel_buttons = new JPanel();
		panel.add(panel_buttons, "1, 1, fill, fill");
		panel_buttons.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("fill:default"),}));
		
		btnGet = new JButton("Get");
		btnGet.setMargin(new Insets(1, 4, 1, 4));
		btnGet.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnGet, "1, 1, fill, fill");
		
		btnSend = new JButton("Send");
		btnSend.setMargin(new Insets(1, 4, 1, 4));
		btnSend.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSend, "3, 1, fill, fill");
		
		btnLoad = new JButton("Load");
		btnLoad.setMargin(new Insets(1, 4, 1, 4));
		btnLoad.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnLoad, "5, 1");
		
		btnSave = new JButton("Save");
		btnSave.setMargin(new Insets(1, 4, 1, 4));
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_buttons.add(btnSave, "7, 1");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, "1, 2, left, top");
		
		JPanel panel_misc = new JPanel();
		tabbedPane.addTab("Misc", null, panel_misc, null);
		panel_misc.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("12dlu"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblType = new LabelWithState("Type");
		lblType.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblType, "1, 1, right, default");
		
		comboBox_type = new JComboBoxCustom();
		comboBox_type.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_type.selectEventsDisabled > 0) {
						comboBox_type.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
			}
		});
		comboBox_type.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		comboBox_type.addItem("Pot");		
		comboBox_type.addItem("FootContr");
		panel_misc.add(comboBox_type, "3, 1, fill, default");
		
		lblCurve = new LabelWithState("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCurve, "1, 2, right, default");
		
		comboBox_curve = new JComboBoxCustom();
		comboBox_curve.setMaximumRowCount(16);
		comboBox_curve.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_curve.selectEventsDisabled > 0) {
						comboBox_curve.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
			}
		});
		comboBox_curve.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		for (String string : Constants.CURVES_LIST) {
			comboBox_curve.addItem(string);
			}
		panel_misc.add(comboBox_curve, "3, 2, fill, default");
		
		lblChickCurve = new LabelWithState("Chick Curve");
		lblChickCurve.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_misc.add(lblChickCurve, "1, 3, right, default");
		
		comboBox_chickCurve = new JComboBoxCustom();
		comboBox_chickCurve.setMaximumRowCount(16);
		comboBox_chickCurve.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_chickCurve.selectEventsDisabled > 0) {
						comboBox_chickCurve.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
			}
		});
		comboBox_chickCurve.setFont(new Font("Dialog", Font.PLAIN, 10));
		for (String string : Constants.CURVES_LIST) {
			comboBox_chickCurve.addItem(string);
			}
		panel_misc.add(comboBox_chickCurve, "3, 3, fill, default");
		
		lblHihatInput = new LabelWithState("HiHat Input");
		lblHihatInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblHihatInput, "1, 4, right, default");
		
		comboBox_input = new JComboBoxCustom();
		comboBox_input.setMaximumRowCount(20);
		comboBox_input.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (comboBox_input.selectEventsDisabled > 0) {
						comboBox_input.selectEventsDisabled--;
					} else {
						if (controlsInited) {
							valueChanged();
						}
					}
				}
			}
		});
		updateInputCountsControls(Constants.PADS_COUNT);
		comboBox_input.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(comboBox_input, "3, 4, fill, default");
		
		lblAltInput = new LabelWithState("Alt Input");
		lblAltInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAltInput, "1, 5");
		
		checkBox_altInput = new JCheckBoxCustom("");
		checkBox_altInput.setValueChangedListener(this);
		checkBox_altInput.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_altInput, "3, 5");
		
		lblReverseLevels = new LabelWithState("Reverse Levels");
		lblReverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblReverseLevels, "1, 6");
		
		checkBox_reverseLevels = new JCheckBoxCustom("");
		checkBox_reverseLevels.setValueChangedListener(this);
		checkBox_reverseLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_reverseLevels, "3, 6");
		
		lblSoftChicks = new LabelWithState("Soft Chicks");
		lblSoftChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblSoftChicks, "1, 7");
		
		checkBox_softChicks = new JCheckBoxCustom("");
		checkBox_softChicks.setValueChangedListener(this);
		checkBox_softChicks.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_softChicks, "3, 7");
		
		lblAutoLevels = new LabelWithState("Auto Levels");
		lblAutoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblAutoLevels, "1, 8");
		
		checkBox_autoLevels = new JCheckBoxCustom("");
		checkBox_autoLevels.setValueChangedListener(this);
		checkBox_autoLevels.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(checkBox_autoLevels, "3, 8");
		
		lblNewAlgorithm = new LabelWithState("New Algorithm");
		lblNewAlgorithm.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_misc.add(lblNewAlgorithm, "1, 9");
		
		checkBox_new_algorithm = new JCheckBoxCustom("");
		checkBox_new_algorithm.setValueChangedListener(this);
		checkBox_new_algorithm.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_misc.add(checkBox_new_algorithm, "3, 9");
		
		lblChickDelay = new LabelWithState("Chick Delay");
		lblChickDelay.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblChickDelay, "1, 10");
		
		spin127Control_chickDelay = new Spin127Control();
		spin127Control_chickDelay.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 12));
		spin127Control_chickDelay.setEventListener(this);
		panel_misc.add(spin127Control_chickDelay, "3, 10, fill, fill");
		
		lblCcNumber = new LabelWithState("CC Number");
		lblCcNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCcNumber, "1, 11");
		
		spin127Control_cc = new Spin127Control();
		spin127Control_cc.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 12));
		spin127Control_cc.setEventListener(this);
		panel_misc.add(spin127Control_cc, "3, 11, fill, fill");
		
		lblCcRdcLvl = new LabelWithState("CC Reduction Lvl");
		lblCcRdcLvl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_misc.add(lblCcRdcLvl, "1, 12");
		
		spin127Control_ccRdcLvl = new Spin127Control();
		spin127Control_ccRdcLvl.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 12));
		spin127Control_ccRdcLvl.setEventListener(this);
		spin127Control_ccRdcLvl.getSpinner().setModel(new SpinnerNumberModel(0, 0, 3, 1));
		panel_misc.add(spin127Control_ccRdcLvl, "3, 12, fill, fill");
		
		JPanel panel_levels = new JPanel();
		tabbedPane.addTab("Levels", null, panel_levels, null);
		panel_levels.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblLow = new LabelWithState("Low");
		lblLow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblLow, "1, 1");
		
		spin1023Control_lowLevel = new Spin1023Control();
		spin1023Control_lowLevel.setEventListener(this);
		panel_levels.add(spin1023Control_lowLevel, "3, 1, fill, fill");
		
		lblHigh = new LabelWithState("High");
		lblHigh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHigh, "1, 2");
		
		spin1023Control_highLevel = new Spin1023Control();
		spin1023Control_highLevel.setEventListener(this);
		panel_levels.add(spin1023Control_highLevel, "3, 2, fill, fill");
		
		lblOpen = new LabelWithState("Open");
		lblOpen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblOpen, "1, 3");
		
		spin127Control_openLevel = new Spin127Control();
		spin127Control_openLevel.setEventListener(this);
		panel_levels.add(spin127Control_openLevel, "3, 3, fill, fill");
		
		lblSemiOpen = new LabelWithState("SemiOpen");
		lblSemiOpen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblSemiOpen, "1, 4");
		
		spin127Control_semiOpen = new Spin127Control();
		spin127Control_semiOpen.setEventListener(this);
		panel_levels.add(spin127Control_semiOpen, "3, 4, fill, fill");
		
		lblSemiOpen2 = new LabelWithState("SemiOpen2");
		lblSemiOpen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblSemiOpen2, "1, 5");
		
		spin127Control_semiOpen2 = new Spin127Control();
		spin127Control_semiOpen2.setEventListener(this);
		panel_levels.add(spin127Control_semiOpen2, "3, 5, fill, fill");
		
		lblHalfopen = new LabelWithState("HalfOpen");
		lblHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHalfopen, "1, 6");
		
		spin127Control_halfOpen = new Spin127Control();
		spin127Control_halfOpen.setEventListener(this);
		panel_levels.add(spin127Control_halfOpen, "3, 6, fill, fill");
		
		lblHalfopen2 = new LabelWithState("HalfOpen2");
		lblHalfopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblHalfopen2, "1, 7");
		
		spin127Control_halfOpen2 = new Spin127Control();
		spin127Control_halfOpen2.setEventListener(this);
		panel_levels.add(spin127Control_halfOpen2, "3, 7, fill, fill");
		
		lblClosed = new LabelWithState("Closed");
		lblClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblClosed, "1, 8");
		
		spin127Control_closed = new Spin127Control();
		spin127Control_closed.setEventListener(this);
		panel_levels.add(spin127Control_closed, "3, 8, fill, fill");
		
		lblChickthresh = new LabelWithState("ChickThresh");
		lblChickthresh.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_levels.add(lblChickthresh, "1, 9");
		
		spin127Control_chickThres = new Spin127Control();
		spin127Control_chickThres.setEventListener(this);
		panel_levels.add(spin127Control_chickThres, "3, 9, fill, fill");

		lblShortThresh = new LabelWithState("ShortChickThresh");
		lblShortThresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblShortThresh, "1, 10");
		
		spin127Control_shortThres = new Spin127Control();
		spin127Control_shortThres.setEventListener(this);
		panel_levels.add(spin127Control_shortThres, "3, 10, fill, fill");
		
		lblLongchickthresh = new LabelWithState("LongChickThresh");
		lblLongchickthresh.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_levels.add(lblLongchickthresh, "1, 11");
		
		spin127Control_longThres = new Spin127Control();
		spin127Control_longThres.setEventListener(this);
		panel_levels.add(spin127Control_longThres, "3, 11, fill, fill");
		
		lblChickMinVelocity = new LabelWithState("Chick Min Velocity");
		lblChickMinVelocity.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_levels.add(lblChickMinVelocity, "1, 12");
		
		spin1023Control_chickMinVelocity = new Spin1023Control();
		spin1023Control_chickMinVelocity.setEventListener(this);
		panel_levels.add(spin1023Control_chickMinVelocity, "3, 12, fill, fill");
		
		lblChickMaxVelocity = new LabelWithState("Chick Max Velocity");
		lblChickMaxVelocity.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_levels.add(lblChickMaxVelocity, "1, 13");
		
		spin1023Control_chickMaxVelocity = new Spin1023Control();
		spin1023Control_chickMaxVelocity.setEventListener(this);
		panel_levels.add(spin1023Control_chickMaxVelocity, "3, 13, fill, fill");
		
		lblChickDeadPeriod = new LabelWithState("Chick Dead Period");
		lblChickDeadPeriod.setFont(new Font("Dialog", Font.PLAIN, 10));
		panel_levels.add(lblChickDeadPeriod, "1, 14");
		
		spin1023Control_chickDeadPeriod = new Spin1023Control();
		spin1023Control_chickDeadPeriod.setEventListener(this);
		panel_levels.add(spin1023Control_chickDeadPeriod, "3, 14, fill, fill");
		
		
		JPanel panel_notes = new JPanel();
		tabbedPane.addTab("Notes", null, panel_notes, null);
		panel_notes.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:56dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:default:grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblBowSemiopen = new LabelWithState("Bow SemiOpen");
		lblBowSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiopen, "1, 1");
		
		noteSpinControl_bowSemiOpen = new NoteSpinControl(configFull);
		noteSpinControl_bowSemiOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bowSemiOpen, "3, 1, left, center");
		
		lblEdgeSemiopen = new LabelWithState("Edge SemiOpen");
		lblEdgeSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiopen, "1, 2");
		
		noteSpinControl_edgeSemiOpen = new NoteSpinControl(configFull);
		noteSpinControl_edgeSemiOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeSemiOpen, "3, 2, left, center");
		
		lblBellSemiopen = new LabelWithState("Bell SemiOpen");
		lblBellSemiopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiopen, "1, 3");
		
		noteSpinControl_bellSemiOpen = new NoteSpinControl(configFull);
		noteSpinControl_bellSemiOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bellSemiOpen, "3, 3, left, center");
		
		lblBowSemiopen2 = new LabelWithState("Bow SemiOpen2");
		lblBowSemiopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiopen2, "1, 4");
		
		noteSpinControl_bowSemiOpen2 = new NoteSpinControl(configFull);
		noteSpinControl_bowSemiOpen2.setEventListener(this);
		panel_notes.add(noteSpinControl_bowSemiOpen2, "3, 4, left, center");
		
		lblEdgeSemiopen2 = new LabelWithState("Edge SemiOpen2");
		lblEdgeSemiopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiopen2, "1, 5");
		
		noteSpinControl_edgeSemiOpen2 = new NoteSpinControl(configFull);
		noteSpinControl_edgeSemiOpen2.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeSemiOpen2, "3, 5, left, center");
		
		lblBellSemiopen2 = new LabelWithState("Bell SemiOpen2");
		lblBellSemiopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiopen2, "1, 6");
		
		noteSpinControl_bellSemiOpen2 = new NoteSpinControl(configFull);
		noteSpinControl_bellSemiOpen2.setEventListener(this);
		panel_notes.add(noteSpinControl_bellSemiOpen2, "3, 6, left, center");
		
		lblBowHalfopen = new LabelWithState("Bow HalfOpen");
		lblBowHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowHalfopen, "1, 7");
		
		noteSpinControl_bowHalfOpen = new NoteSpinControl(configFull);
		noteSpinControl_bowHalfOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bowHalfOpen, "3, 7, left, center");
		
		lblEdgeHalfopen = new LabelWithState("Edge HalfOpen");
		lblEdgeHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeHalfopen, "1, 8");
		
		noteSpinControl_edgeHalfOpen = new NoteSpinControl(configFull);
		noteSpinControl_edgeHalfOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeHalfOpen, "3, 8, left, center");
		
		lblBellHalfopen = new LabelWithState("Bell HalfOpen");
		lblBellHalfopen.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellHalfopen, "1, 9");
		
		noteSpinControl_bellHalfOpen = new NoteSpinControl(configFull);
		noteSpinControl_bellHalfOpen.setEventListener(this);
		panel_notes.add(noteSpinControl_bellHalfOpen, "3, 9, left, center");
		
		lblBowHalfopen2 = new LabelWithState("Bow HalfOpen2");
		lblBowHalfopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowHalfopen2, "1, 10");
		
		noteSpinControl_bowHalfOpen2 = new NoteSpinControl(configFull);
		noteSpinControl_bowHalfOpen2.setEventListener(this);
		panel_notes.add(noteSpinControl_bowHalfOpen2, "3, 10, left, center");
		
		lblEdgeHalfopen2 = new LabelWithState("Edge HalfOpen2");
		lblEdgeHalfopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeHalfopen2, "1, 11");
		
		noteSpinControl_edgeHalfOpen2 = new NoteSpinControl(configFull);
		noteSpinControl_edgeHalfOpen2.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeHalfOpen2, "3, 11, left, center");
		
		lblBellHalfopen2 = new LabelWithState("Bell HalfOpen2");
		lblBellHalfopen2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellHalfopen2, "1, 12");
		
		noteSpinControl_bellHalfOpen2 = new NoteSpinControl(configFull);
		noteSpinControl_bellHalfOpen2.setEventListener(this);
		panel_notes.add(noteSpinControl_bellHalfOpen2, "3, 12, left, center");
		
		lblBowSemiclosed = new LabelWithState("Bow SemiClosed");
		lblBowSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowSemiclosed, "1, 13");
		
		noteSpinControl_bowSemiClosed = new NoteSpinControl(configFull);
		noteSpinControl_bowSemiClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bowSemiClosed, "3, 13, left, center");
		
		lblEdgeSemiclosed = new LabelWithState("Edge SemiClosed");
		lblEdgeSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeSemiclosed, "1, 14");
		
		noteSpinControl_edgeSemiClosed = new NoteSpinControl(configFull);
		noteSpinControl_edgeSemiClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeSemiClosed, "3, 14, left, center");
		
		lblBellSemiclosed = new LabelWithState("Bell SemiClosed");
		lblBellSemiclosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellSemiclosed, "1, 15");
		
		noteSpinControl_bellSemiClosed = new NoteSpinControl(configFull);
		noteSpinControl_bellSemiClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bellSemiClosed, "3, 15, left, center");
		
		lblBowClosed = new LabelWithState("Bow Closed");
		lblBowClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBowClosed, "1, 16");
		
		noteSpinControl_bowClosed = new NoteSpinControl(configFull);
		noteSpinControl_bowClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bowClosed, "3, 16, left, center");
		
		lblEdgeClosed = new LabelWithState("Edge Closed");
		lblEdgeClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblEdgeClosed, "1, 17");
		
		noteSpinControl_edgeClosed = new NoteSpinControl(configFull);
		noteSpinControl_edgeClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_edgeClosed, "3, 17, left, center");
		
		lblBellClosed = new LabelWithState("Bell Closed");
		lblBellClosed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblBellClosed, "1, 18");
		
		noteSpinControl_bellClosed = new NoteSpinControl(configFull);
		noteSpinControl_bellClosed.setEventListener(this);
		panel_notes.add(noteSpinControl_bellClosed, "3, 18, left, center");
		
		lblChick = new LabelWithState("Chick");
		lblChick.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblChick, "1, 19");
		
		noteSpinControl_chick = new NoteSpinControl(configFull);
		noteSpinControl_chick.setEventListener(this);
		panel_notes.add(noteSpinControl_chick, "3, 19, left, center");
		
		lblSplash = new LabelWithState("Splash");
		lblSplash.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_notes.add(lblSplash, "1, 20");
		
		noteSpinControl_splash = new NoteSpinControl(configFull);
		noteSpinControl_splash.setEventListener(this);
		panel_notes.add(noteSpinControl_splash, "3, 20, left, center");

		controlsInited = true;
	}
	
	public void valueChanged() {
		if (controlsInited) {
			updateConfig();
			firePropertyChange("valueChanged", false, true);
		}
		updateSyncState();
	}

	private void updateSyncState() {
		if (configFull.configPedal.syncState == Constants.SYNC_STATE_UNKNOWN ) {
			lblType.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblCurve.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChickCurve.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblHihatInput.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblAltInput.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblReverseLevels.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblSoftChicks.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblAutoLevels.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblNewAlgorithm.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChickDelay.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblCcRdcLvl.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblCcNumber.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblLow.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblHigh.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblOpen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblSemiOpen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblSemiOpen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblHalfopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblHalfopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblClosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblShortThresh.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblLongchickthresh.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChickthresh.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChickMinVelocity.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChickMaxVelocity.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChickDeadPeriod.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBowSemiopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblEdgeSemiopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBellSemiopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBowSemiopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblEdgeSemiopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBellSemiopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBowHalfopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblEdgeHalfopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBellHalfopen.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBowHalfopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblEdgeHalfopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBellHalfopen2.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBowSemiclosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblEdgeSemiclosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBellSemiclosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBowClosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblEdgeClosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblBellClosed.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblChick.setSyncState(Constants.SYNC_STATE_UNKNOWN);
			lblSplash.setSyncState(Constants.SYNC_STATE_UNKNOWN);
		} else {
			lblType.setSyncNotSync(configFull.configPedal.type == moduleConfigFull.configPedal.type);
			lblCurve.setSyncNotSync(configFull.configPedal.curve == moduleConfigFull.configPedal.curve);
			lblChickCurve.setSyncNotSync(configFull.configPedal.chickCurve == moduleConfigFull.configPedal.chickCurve);
			lblHihatInput.setSyncNotSync(configFull.configPedal.hhInput == moduleConfigFull.configPedal.hhInput);
			lblAltInput.setSyncNotSync(configFull.configPedal.altIn == moduleConfigFull.configPedal.altIn);
			lblReverseLevels.setSyncNotSync(configFull.configPedal.reverseLevels == moduleConfigFull.configPedal.reverseLevels);
			lblSoftChicks.setSyncNotSync(configFull.configPedal.softChicks == moduleConfigFull.configPedal.softChicks);
			lblAutoLevels.setSyncNotSync(configFull.configPedal.autoLevels == moduleConfigFull.configPedal.autoLevels);
			lblNewAlgorithm.setSyncNotSync(configFull.configPedal.new_algorithm == moduleConfigFull.configPedal.new_algorithm);
			lblChickDelay.setSyncNotSync(configFull.configPedal.chickDelay == moduleConfigFull.configPedal.chickDelay);
			lblCcRdcLvl.setSyncNotSync(configFull.configPedal.ccRdcLvl == moduleConfigFull.configPedal.ccRdcLvl);
			lblCcNumber.setSyncNotSync(configFull.configPedal.cc == moduleConfigFull.configPedal.cc);
			lblLow.setSyncNotSync(configFull.configPedal.lowLevel == moduleConfigFull.configPedal.lowLevel);
			lblHigh.setSyncNotSync(configFull.configPedal.highLevel == moduleConfigFull.configPedal.highLevel);
			lblOpen.setSyncNotSync(configFull.configPedal.openLevel == moduleConfigFull.configPedal.openLevel);
			lblSemiOpen.setSyncNotSync(configFull.configPedal.semiOpenLevel == moduleConfigFull.configPedal.semiOpenLevel);
			lblSemiOpen2.setSyncNotSync(configFull.configPedal.semiOpenLevel2 == moduleConfigFull.configPedal.semiOpenLevel2);
			lblHalfopen.setSyncNotSync(configFull.configPedal.halfOpenLevel == moduleConfigFull.configPedal.halfOpenLevel);
			lblHalfopen2.setSyncNotSync(configFull.configPedal.halfOpenLevel2 == moduleConfigFull.configPedal.halfOpenLevel2);
			lblClosed.setSyncNotSync(configFull.configPedal.closedLevel == moduleConfigFull.configPedal.closedLevel);
			lblShortThresh.setSyncNotSync(configFull.configPedal.shortThres == moduleConfigFull.configPedal.shortThres);
			lblLongchickthresh.setSyncNotSync(configFull.configPedal.longThres == moduleConfigFull.configPedal.longThres);
			lblChickthresh.setSyncNotSync(configFull.configPedal.chickThres == moduleConfigFull.configPedal.chickThres);
			lblChickMinVelocity.setSyncNotSync(configFull.configPedal.chickParam1 == moduleConfigFull.configPedal.chickParam1);
			lblChickMaxVelocity.setSyncNotSync(configFull.configPedal.chickParam2 == moduleConfigFull.configPedal.chickParam2);
			lblChickDeadPeriod.setSyncNotSync(configFull.configPedal.chickParam3 == moduleConfigFull.configPedal.chickParam3);
			lblBowSemiopen.setSyncNotSync(configFull.configPedal.bowSemiOpenNote == moduleConfigFull.configPedal.bowSemiOpenNote);
			lblEdgeSemiopen.setSyncNotSync(configFull.configPedal.edgeSemiOpenNote == moduleConfigFull.configPedal.edgeSemiOpenNote);
			lblBellSemiopen.setSyncNotSync(configFull.configPedal.bellSemiOpenNote == moduleConfigFull.configPedal.bellSemiOpenNote);
			lblBowSemiopen2.setSyncNotSync(configFull.configPedal.bowSemiOpen2Note == moduleConfigFull.configPedal.bowSemiOpen2Note);
			lblEdgeSemiopen2.setSyncNotSync(configFull.configPedal.edgeSemiOpen2Note == moduleConfigFull.configPedal.edgeSemiOpen2Note);
			lblBellSemiopen2.setSyncNotSync(configFull.configPedal.bellSemiOpen2Note == moduleConfigFull.configPedal.bellSemiOpen2Note);
			lblBowHalfopen.setSyncNotSync(configFull.configPedal.bowHalfOpenNote == moduleConfigFull.configPedal.bowHalfOpenNote);
			lblEdgeHalfopen.setSyncNotSync(configFull.configPedal.edgeHalfOpenNote == moduleConfigFull.configPedal.edgeHalfOpenNote);
			lblBellHalfopen.setSyncNotSync(configFull.configPedal.bellHalfOpenNote == moduleConfigFull.configPedal.bellHalfOpenNote);
			lblBowHalfopen2.setSyncNotSync(configFull.configPedal.bowHalfOpen2Note == moduleConfigFull.configPedal.bowHalfOpen2Note);
			lblEdgeHalfopen2.setSyncNotSync(configFull.configPedal.edgeHalfOpen2Note == moduleConfigFull.configPedal.edgeHalfOpen2Note);
			lblBellHalfopen2.setSyncNotSync(configFull.configPedal.bellHalfOpen2Note == moduleConfigFull.configPedal.bellHalfOpen2Note);
			lblBowSemiclosed.setSyncNotSync(configFull.configPedal.bowSemiClosedNote == moduleConfigFull.configPedal.bowSemiClosedNote);
			lblEdgeSemiclosed.setSyncNotSync(configFull.configPedal.edgeSemiClosedNote == moduleConfigFull.configPedal.edgeSemiClosedNote);
			lblBellSemiclosed.setSyncNotSync(configFull.configPedal.bellSemiClosedNote == moduleConfigFull.configPedal.bellSemiClosedNote);
			lblBowClosed.setSyncNotSync(configFull.configPedal.bowClosedNote == moduleConfigFull.configPedal.bowClosedNote);
			lblEdgeClosed.setSyncNotSync(configFull.configPedal.edgeClosedNote == moduleConfigFull.configPedal.edgeClosedNote);
			lblBellClosed.setSyncNotSync(configFull.configPedal.bellClosedNote == moduleConfigFull.configPedal.bellClosedNote);
			lblChick.setSyncNotSync(configFull.configPedal.chickNote == moduleConfigFull.configPedal.chickNote);
			lblSplash.setSyncNotSync(configFull.configPedal.splashNote == moduleConfigFull.configPedal.splashNote);
		}		
	}

	public void updateControls() {
		comboBox_type.setSelectedIndexWithoutEvent(configFull.configPedal.type?1:0);
		comboBox_curve.setSelectedIndexWithoutEvent(configFull.configPedal.curve);
		comboBox_chickCurve.setSelectedIndexWithoutEvent(configFull.configPedal.chickCurve);
		comboBox_input.setSelectedIndexWithoutEvent((configFull.configPedal.hhInput-2)/2);
		checkBox_altInput.setValueWithoutEvent(configFull.configPedal.altIn);
		checkBox_reverseLevels.setValueWithoutEvent(configFull.configPedal.reverseLevels);
		checkBox_softChicks.setValueWithoutEvent(configFull.configPedal.softChicks);
		checkBox_autoLevels.setValueWithoutEvent(configFull.configPedal.autoLevels);
		checkBox_new_algorithm.setValueWithoutEvent(configFull.configPedal.new_algorithm);
		spin127Control_chickDelay.setValueWithoutEvents(configFull.configPedal.chickDelay);
		spin127Control_ccRdcLvl.setValueWithoutEvents(configFull.configPedal.ccRdcLvl);
		spin127Control_cc.setValueWithoutEvents(configFull.configPedal.cc);
		spin1023Control_lowLevel.setValueWithoutEvents(configFull.configPedal.lowLevel);
		spin1023Control_highLevel.setValueWithoutEvents(configFull.configPedal.highLevel);
		spin127Control_openLevel.setValueWithoutEvents(configFull.configPedal.openLevel);
		spin127Control_semiOpen.setValueWithoutEvents(configFull.configPedal.semiOpenLevel);
		spin127Control_semiOpen2.setValueWithoutEvents(configFull.configPedal.semiOpenLevel2);
		spin127Control_halfOpen.setValueWithoutEvents(configFull.configPedal.halfOpenLevel);
		spin127Control_halfOpen2.setValueWithoutEvents(configFull.configPedal.halfOpenLevel2);
		spin127Control_closed.setValueWithoutEvents(configFull.configPedal.closedLevel);
		spin127Control_shortThres.setValueWithoutEvents(configFull.configPedal.shortThres);
		spin127Control_longThres.setValueWithoutEvents(configFull.configPedal.longThres);
		spin127Control_chickThres.setValueWithoutEvents(configFull.configPedal.chickThres);
		spin1023Control_chickMinVelocity.setValueWithoutEvents(configFull.configPedal.chickParam1);
		spin1023Control_chickMaxVelocity.setValueWithoutEvents(configFull.configPedal.chickParam2);
		spin1023Control_chickDeadPeriod.setValueWithoutEvents(configFull.configPedal.chickParam3);
		noteSpinControl_bowSemiOpen.setValueWithoutEvents(configFull.configPedal.bowSemiOpenNote);
		noteSpinControl_edgeSemiOpen.setValueWithoutEvents(configFull.configPedal.edgeSemiOpenNote);
		noteSpinControl_bellSemiOpen.setValueWithoutEvents(configFull.configPedal.bellSemiOpenNote);
		noteSpinControl_bowSemiOpen2.setValueWithoutEvents(configFull.configPedal.bowSemiOpen2Note);
		noteSpinControl_edgeSemiOpen2.setValueWithoutEvents(configFull.configPedal.edgeSemiOpen2Note);
		noteSpinControl_bellSemiOpen2.setValueWithoutEvents(configFull.configPedal.bellSemiOpen2Note);
		noteSpinControl_bowHalfOpen.setValueWithoutEvents(configFull.configPedal.bowHalfOpenNote);
		noteSpinControl_edgeHalfOpen.setValueWithoutEvents(configFull.configPedal.edgeHalfOpenNote);
		noteSpinControl_bellHalfOpen.setValueWithoutEvents(configFull.configPedal.bellHalfOpenNote);
		noteSpinControl_bowHalfOpen2.setValueWithoutEvents(configFull.configPedal.bowHalfOpen2Note);
		noteSpinControl_edgeHalfOpen2.setValueWithoutEvents(configFull.configPedal.edgeHalfOpen2Note);
		noteSpinControl_bellHalfOpen2.setValueWithoutEvents(configFull.configPedal.bellHalfOpen2Note);
		noteSpinControl_bowSemiClosed.setValueWithoutEvents(configFull.configPedal.bowSemiClosedNote);
		noteSpinControl_edgeSemiClosed.setValueWithoutEvents(configFull.configPedal.edgeSemiClosedNote);
		noteSpinControl_bellSemiClosed.setValueWithoutEvents(configFull.configPedal.bellSemiClosedNote);
		noteSpinControl_bowClosed.setValueWithoutEvents(configFull.configPedal.bowClosedNote);
		noteSpinControl_edgeClosed.setValueWithoutEvents(configFull.configPedal.edgeClosedNote);
		noteSpinControl_bellClosed.setValueWithoutEvents(configFull.configPedal.bellClosedNote);
		noteSpinControl_chick.setValueWithoutEvents(configFull.configPedal.chickNote);
		noteSpinControl_splash.setValueWithoutEvents(configFull.configPedal.splashNote);
		updateSyncState();
	}
	
	public void updateConfig() {
		if (controlsInited) {
			configFull.configPedal.type = (comboBox_type.getSelectedIndex() != 0);
			configFull.configPedal.curve = comboBox_curve.getSelectedIndex();
			configFull.configPedal.chickCurve = comboBox_chickCurve.getSelectedIndex();
			configFull.configPedal.hhInput = (comboBox_input.getSelectedIndex()*2 + 2);
			configFull.configPedal.altIn = checkBox_altInput.isSelected();
			configFull.configPedal.reverseLevels = checkBox_reverseLevels.isSelected();
			configFull.configPedal.softChicks = checkBox_softChicks.isSelected();
			configFull.configPedal.autoLevels = checkBox_autoLevels.isSelected();
			configFull.configPedal.new_algorithm = checkBox_new_algorithm.isSelected();
			configFull.configPedal.chickDelay = (Integer)spin127Control_chickDelay.getSpinner().getValue();
			configFull.configPedal.cc = (Integer)spin127Control_cc.getSpinner().getValue();
			configFull.configPedal.ccRdcLvl = (Integer)spin127Control_ccRdcLvl.getSpinner().getValue();
			configFull.configPedal.openLevel = (Integer)spin127Control_openLevel.getSpinner().getValue();
			configFull.configPedal.semiOpenLevel = (Integer)spin127Control_semiOpen.getSpinner().getValue();
			configFull.configPedal.semiOpenLevel2 = (Integer)spin127Control_semiOpen2.getSpinner().getValue();
			configFull.configPedal.halfOpenLevel = (Integer)spin127Control_halfOpen.getSpinner().getValue();
			configFull.configPedal.halfOpenLevel2 = (Integer)spin127Control_halfOpen2.getSpinner().getValue();
			configFull.configPedal.closedLevel = (Integer)spin127Control_closed.getSpinner().getValue();
			configFull.configPedal.shortThres = (Integer)spin127Control_shortThres.getSpinner().getValue();
			configFull.configPedal.longThres = (Integer)spin127Control_longThres.getSpinner().getValue();
			configFull.configPedal.chickThres = (Integer)spin127Control_chickThres.getSpinner().getValue();
			configFull.configPedal.lowLevel = (Integer)spin1023Control_lowLevel.getSpinner().getValue();
			configFull.configPedal.highLevel = (Integer)spin1023Control_highLevel.getSpinner().getValue();
			configFull.configPedal.chickParam1 = (Integer)spin1023Control_chickMinVelocity.getSpinner().getValue();
			configFull.configPedal.chickParam2 = (Integer)spin1023Control_chickMaxVelocity.getSpinner().getValue();
			configFull.configPedal.chickParam3 = (Integer)spin1023Control_chickDeadPeriod.getSpinner().getValue();
		
			configFull.configPedal.bowSemiOpenNote = (Integer)noteSpinControl_bowSemiOpen.getValue();		
			configFull.configPedal.edgeSemiOpenNote = (Integer)noteSpinControl_edgeSemiOpen.getValue();
			configFull.configPedal.bellSemiOpenNote = (Integer)noteSpinControl_bellSemiOpen.getValue();
			configFull.configPedal.bowSemiOpen2Note = (Integer)noteSpinControl_bowSemiOpen2.getValue();		
			configFull.configPedal.edgeSemiOpen2Note = (Integer)noteSpinControl_edgeSemiOpen2.getValue();
			configFull.configPedal.bellSemiOpen2Note = (Integer)noteSpinControl_bellSemiOpen2.getValue();
			configFull.configPedal.bowHalfOpenNote = (Integer)noteSpinControl_bowHalfOpen.getValue();
			configFull.configPedal.edgeHalfOpenNote = (Integer)noteSpinControl_edgeHalfOpen.getValue();
			configFull.configPedal.bellHalfOpenNote = (Integer)noteSpinControl_bellHalfOpen.getValue();
			configFull.configPedal.bowHalfOpen2Note = (Integer)noteSpinControl_bowHalfOpen2.getValue();
			configFull.configPedal.edgeHalfOpen2Note = (Integer)noteSpinControl_edgeHalfOpen2.getValue();
			configFull.configPedal.bellHalfOpen2Note = (Integer)noteSpinControl_bellHalfOpen2.getValue();
			configFull.configPedal.bowSemiClosedNote = (Integer)noteSpinControl_bowSemiClosed.getValue();
			configFull.configPedal.edgeSemiClosedNote = (Integer)noteSpinControl_edgeSemiClosed.getValue();
			configFull.configPedal.bellSemiClosedNote = (Integer)noteSpinControl_bellSemiClosed.getValue();
			configFull.configPedal.bowClosedNote = (Integer)noteSpinControl_bowClosed.getValue();
			configFull.configPedal.edgeClosedNote = (Integer)noteSpinControl_edgeClosed.getValue();
			configFull.configPedal.bellClosedNote = (Integer)noteSpinControl_bellClosed.getValue();
			configFull.configPedal.chickNote = (Integer)noteSpinControl_chick.getValue();
			configFull.configPedal.splashNote = (Integer)noteSpinControl_splash.getValue();			
		}
	}
	
	public JButton getBtnGet() {
		return btnGet;
	}
	public JButton getBtnSend() {
		return btnSend;
	}
	
	public void updateInputCountsControls(int count) {
		if (count != prevInputsCount) {
			prevInputsCount = count;
			comboBox_input.selectEventsDisabled = 1;
			comboBox_input.removeAllItems();
	        for(int i=2; i<(count - 1); i++){
	        	comboBox_input.addItem(((Integer)i).toString());
	        	i++;
	        }
			comboBox_input.setSelectedIndexWithoutEvent((configFull.configPedal.hhInput-2)/2);
		}
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}
	public JButton getBtnSave() {
		return btnSave;
	}
}
