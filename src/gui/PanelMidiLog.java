package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Timer;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;

class LogStore {
	public int note = 0;
	public int level = 0;
	public int timeDiff = 0;
	public Color color = Color.BLUE;
}

public class PanelMidiLog extends JPanel {
	private JPanel panelBars;
	private PanelMidiBar [] panelsMidiBar;  
	private ColumnSpec [] columnSpecs;
	private LogStore [] logStore; 
	private int storePointer = 0;
	private long prevTime;
	private JLabel lblHitsIntervalsmilliseconds;
	private JLabel lblNotesNumbers;
	private PanelMidiLevelBar panelHiHatBar;
	private JLabel lblHihatPosition;
	private JPanel panelHiHat;
	private JLabel lblL;
	private JLabel lblL_1;
	private JPanel panelCombined;
	private JPanel panelScroll;
	private PanelMidiScroll panelMidiScroll;
	private JPanel panelScrollControls;
	private JLabel lblScrollSpeed;
	private JCheckBox checkBoxAutoPause;
	private JLabel lblAutoPause;
	private JToggleButton tglbtnPause;
	private JScrollBar scrollBar;
	private JPanel panel;
	private JComboBox comboBoxBarsCount;
	private JLabel lblBarsNumber;
	
	public int barsCount;
	private JLabel lblHeadHit;
	private JLabel lblRimHit;
	private JLabel lblrdZoneHit;
	private JLabel lblChokeOn;
	private JLabel lblChokeOff;
	private JPanel panelHeadColor;
	private JPanel panelRimColor;
	private JPanel panel3rdColor;
	private JPanel panelChokeOnColor;
	private JPanel panelChokeOffColor;
	private JLabel lblUnknown;
	private JPanel panelUnknownColor;

	/**
	 * Create the panel.
	 */
	public PanelMidiLog(int bars) {
		barsCount = bars;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));
		panelCombined = new JPanel();
		add(panelCombined, "1, 1, left, top");
		panelCombined.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:pref"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblHitsIntervalsmilliseconds = new JLabel("hits intervals (milliseconds)");
		lblHitsIntervalsmilliseconds.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelCombined.add(lblHitsIntervalsmilliseconds, "1, 2");
		
		panelBars = new JPanel();
//		panelBars.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				showNewHit((int)(Math.random()*127),(int)(Math.random()*127), Color.BLUE);
//			}
//		});
		
		lblHihatPosition = new JLabel("HiHat");
		lblHihatPosition.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelCombined.add(lblHihatPosition, "3, 2");
		panelCombined.add(panelBars, "1, 4, fill, fill");
		
		panelHiHat = new JPanel();
		panelCombined.add(panelHiHat, "3, 4, fill, fill");
		panelHiHat.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblL = new JLabel("Open");
		lblL.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panelHiHat.add(lblL, "1, 2, center, default");
		
		panelHiHatBar = new PanelMidiLevelBar();
		panelHiHat.add(panelHiHatBar, "1, 4, center, top");
		
		lblL_1 = new JLabel("Closed");
		lblL_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		panelHiHat.add(lblL_1, "1, 6, center, default");
		
		panel = new JPanel();
		panelCombined.add(panel, "5, 4, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				ColumnSpec.decode("2dlu"),
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		comboBoxBarsCount = new JComboBox();
		comboBoxBarsCount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
		        if (arg0.getStateChange() == ItemEvent.SELECTED) {
		        	barsCount = comboBoxBarsCount.getSelectedIndex()*4 + 16;
		        	reSetPanelBars();
		    		panelMidiScroll.reSetSize(new Dimension(panelBars.getPreferredSize().width, 132));
	        		firePropertyChange("resize", false, true);
		        }
			}
		});
		comboBoxBarsCount.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		comboBoxBarsCount.setModel(new DefaultComboBoxModel(new String[] {"16", "20", "24", "28", "32"}));
		panel.add(comboBoxBarsCount, "1, 1, fill, default");
		
		lblBarsNumber = new JLabel("Bars number");
		lblBarsNumber.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblBarsNumber, "3, 1");
		
		panelHeadColor = new JPanel();
		panelHeadColor.setBackground(Constants.MD_HEAD_COLOR);
		panel.add(panelHeadColor, "1, 3, fill, fill");
		
		lblHeadHit = new JLabel("Head hit");
		lblHeadHit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblHeadHit, "3, 3");
		
		panelRimColor = new JPanel();
		panelRimColor.setBackground(Constants.MD_RIM_COLOR);
		panel.add(panelRimColor, "1, 5, fill, fill");
		
		lblRimHit = new JLabel("Rim hit");
		lblRimHit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblRimHit, "3, 5");
		
		panel3rdColor = new JPanel();
		panel3rdColor.setBackground(Constants.MD_3RD_COLOR);
		panel.add(panel3rdColor, "1, 7, fill, fill");
		
		lblrdZoneHit = new JLabel("3rd zone hit");
		lblrdZoneHit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblrdZoneHit, "3, 7");
		
		panelChokeOnColor = new JPanel();
		panelChokeOnColor.setBackground(Constants.MD_AFTERTOUCH_ON_COLOR);
		panel.add(panelChokeOnColor, "1, 9, fill, fill");
		
		lblChokeOn = new JLabel("Choke on");
		lblChokeOn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblChokeOn, "3, 9");
		
		panelChokeOffColor = new JPanel();
		panelChokeOffColor.setBackground(Constants.MD_AFTERTOUCH_OFF_COLOR);
		panel.add(panelChokeOffColor, "1, 11, fill, fill");
		
		lblChokeOff = new JLabel("Choke off");
		lblChokeOff.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblChokeOff, "3, 11");
		
		panelUnknownColor = new JPanel();
		panelUnknownColor.setBackground(Constants.MD_UNKNOWN_COLOR);
		panel.add(panelUnknownColor, "1, 13, fill, fill");
		
		lblUnknown = new JLabel("Unknown");
		lblUnknown.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panel.add(lblUnknown, "3, 13");
		
		lblNotesNumbers = new JLabel("notes numbers");
		lblNotesNumbers.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelCombined.add(lblNotesNumbers, "1, 6");
		
		panelScroll = new JPanel();
		add(panelScroll, "1, 3, fill, fill");
		panelScroll.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("pref:grow"),}));
		
