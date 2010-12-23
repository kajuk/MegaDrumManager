package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ThirdZoneControls extends JPanel {
	private NoteSpinControl noteSpinControl_note;
	private NoteSpinControl noteSpinControl_altNote;
	private NoteSpinControl noteSpinControl_pressrollNote;
	private NoteSpinControl noteSpinControl_dampenedNote;
	private JSpinner spinner_threshold;
	private JSpinner spinner_midPointWidth;
	private JSlider slider_midPoint;

	private Config3rd config3rd;

	/**
	 * Create the panel.
	 */
	public ThirdZoneControls() {
		config3rd = new Config3rd();
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:42dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("60dlu:grow"),
				ColumnSpec.decode("10dlu"),
				ColumnSpec.decode("42dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu"),},
			new RowSpec[] {
				RowSpec.decode("13dlu"),
				RowSpec.decode("13dlu"),
				RowSpec.decode("13dlu"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblNote, "1, 1, right, default");
		
		noteSpinControl_note = new NoteSpinControl();
		add(noteSpinControl_note, "3, 1, fill, fill");
		
		JLabel lblMidpoint = new JLabel("MidPoint");
		lblMidpoint.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpoint, "5, 1, right, default");
		
		slider_midPoint = new JSlider();
		slider_midPoint.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config3rd.threshold = (short)((config3rd.threshold&0x0f)|((slider_midPoint.getValue()&0x0f)<<4));
				//updateControls();
			}
		});
		slider_midPoint.setPaintLabels(true);
		slider_midPoint.setSnapToTicks(true);
		slider_midPoint.setPaintTicks(true);
		slider_midPoint.setMinorTickSpacing(1);
		slider_midPoint.setValue(7);
		slider_midPoint.setMaximum(15);
		add(slider_midPoint, "7, 1, fill, fill");
		
		JLabel lblAltNote = new JLabel("Alt Note");
		lblAltNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblAltNote, "1, 2, right, default");
		
		noteSpinControl_altNote = new NoteSpinControl();
		add(noteSpinControl_altNote, "3, 2, fill, fill");
		
		JLabel lblMidpointWidth = new JLabel("MidPoint Width");
		lblMidpointWidth.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblMidpointWidth, "5, 2, right, default");
		
		spinner_midPointWidth = new JSpinner();
		spinner_midPointWidth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config3rd.threshold = (short)((config3rd.threshold&0xf0)|((((Integer)spinner_midPointWidth.getValue())&0x0f)));
				//updateControls();
			}
		});
		spinner_midPointWidth.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 15), new Short((short) 1)));
		add(spinner_midPointWidth, "7, 2, left, fill");
		
		JLabel lblPressrollNote = new JLabel("Pressroll Note");
		lblPressrollNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblPressrollNote, "1, 3, right, default");
		
		noteSpinControl_pressrollNote = new NoteSpinControl();
		add(noteSpinControl_pressrollNote, "3, 3, fill, fill");
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblThreshold, "5, 3, right, default");
		
		spinner_threshold = new JSpinner();
		spinner_threshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config3rd.threshold = (Short)spinner_threshold.getValue();
				//updateControls();
		}
		});
		spinner_threshold.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner_threshold, "7, 3, left, fill");
		
		JLabel lblDampenedNote = new JLabel("Dampened Note");
		lblDampenedNote.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		add(lblDampenedNote, "1, 4");
		
		noteSpinControl_dampenedNote = new NoteSpinControl();
		add(noteSpinControl_dampenedNote, "3, 4, fill, fill");

	}
	private void updateControls() {
		noteSpinControl_note.getSpinner().setValue(config3rd.note);
		noteSpinControl_altNote.getSpinner().setValue(config3rd.altNote);
		noteSpinControl_pressrollNote.getSpinner().setValue(config3rd.pressrollNote);
		noteSpinControl_dampenedNote.getSpinner().setValue(config3rd.dampenedNote);
		spinner_threshold.setValue(config3rd.threshold);
		spinner_midPointWidth.setValue(config3rd.threshold&0x0f);
		slider_midPoint.setValue((config3rd.threshold&0xf0)>>4);
	}
	
	private void updateConfig() {
		config3rd.note = ((Short)noteSpinControl_note.getSpinner().getValue()).shortValue();
		config3rd.altNote = ((Short)noteSpinControl_altNote.getSpinner().getValue()).shortValue();
		config3rd.pressrollNote = ((Short)noteSpinControl_pressrollNote.getSpinner().getValue()).shortValue();
		config3rd.dampenedNote = ((Short)noteSpinControl_dampenedNote.getSpinner().getValue()).shortValue();
		config3rd.threshold = (Short)spinner_threshold.getValue();
		//config3rd.threshold = (short)((Integer)spinner_threshold.getValue()).shortValue();
	}
	
	public void setConfig(Config3rd config) {
		config3rd.copyVarsFrom(config);
		updateControls();
	}
	
	public Config3rd getConfig() {
		updateConfig();
		return config3rd;
	}

}
