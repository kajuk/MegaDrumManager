package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class ControlsPadsExtra extends JPanel {
	private Boolean changeEventsAllowed = false;
	
	private CurvesPaint paintPanel;
	//private int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};
	private ChangeListener spinnerChangeListener;
	private JSpinner [] spinners;
	private ConfigCurve [] configCurves;
	private int curvePointer;
	private int prevCurvePointer;
	private ConfigCustomName [] configCustomNames;
	private int customNamePointer;
	private int prevCustomNamePointer;
	private JComboBox comboBox_curveNumber;
	private JButton button_first;
	private JButton button_prev;
	private JButton button_next;
	private JButton button_last;
	private JButton button_curveGet;
	private JButton button_curveSend;
	private JButton button_curveGetAll;
	private JButton button_curveSendAll;
	private JButton btnCurveLoad;
	private JButton btnCurveSave;
	private JTabbedPane tabbedPane;
	private JPanel panelCurves;
	private JPanel panelCustomNames;
	private JPanel panelNamesGetSend;
	private JPanel panelNamesEdit;
	private JTextField textFieldEditName;
	private JComboBox comboBoxSelectName;
	private JLabel lblCustomNames;
	private JComboBox comboBoxCustomNamesCount;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JButton button_5;
	private JLabel lblEditSelectedName;
	private JLabel lblSelectNameTo;
	
	/**
	 * Create the panel.
	 */
	public ControlsPadsExtra() {
		configCurves = new ConfigCurve[Constants.CURVES_COUNT];
        for(int i=0; i<Constants.CURVES_COUNT; i++){
        	configCurves[i] = new ConfigCurve();
        }
        curvePointer = 0;
        prevCurvePointer = -1;
        
		configCustomNames = new ConfigCustomName[Constants.CUSTOM_NAMES_MAX];
        for(Integer i=0; i<Constants.CUSTOM_NAMES_MAX; i++){
        	configCustomNames[i] = new ConfigCustomName();
        }
        customNamePointer = 0;
        prevCustomNamePointer = -1;

        setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),}));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "1, 1");
		
		panelCurves = new JPanel();
		tabbedPane.addTab("Custom Curves", null, panelCurves, null);
		panelCurves.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JPanel panelCurvesGetSend = new JPanel();
		panelCurves.add(panelCurvesGetSend, "1, 1, left, default");
		panelCurvesGetSend.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,}));
		
		button_curveGet = new JButton("Get");
		button_curveGet.setMargin(new Insets(1, 4, 1, 4));
		button_curveGet.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveGet, "1, 1");
		
		button_curveSend = new JButton("Send");
		button_curveSend.setMargin(new Insets(1, 4, 1, 4));
		button_curveSend.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveSend, "3, 1");
		
		button_curveGetAll = new JButton("GetAll");
		button_curveGetAll.setMargin(new Insets(1, 0, 1, 0));
		button_curveGetAll.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveGetAll, "5, 1");
		
		button_curveSendAll = new JButton("SendAll");
		button_curveSendAll.setMargin(new Insets(1, 0, 1, 0));
		button_curveSendAll.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(button_curveSendAll, "7, 1");
		
		btnCurveLoad = new JButton("Load");
		btnCurveLoad.setMargin(new Insets(1, 2, 1, 2));
		btnCurveLoad.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(btnCurveLoad, "9, 1");
		
		btnCurveSave = new JButton("Save");
		btnCurveSave.setMargin(new Insets(1, 2, 1, 2));
		btnCurveSave.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesGetSend.add(btnCurveSave, "11, 1");
		
		JPanel panelCurvesSelection = new JPanel();
		panelCurves.add(panelCurvesSelection, "1, 2, left, default");
		panelCurvesSelection.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,}));
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.BOLD, 11));
		panelCurvesSelection.add(lblCurve, "2, 1");
		
		comboBox_curveNumber = new JComboBox();
		comboBox_curveNumber.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	prevCurvePointer = curvePointer;
		        	curvePointer = comboBox_curveNumber.getSelectedIndex();
		        	updateCurveControls();
		        }
			}
		});
		comboBox_curveNumber.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		comboBox_curveNumber.setSelectedIndex(0);
		comboBox_curveNumber.setMaximumRowCount(28);
		comboBox_curveNumber.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panelCurvesSelection.add(comboBox_curveNumber, "4, 1");
		
		button_first = new JButton("first");
		button_first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	curvePointer = 0;
	        	updateCurveControls();
			}
		});
		button_first.setMargin(new Insets(1, 8, 1, 8));
		button_first.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_first, "6, 1");
		
		button_prev = new JButton("prev");
		button_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	if (curvePointer > 0) {
	        		curvePointer--;
	        	}
	        	updateCurveControls();
			}
		});
		button_prev.setMargin(new Insets(1, 6, 1, 6));
		button_prev.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_prev, "8, 1");
		
		button_next = new JButton("next");
		button_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	if (curvePointer < (Constants.CURVES_COUNT - 1)) {
	        		curvePointer++;
	        	}
	        	updateCurveControls();
			}
		});
		button_next.setMargin(new Insets(1, 6, 1, 6));
		button_next.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_next, "10, 1");
		
		button_last = new JButton("last");
		button_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	curvePointer = Constants.CURVES_COUNT - 1;
	        	updateCurveControls();
			}
		});
		button_last.setMargin(new Insets(1, 8, 1, 8));
		button_last.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelCurvesSelection.add(button_last, "12, 1");
		
		paintPanel = new CurvesPaint();
		panelCurves.add(paintPanel, "1, 3, left, default");
		paintPanel.yValues = configCurves[curvePointer].yValues;
		
		JPanel panelControls = new JPanel();
		panelCurves.add(panelControls, "1, 4, left, default");
		panelControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),
				ColumnSpec.decode("36px"),},
			new RowSpec[] {
				RowSpec.decode("18px"),}));
		
		panelCustomNames = new JPanel();
		tabbedPane.addTab("Custom Names", null, panelCustomNames, null);
		panelCustomNames.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		panelNamesGetSend = new JPanel();
		panelCustomNames.add(panelNamesGetSend, "1, 1, fill, fill");
		panelNamesGetSend.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,}));
		
		lblCustomNames = new JLabel("Custom names:");
		lblCustomNames.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelNamesGetSend.add(lblCustomNames, "1, 1, right, default");
		
		comboBoxCustomNamesCount = new JComboBox();
		comboBoxCustomNamesCount.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		comboBoxCustomNamesCount.setModel(new DefaultComboBoxModel(new String[] {"2", "16", "32"}));
		panelNamesGetSend.add(comboBoxCustomNamesCount, "2, 1, fill, default");
		
		button = new JButton("Get");
		button.setMargin(new Insets(1, 4, 1, 4));
		button.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button, "4, 1");
		
		button_1 = new JButton("Send");
		button_1.setMargin(new Insets(1, 4, 1, 4));
		button_1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_1, "6, 1");
		
		button_2 = new JButton("GetAll");
		button_2.setMargin(new Insets(1, 0, 1, 0));
		button_2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_2, "8, 1");
		
		button_3 = new JButton("SendAll");
		button_3.setMargin(new Insets(1, 0, 1, 0));
		button_3.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_3, "10, 1");
		
		button_4 = new JButton("Load");
		button_4.setMargin(new Insets(1, 2, 1, 2));
		button_4.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_4, "12, 1");
		
		button_5 = new JButton("Save");
		button_5.setMargin(new Insets(1, 2, 1, 2));
		button_5.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelNamesGetSend.add(button_5, "14, 1");
		
		panelNamesEdit = new JPanel();
		panelCustomNames.add(panelNamesEdit, "1, 3, fill, fill");
		panelNamesEdit.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblEditSelectedName = new JLabel("Edit selected name:");
		panelNamesEdit.add(lblEditSelectedName, "1, 1, right, default");
		
		textFieldEditName = new JTextField();
		textFieldEditName.setColumns(10);
		panelNamesEdit.add(textFieldEditName, "3, 1, fill, default");
		
		lblSelectNameTo = new JLabel("Select name to edit:");
		panelNamesEdit.add(lblSelectNameTo, "1, 3, right, default");
		
		comboBoxSelectName = new JComboBox();
		comboBoxSelectName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					prevCustomNamePointer = customNamePointer;
					customNamePointer = comboBoxSelectName.getSelectedIndex();
					textFieldEditName.setText(configCustomNames[comboBoxSelectName.getSelectedIndex()].name);
				}
			}
		});
		
		updateCustomNameControls(Constants.CUSTOM_NAMES_MAX);
		
		panelNamesEdit.add(comboBoxSelectName, "3, 3, fill, default");

		paintPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				updateYvalues();
			}
		});
		paintPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				updateYvalues();
			}
		});
		for (int i = 0; i < 4; i++) {
			comboBox_curveNumber.addItem(i+1);
		}
		
		spinnerChangeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int id;
				id = ((CSpinner)arg0.getSource()).getIndex();
				configCurves[curvePointer].yValues[id] = ((Integer)((CSpinner)arg0.getSource()).getValue());
				//paintPanel.yValues = yValues;
				paintPanel.repaint();
				valueChanged();
			}
		};
		
		spinners = new CSpinner[9];
		for (int i = 0; i < 9; i++ ) {
			spinners[i] = new CSpinner(i);
			spinners[i].setModel(new SpinnerNumberModel(configCurves[curvePointer].yValues[i], 2, 255, 1));
			spinners[i].addChangeListener(spinnerChangeListener);
			panelControls.add(spinners[i], ((Integer)(i+1)).toString() + ", 1");
		}
		
		changeEventsAllowed = true;
	}    

	private void valueChanged() {
		if (changeEventsAllowed) {
			firePropertyChange("valueChanged", false, true);
		}
	}

	private void updateCurveControls() {
    	paintPanel.yValues = configCurves[curvePointer].yValues;
    	comboBox_curveNumber.setSelectedIndex(curvePointer);
    	updateYvalues();
    	paintPanel.repaint();
	}
	
	private void updateYvalues() {
		//yValues = paintPanel.yValues;
		if (spinners != null) {
			for (int i = 0; i < 9; i++) {
				spinners[i].setValue(configCurves[curvePointer].yValues[i]);
			}
		}
	}
	
	private void updateCustomNameControls(int count) {
		comboBoxSelectName.removeAllItems();
		for (int i = 0; i < count; i++) {
			comboBoxSelectName.addItem(configCustomNames[i].name);
		}		
		comboBoxSelectName.setSelectedIndex(customNamePointer);
	}
	
	public ConfigCurve getCurveConfig(int curve_id) {
		return configCurves[curve_id];
	}
	
	public void setCurveConfig(byte [] buffer,int curveId) {
		changeEventsAllowed = false;
		PropertiesConfiguration prop = new PropertiesConfiguration();
		PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(prop);
		ConfigCurve config = new ConfigCurve();
		configCurves[curveId].copyToPropertiesConfiguration(prop, layout, "", curveId);
		config.copyFromPropertiesConfiguration(prop, "",curveId);		
		Utils.copySysexToConfigCurve(buffer, config);
		config.copyToPropertiesConfiguration(prop, layout, "", curveId);
		configCurves[curveId].copyFromPropertiesConfiguration(prop, "", curveId);		
		updateYvalues();
		paintPanel.repaint();
		changeEventsAllowed = true;
	}

	public void loadFromConfigFull (ConfigFull config) {
		changeEventsAllowed = false;
		configCurves = config.configCurves;
		updateCurveControls();
		configCustomNames = config.configCustomNames;
		for (int i = 0; i < comboBoxCustomNamesCount.getItemCount(); i++) {
			if (comboBoxCustomNamesCount.getItemAt(i).toString().equals(((Integer)config.customNamesCount).toString())) {
				comboBoxCustomNamesCount.setSelectedIndex(i);
			}
		}		
		updateCustomNameControls(config.customNamesCount);
		changeEventsAllowed = true;
	}
	
	public int getCurvePointer() {
		return curvePointer;
	}
	public JButton getButton_curveGet() {
		return button_curveGet;
	}
	public JButton getButton_curveSend() {
		return button_curveSend;
	}
	public JButton getButton_curveGetAll() {
		return button_curveGetAll;
	}
	public JButton getButton_curveSendAll() {
		return button_curveSendAll;
	}
	public JButton getBtnCurveLoad() {
		return btnCurveLoad;
	}
	public JButton getBtnCurveSave() {
		return btnCurveSave;
	}
}