//		panelBars.setLayout(new FormLayout(columnSpecs,
//			new RowSpec[] {
//				FormFactory.RELATED_GAP_ROWSPEC,
//				FormFactory.DEFAULT_ROWSPEC,}));
		
		reSetPanelBars();

		panelMidiScroll = new PanelMidiScroll(new Dimension(panelBars.getPreferredSize().width, 128));
		panelMidiScroll.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		panelScroll.add(panelMidiScroll, "1, 1, fill, fill");
		
		panelScrollControls = new JPanel();
		panelScrollControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelScroll.add(panelScrollControls, "3, 1, fill, fill");
		panelScrollControls.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("2dlu"),
				ColumnSpec.decode("right:pref"),
				ColumnSpec.decode("2dlu"),
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		scrollBar = new JScrollBar();
		scrollBar.setValue(50);
		scrollBar.setPreferredSize(new Dimension(80, 14));
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				panelMidiScroll.reSetTimer((Integer)scrollBar.getValue());

			}
		});
		scrollBar.setMinimum(10);
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		panelScrollControls.add(scrollBar, "2, 2");
		
		lblScrollSpeed = new JLabel("Scroll speed");
		lblScrollSpeed.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelScrollControls.add(lblScrollSpeed, "4, 2");
		
		checkBoxAutoPause = new JCheckBox("");
		checkBoxAutoPause.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelMidiScroll.autoPause = checkBoxAutoPause.isSelected();
			}
		});
		
		tglbtnPause = new JToggleButton("Pause");
		tglbtnPause.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelMidiScroll.pauseScroll = tglbtnPause.isSelected();
			}
		});
		tglbtnPause.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelScrollControls.add(tglbtnPause, "2, 4");
		panelScrollControls.add(checkBoxAutoPause, "2, 6");
		
		lblAutoPause = new JLabel("Auto pause");
		lblAutoPause.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		panelScrollControls.add(lblAutoPause, "4, 6");
		panelMidiScroll.autoPause = checkBoxAutoPause.isSelected();
		
		panelMidiScroll.reSetSize(new Dimension(panelBars.getPreferredSize().width, 132));
		panelMidiScroll.reSetTimer((Integer)scrollBar.getValue());
		prevTime = System.nanoTime();

	}
	
	public void showNewHit(int note, int level, Color color) {
		int timeDiff;
		storePointer++;
		if (storePointer >= barsCount) {
			storePointer = 0;
		}
		timeDiff = (int)((System.nanoTime() - prevTime)/1000000);
		prevTime = System.nanoTime();
		logStore[storePointer].timeDiff = timeDiff;
		logStore[storePointer].note = note;
		logStore[storePointer].level = level;
		logStore[storePointer].color = color;
		int p = storePointer;
		for (int i=0;i<barsCount;i++) {
			panelsMidiBar[barsCount - i - 1].noteNumber = logStore[p].note; 
			panelsMidiBar[barsCount - i - 1].level = logStore[p].level;
			panelsMidiBar[barsCount - i - 1].color = logStore[p].color;
			panelsMidiBar[barsCount - i - 1].timeDiff = logStore[p].timeDiff;
			panelsMidiBar[barsCount - i - 1].updateToValues();
			p--;
			if (p<0) {
				p = barsCount - 1;
			}
		}
		if (panelMidiScroll != null) {
			panelMidiScroll.showHit(level, Color.BLACK);
		}
	}
	public void showHiHatLevel(int level, Color color) {
		panelHiHatBar.level = level;
		panelHiHatBar.fgColor = color;
		panelHiHatBar.repaint();
	}
	
	public void reSetPanelBars() {
		panelBars.removeAll();
		panelsMidiBar = new PanelMidiBar[barsCount];
		logStore = new LogStore[barsCount];
		for (int i=0; i<barsCount;i++) {
			panelsMidiBar[i] = new PanelMidiBar();
			panelBars.add(panelsMidiBar[i], ((Integer)((i+1)*2)).toString()+", 2");
			logStore[i] = new LogStore();
		}
	}
	
	public void pauseLiveScroll(boolean b) {
		panelMidiScroll.pauseScroll = b;
		tglbtnPause.setSelected(b);
	}
}
