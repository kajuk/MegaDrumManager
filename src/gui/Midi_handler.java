package gui;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Transmitter;
import javax.sound.midi.Receiver;
import	javax.sound.midi.Sequence;
import	javax.sound.midi.Sequencer;
//import	javax.sound.midi.Track;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.SysexMessage;

public class Midi_handler {

	private int nPorts;
	private int port;
	private int port_in;
	private int port_out;

	private MidiDevice midiout;
	private MidiDevice midiin;
	private MidiDevice.Info[]	aInfos;
	private Receiver receiver;
	private DumpReceiver dump_receiver;
	private Transmitter	transmitter;
	
	public int config_chain_id;
	public ConfigMisc config_misc;

	public Midi_handler () {
		midiin = null;
		midiout = null;
		receiver = null;
		transmitter = null;
		port_in = 0;
		port_out = 0;
		DumpReceiver rcvr = new DumpReceiver();
		config_chain_id = 0;
		config_misc = new ConfigMisc();
	}
	
	public void Close_all_ports() {
		if (midiout != null) {
			if (midiout.isOpen()) {
				midiout.close();
			}
		}
		if (midiin != null) {
			if (midiin.isOpen()) {
				midiin.close();
			}
		}
	}
	
//	public void send_sysex(Receiver rr, byte [] buf, int size) {
	public void send_sysex(byte [] buf) {
		SysexMessage	sysexMessage = new SysexMessage();

		try {
			sysexMessage.setMessage(buf, buf.length);
			receiver.send(sysexMessage, -1);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void send_config_misc() {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_misc.getSysex(config_chain_id));
			}
		}
	}
	
	public void Init_options (Options dialog_options) {
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		nPorts = aInfos.length;
		int[] table_in = new int[nPorts];
		int[] table_out = new int[nPorts];

		dialog_options.comboBox_MIDI_In.removeAllItems();
		dialog_options.comboBox_MIDI_Out.removeAllItems();
		
 		port = 0;
		for (int i = 0; i < nPorts; i++)
		{
			try
			{
				midiin = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiin.getMaxTransmitters() != 0)
				{
					table_in[port] = i;
					dialog_options.comboBox_MIDI_In.addItem(aInfos[i].getName());
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to list MIDI In devices");
			}
		}
		port = 0;		
		for (int i = 0; i < nPorts; i++)
		{
			try
			{
				midiout = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiout.getMaxReceivers() != 0)
				{
					table_out[port] = i;
					dialog_options.comboBox_MIDI_Out.addItem(aInfos[i].getName());
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to list MIDI Out devices");
			}
		}
		
		dialog_options.comboBox_MIDI_In.setSelectedIndex(port_in);
		dialog_options.comboBox_MIDI_Out.setSelectedIndex(port_out);
		dialog_options.config_applied = false;

		dialog_options.setVisible(true);

		if (dialog_options.config_applied) {
			port_in = dialog_options.midi_port_in;
			port_out = dialog_options.midi_port_out;
			midiout.close();
			midiin.close();
		    try {
		    	if (midiin.isOpen()) {
		    		midiin.close();
		    	}
		    	midiin = MidiSystem.getMidiDevice(aInfos[table_in[port_in]]);
		    	midiin.open();
				transmitter = midiin.getTransmitter();
				transmitter.setReceiver(dump_receiver);		    	
				//System.out.printf("\nOpened MIDI In port %d (%d) - %s.\n", port_in,table_in[port_in],aInfos[table_in[port_in]].getName());
		    } catch (MidiUnavailableException e) {
		    	Main_window.show_error("Cannot open MIDI In port");
		    }		    		    
		    try {
		    	if (midiout.isOpen()) {
		    		midiout.close();
		    	}
		    	midiout = MidiSystem.getMidiDevice(aInfos[table_out[port_out]]);
		    	midiout.open();
		    	receiver = midiout.getReceiver();
				//System.out.printf("\nOpened MIDI Out port %d (%d) - %s.\n", port_out,table_out[port_out],aInfos[table_out[port_out]].getName());
		    } catch (MidiUnavailableException e) {
		    	Main_window.show_error("Cannot open MIDI Out port");
		    }
		}

	}
}
