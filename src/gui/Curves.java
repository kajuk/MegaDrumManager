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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Curves extends JPanel {
	private CurvesPaint paintPanel;
	//private int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};
	private ChangeListener spinnerChangeListener;
	private JSpinner [] spinners;
	private ConfigCurve [] configCurves;
	private int curvePointer;
	private int prevCurvePointer;
	private JComboBox comboBox_curveNumber;
	private JButton button_first;
	private JButton button_prev;
	private JButton button_next;
	private JButton button_last;
	private JButton button_get;
	private JButton button_send;
	private JButton button_getAll;
	private JButton button_sendAll;
	

	/**
	 * Create the panel.
	 */
	public Curves() {
		configCurves = new ConfigCurve[Constants.CURVES_COUNT];
        for(int i=0; i<Constants.CURVES_COUNT; i++){
        	configCurves[i] = new ConfigCurve();
        }
        curvePointer = 0;
        prevCurvePointer = -1;
        
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:281px:grow"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),
				RowSpec.decode("1dlu"),
				RowSpec.decode("12dlu"),
				RowSpec.decode("279px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("12dlu:grow"),}));
		
		paintPanel = new CurvesPaint();
		paintPanel.yValues = configCurves[curvePointer].yValues;
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
		
		JPanel panel = new JPanel();
		add(panel, "1, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		button_get = new JButton("Get");
		button_get.setMargin(new Insets(1, 4, 1, 4));
		button_get.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_get, "1, 1");
		
		button_send = new JButton("Send");
		button_send.setMargin(new Insets(1, 4, 1, 4));
		button_send.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_send, "3, 1");
		
		button_getAll = new JButton("GetAll");
		button_getAll.setMargin(new Insets(1, 4, 1, 4));
		button_getAll.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_getAll, "5, 1");
		
		button_sendAll = new JButton("SendAll");
		button_sendAll.setMargin(new Insets(1, 4, 1, 4));
		button_sendAll.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel.add(button_sendAll, "7, 1");
		
		JPanel panel_1 = new JPanel();
		add(panel_1, "1, 3, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("24dlu"),
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("24dlu"),},
			new RowSpec[] {
				RowSpec.decode("fill:12dlu"),}));
		
		JLabel lblCurve = new JLabel("Curve");
		lblCurve.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_1.add(lblCurve, "2, 1, right, fill");
		
		comboBox_curveNumber = new JComboBox();
		comboBox_curveNumber.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	prevCurvePointer = curvePointer;
		        	curvePointer = comboBox_curveNumber.getSelectedIndex();
		        	paintPanel.yValues = configCurves[curvePointer].yValues;
		        	updateYvalues();
		        	paintPanel.repaint();
		        }
			}
		});
		for (int i = 0; i < 4; i++) {
			comboBox_curveNumber.addItem(i+1);
		}
		comboBox_curveNumber.setSelectedIndex(0);
		comboBox_curveNumber.setMaximumRowCount(28);
		comboBox_curveNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_1.add(comboBox_curveNumber, "4, 1, fill, fill");
		
		button_first = new JButton("first");
		button_first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	curvePointer = 0;
	        	paintPanel.yValues = configCurves[curvePointer].yValues;
	        	updateYvalues();
	        	paintPanel.repaint();
			}
		});
		button_first.setMargin(new Insets(1, 10, 1, 10));
		button_first.setFont(new Font("Segoe UI", Font.PLAIN, 9));
		panel_1.add(button_first, "6, 1");
		
		button_prev = new JButton("prev");
		button_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	if (curvePointer > 0) {
	        		curvePointer--;
	        	}
	        	paintPanel.yValues = configCurves[curvePointer].yValues;
	        	updateYvalues();
	        	paintPanel.repaint();
			}
		});
		button_prev.setMargin(new Insets(1, 8, 1, 8));
		button_prev.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_1.add(button_prev, "8, 1");
		
		button_next = new JButton("next");
		button_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	if (curvePointer < (Constants.CURVES_COUNT - 1)) {
	        		curvePointer++;
	        	}
	        	paintPanel.yValues = configCurves[curvePointer].yValues;
	        	updateYvalues();
	        	paintPanel.repaint();
			}
		});
		button_next.setMargin(new Insets(1, 8, 1, 8));
		button_next.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_1.add(button_next, "10, 1");
		
		button_last = new JButton("last");
		button_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	        	prevCurvePointer = curvePointer;
	        	curvePointer = Constants.CURVES_COUNT - 1;
	        	paintPanel.yValues = configCurves[curvePointer].yValues;
	        	updateYvalues();
	        	paintPanel.repaint();
			}
		});
		button_last.setMargin(new Insets(1, 8, 1, 8));
		button_last.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel_1.add(button_last, "12, 1");
		add(paintPanel, "1, 4, left, fill");
		
		JPanel panelControls = new JPanel();
		add(panelControls, "1, 6, fill, fill");
		panelControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),
				ColumnSpec.decode("17dlu"),},
			new RowSpec[] {
				RowSpec.decode("12dlu"),}));
		
		spinnerChangeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int id;
				id = ((CSpinner)arg0.getSource()).getIndex();
				configCurves[curvePointer].yValues[id] = ((Integer)((CSpinner)arg0.getSource()).getValue());
				//paintPanel.yValues = yValues;
				paintPanel.repaint();
			}
		};
		
		spinners = new CSpinner[9];
		for (int i = 0; i < 9; i++ ) {
			spinners[i] = new CSpinner(i);
			spinners[i].setModel(new SpinnerNumberModel(configCurves[curvePointer].yValues[i], 2, 255, 1));
			spinners[i].addChangeListener(spinnerChangeListener);
			panelControls.add(spinners[i], ((Integer)(i+1)).toString() + ", 1");
		}
		
	}    

	private void updateYvalues() {
		//yValues = paintPanel.yValues;
		if (spinners != null) {
			for (int i = 0; i < 9; i++) {
				spinners[i].setValue(configCurves[curvePointer].yValues[i]);
			}
		}
	}
	
	public ConfigCurve getConfig(int curve_id) {
		return configCurves[curve_id];
	}
	
	public void setConfig(ConfigCurve config,int curve_id) {
		configCurves[curve_id].copyVarsFrom(config);
		updateYvalues();
		paintPanel.repaint();
	}

	
	public int getCurvePointer() {
		return curvePointer;
	}
	public JButton getButton_get() {
		return button_get;
	}
	public JButton getButton_send() {
		return button_send;
	}
	public JButton getButton_getAll() {
		return button_getAll;
	}
	public JButton getButton_sendAll() {
		return button_sendAll;
	}
}
