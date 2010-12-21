package gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSpinner;
import java.awt.Insets;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerModel;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class NoteSpinControl extends JPanel {
	private JLabel lblNoteName;
	private JSpinner spinner;

	private int octave;
	private int note_pointer;
	private static final String [] note_names = {"C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#", "A ", "A#", "B "};
	/**
	 * Create the panel.
	 */
	public NoteSpinControl() {
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				short note_number;
				short base;
				String note_text;
				note_number = ((Short)spinner.getValue()).shortValue();
				if (note_number > 0) {
					octave = note_number/12;
					base = (short)(octave*12);
					note_pointer = note_number - base;
					note_text = note_names[note_pointer] + " " + Integer.toString(octave - 1);
					lblNoteName.setText(note_text);
				} else {
					lblNoteName.setText("Disabled");
				}
			}
		});
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("47px"),
				ColumnSpec.decode("63px"),},
			new RowSpec[] {
				RowSpec.decode("20px"),}));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spinner.setModel(new SpinnerNumberModel(new Short((short) 0), new Short((short) 0), new Short((short) 127), new Short((short) 1)));
		add(spinner, "1, 1, fill, fill");
		
		lblNoteName = new JLabel("Disabled");
		lblNoteName.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		add(lblNoteName, "2, 1, fill, fill");
	}

	public SpinnerModel getSpinnerModel() {
		return spinner.getModel();
	}
	public void setSpinnerModel(SpinnerModel model) {
		spinner.setModel(model);
	}
	
	public short getValue() {
		return ((Short)spinner.getValue()).shortValue();
	}
	
	public void setValue(short value) {
		if ((value >= 0 ) && (value < 128)) {
			spinner.setValue(value);
		}
	}
}