package gui;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.awt.Dialog.ModalityType;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Upgrade extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4617003899287250352L;
	private Midi_handler midi_handler;
	private JTextField textField_fileName;
	private FileManager fileManager;
	private File file;
	private ConfigOptions configOptions;
	private JButton btnStart;
	private JButton btnCancel;
	private JButton btnClose;
	private Upgrade mySelf;
	private JProgressBar progressBar;
	private Color pbBgColor;
	private Color pbFgColor;
	//private Thread midiThread;
	ExecutorService backgroundExec = Executors.newCachedThreadPool();
	Future<?>  midiTask = null;
	private JTextPane txtInstruction;
	public boolean reOpenMidi = false;
	
	
	/**
	 * Create the panel.
	 */
	public Upgrade(Midi_handler mh, FileManager fm) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				if (!textField_fileName.getText().isEmpty()) {
					btnStart.setEnabled(true);
					progressBar.setString(null);
					progressBar.setBackground(pbBgColor);
					progressBar.setForeground(pbFgColor);
				}
			}
		});
		mySelf = this;
		setModalityType(ModalityType.APPLICATION_MODAL);
		midi_handler = mh;
		fileManager = fm;
		file = null;
		setResizable(false);
		setName("upgradeDialog");
		setTitle("MegaDrum Firmware Upgrade");
		setBounds(100, 100, 495, 400);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		txtInstruction = new JTextPane();
		txtInstruction.setEditable(false);
		getContentPane().add(txtInstruction, "2, 2, fill, fill");
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "2, 4, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblMegadrumFirmware = new JLabel("MegaDrum Firmware:");
		panel_1.add(lblMegadrumFirmware, "2, 2, right, default");
		
		textField_fileName = new JTextField();
		textField_fileName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				file = new File(textField_fileName.getText());
				if (file.isFile()) {
					btnStart.setEnabled(true);					
				} else {
					btnStart.setEnabled(false);					
				}
			}
		});
		panel_1.add(textField_fileName, "4, 2, fill, default");
		textField_fileName.setColumns(10);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				file = fileManager.selectFirmwareFile(configOptions);
				if (file != null) {
					textField_fileName.setText(file.getAbsolutePath());
					btnStart.setEnabled(true);
				}
			}
		});
		panel_1.add(btnOpen, "6, 2");
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		pbBgColor = progressBar.getBackground();
		pbFgColor = progressBar.getForeground();

		getContentPane().add(progressBar, "2, 6");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "2, 8, fill, fill");
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel.setEnabled(true);
				btnStart.setEnabled(false);
				btnClose.setEnabled(false);
				progressBar.setString(null);
				progressBar.setBackground(pbBgColor);
				progressBar.setForeground(pbFgColor);
				callUpgradeThread();
			}
		});
		btnStart.setEnabled(false);
		panel.add(btnStart);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				midi_handler.upgradeCancelled = true;
				btnStart.setEnabled(true);
				btnCancel.setEnabled(false);
				btnClose.setEnabled(true);
			}
		});
		btnCancel.setEnabled(false);
		panel.add(btnCancel);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(false);
				btnCancel.setEnabled(false);
				setVisible(false);
				if (midi_handler.isMidiOpen()) {
					reOpenMidi = true;
					//midi_handler.closeAllPorts();
					//midi_handler.initPorts();
					//midi_handler.requestVersionAndMcu();
				}
			}
		});
		panel.add(btnClose);
	}
	
	public void setVisible(boolean v) {
		super.setVisible(v);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
	}
	
	private void callUpgradeThread() {
            midiTask = backgroundExec.submit(new Runnable() {
                public void run() {
                   //while (true) {
                       //if (Thread.currentThread().isInterrupted()) {
                       //    break;
                       //}
   						try {
   							midi_handler.upgradeCancelled = false;
							midi_handler.doFirmwareUpgrade(mySelf, file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                   //}
                }
            });
            

//            backgroundExec.execute(new Runnable() {
//            public void run() {
//                try {
//					midi_handler.doFirmwareUpgrade(mySelf, configOptions, file);
//                } finally {
//                    GuiExecutor.instance().execute(new Runnable() {
//                        public void run() {
//                            btnStart.setEnabled(true);
//                            btnCancel.setEnabled(true);
//                        }
//                    });
//                }
//            }
//        });

	}
	
//	private void callUpgradeThread() {
//        midiThread = new Thread(new Runnable() {
//            public void run() {
//                // since we're not on the EDT,
//                // let's put the setVisible-code
//                // into the Event Dispatching Queue
//                SwingUtilities.invokeLater( new Runnable() {
//                    public void run() {
//        				try {
//        					midi_handler.upgradeCancelled = false;
//        					midi_handler.doFirmwareUpgrade(mySelf, configOptions, file);
//        				} catch (IOException e1) {
//        					// TODO Auto-generated catch block
//        					e1.printStackTrace();
//        				}
//                   }
//                });
//            }
//
//		});
//		midiThread.setPriority( Thread.NORM_PRIORITY );
//		midiThread.run();
//	}

	public void midiFinished(int result, String resultString) {
		if (result > 0) {
			progressBar.setBackground(Color.YELLOW);
			progressBar.setForeground(Color.RED);
		} else {
			progressBar.setBackground(pbBgColor);
			progressBar.setForeground(pbFgColor);
		}
		progressBar.setString(resultString);
		btnStart.setEnabled(true);
		btnCancel.setEnabled(false);
		btnClose.setEnabled(true);
	}
	
	public void setProgressBar(int v) {
		progressBar.setValue(v);
		Rectangle progressRect = progressBar.getBounds();
		progressRect.x = 0;
		progressRect.y = 0;
		//progressBar.paintImmediately( progressRect );
		//progressBar.repaint(progressRect);
		progressBar.repaint(10);
	}

	
	public void setConfigOptions(ConfigOptions config) {
		configOptions = config;
		if (configOptions.mcuType < 3) {
			txtInstruction.setText(Constants.UPGRADE_INSTRUCTION_ATMEGA);
		} else {
			txtInstruction.setText(Constants.UPGRADE_INSTRUCTION_ARM);			
		}
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JButton getBtnCancel() {
		return btnCancel;
	}
	public JButton getBtnStart() {
		return btnStart;
	}
}
