package gui;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.ShortMessage;
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

	//private int nPorts;
	//private int port;

	private MidiDevice midiin;
	private MidiDevice midiout;
	private MidiDevice midithru;
	private MidiDevice.Info[]	aInfos;
	private Receiver receiver;
	private Receiver thruReceiver;
	private DumpReceiver dump_receiver;
	private Transmitter	transmitter;
	
	public int config_chain_id;
	public ConfigMisc config_misc;
	public ConfigPedal config_pedal;
	public ConfigPad config_pad;
	public Config3rd config_3rd;
	public boolean getMidiBlocked;

	public Midi_handler () {
		midiin = null;
		midiout = null;
		midithru = null;
		receiver = null;
		thruReceiver = null;
		transmitter = null;
		config_chain_id = 0;
		config_misc = new ConfigMisc();
		config_pedal = new ConfigPedal();
		config_pad = new ConfigPad();
		config_3rd = new Config3rd();
		dump_receiver = new DumpReceiver();
		getMidiBlocked = false;
	}
	
	public void closeAllPorts() {
		if (midiin != null) {
			if (midiin.isOpen()) {
				midiin.close();
			}
		}
		if (midiout != null) {
			if (midiout.isOpen()) {
				midiout.close();
			}
		}
		if (midithru != null) {
			if (midithru.isOpen()) {
				midithru.close();
			}
		}
	}

	public void sendMidiShort(byte [] buf) {
		ShortMessage shortMessage = new ShortMessage();
		try {
			switch (buf.length) {
			case 1:
				shortMessage.setMessage(buf[0]);
				break;
			case 2:
				shortMessage.setMessage(buf[0], buf[1],0);
				break;
			default:
				shortMessage.setMessage(buf[0], buf[1],buf[2]);
				break;
			}
			thruReceiver.send(shortMessage, -1);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public void send_config_pedal() {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_pedal.getSysex(config_chain_id));
			}
		}
	}
	
	public void send_config_pad(int pad_id) {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_pad.getSysex(config_chain_id, pad_id));
			}
		}
	}

	public void send_config_3rd(int third_id) {
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(config_3rd.getSysex(config_chain_id, third_id));
			}
		}
	}
	
	public void request_config_misc() {
		byte [] sx = new byte[5];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_MISC;
		sx[4] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}

	public void request_config_pedal() {
		byte [] sx = new byte[5];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_PEDAL;
		sx[4] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}
	
	public void request_config_pad(int pad_id) {
		byte [] sx = new byte[6];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_PAD;
		sx[4] = (byte)pad_id;
		sx[5] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}

	public void request_config_3rd(int third_id) {
		byte [] sx = new byte[6];
		
		sx[0] = Constants.SYSEX_START;
		sx[1] = Constants.MD_SYSEX;
		sx[2] = (byte)config_chain_id;
		sx[3] = Constants.MD_SYSEX_3RD;
		sx[4] = (byte)third_id;
		sx[5] = Constants.SYSEX_END;
		if (midiout != null) {
			if (midiout.isOpen()) {
				send_sysex(sx);
			}
		}
	}

	public void clear_midi_input() {
		byte [] result;
		result = null;
		int size = 1;
		
		if (midiin != null) {
			if (midiin.isOpen()) {
				// Clear MIDI input buffer
				while (size > 0) {
					result = dump_receiver.getByteMessage();
					if (result == null)
					{
						size = 0;
						//out("input buffer cleared");
					}
					else
					{
						size = result.length;
					}			
				}
			}
		}
	}
	
	public void get_midi() {
		if (!getMidiBlocked) {
			byte [] buffer;
			buffer = null;
			int size = 0;
			
			if (midiin != null) {
				if (midiin.isOpen()) {
					buffer = dump_receiver.getByteMessage();
					
					if (buffer != null) {
						size = buffer.length;
						if (( buffer[0] == Constants.SYSEX_START) && (buffer[size-1] == Constants.SYSEX_END)) {
							if (buffer[1] == Constants.MD_SYSEX) {
								if (buffer[2] == (byte) config_chain_id) {
									switch(buffer[3]) {
									case Constants.MD_SYSEX_MISC:
										config_misc.setFromSysex(buffer);
										break;
									case Constants.MD_SYSEX_PEDAL:
										config_pedal.setFromSysex(buffer);
										break;
									case Constants.MD_SYSEX_PAD:
										config_pad.setFromSysex(buffer);
										break;
									case Constants.MD_SYSEX_3RD:
										config_3rd.setFromSysex(buffer);
										break;
									default:
										break;
									}
								}
							}
						} else {
							// TO-DO
							sendMidiShort(buffer);
						}
					}
				}
			}
		}
	}
	
	public String [] getMidiInList() {
		String [] list;
		int nPorts;
		int port;
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		nPorts = aInfos.length;
		int[] table = new int[nPorts];
		
		port = 0;		
		for (int i = 0; i < nPorts; i++) {
			try
			{
				midiin = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiin.getMaxTransmitters() != 0)
				{
					table[port] = i;
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to list MIDI In devices");
			}
			
		}
		list = new String[port];
		for (int i = 0; i < port; i++) {
			list[i] = aInfos[table[i]].getName();
		}
		
		return list;
	}
	
	public String [] getMidiOutList() {
		String [] list;
		int nPorts;
		int port;
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		nPorts = aInfos.length;
		int[] table = new int[nPorts];
		
		port = 0;		
		for (int i = 0; i < nPorts; i++) {
			try
			{
				midiout = MidiSystem.getMidiDevice(aInfos[i]);
				if (midiout.getMaxReceivers() != 0)
				{
					table[port] = i;
					port++;
				}
			}
			catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to list MIDI In devices");
			}
			
		}
		list = new String[port];
		for (int i = 0; i < port; i++) {
			list[i] = aInfos[table[i]].getName();
		}
		
		return list;		
	}
	
	public void initPorts(ConfigOptions options) {
		aInfos = MidiSystem.getMidiDeviceInfo(); 
		int nPorts;
		nPorts = aInfos.length;

		closeAllPorts();
		
		config_chain_id = options.chainId;
 		if (!options.MidiInName.equals("")) {
			try
			{
				for (int i = 0; i < nPorts; i++) {
					midiin = MidiSystem.getMidiDevice(aInfos[i]);
					if (midiin.getMaxTransmitters() != 0) {
						if (aInfos[i].getName().equals(options.MidiInName)) {
					    	midiin = MidiSystem.getMidiDevice(aInfos[i]);
					    	midiin.open();
							transmitter = midiin.getTransmitter();
							transmitter.setReceiver(dump_receiver);		
							break;
						}
					}
				}
			} catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to open MIDI In device");
			}
 		}

 		if (!options.MidiOutName.equals("")) {
			try
			{
				for (int i = 0; i < nPorts; i++) {
					midiout = MidiSystem.getMidiDevice(aInfos[i]);
					if (midiout.getMaxReceivers() != 0) {
						if (aInfos[i].getName().equals(options.MidiOutName)) {
					    	midiout = MidiSystem.getMidiDevice(aInfos[i]);
					    	midiout.open();
					    	receiver = midiout.getReceiver();
							break;
						}
					}
				}
			} catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to open MIDI Out device");
			}
 		}

 		if ((!options.MidiThruName.equals("")) && (options.useThruPort)) {
			try
			{
				for (int i = 0; i < nPorts; i++) {
					midithru = MidiSystem.getMidiDevice(aInfos[i]);
					if (midithru.getMaxReceivers() != 0) {
						if (aInfos[i].getName().equals(options.MidiThruName)) {
					    	midithru = MidiSystem.getMidiDevice(aInfos[i]);
					    	midithru.open();
					    	thruReceiver = midithru.getReceiver();
							break;
						}
					}
				}
			} catch (MidiUnavailableException e)
			{
				Main_window.show_error("Error trying to open MIDI Out device");
			}
 		}
 		
	}
	
	private static int readHex(DataInputStream d) throws IOException {
		StringBuffer curr;
		int result;

		curr = new StringBuffer("");
		
		curr.append(String.format("%c", d.readByte()).toUpperCase());
		curr.append(String.format("%c", d.readByte()).toUpperCase());
		
		result = Integer.parseInt(curr.toString(),16);
		//out(String.valueOf(result));
		//System.out.printf("%h\n", result);
		return result;
	}

	public static void write(Receiver rr, int[] buf, int ind, int size)
	{
		byte[] ba = new byte[(size*2) + 2];
		int p = 0;
		ba[p++] =(byte) 0xf0;
		while (size > 0)
		{
			//System.out.printf("%h\n", buf[ind]);
			ba[p++] = (byte) ((byte) (buf[ind]>>4) & 0x0f);
			//System.out.printf("%h\n", ba[p-1]);
			ba[p++] = (byte) ((byte) (buf[ind]) & 0x0f);
			//System.out.printf("%h\n", ba[p-1]);
			size --;
			ind++;
		}
		ba[p++] = (byte) 0xf7;
		SysexMessage msg = new SysexMessage();
		try {
			msg.setMessage(ba, p);
			rr.send(msg, -1);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final int Block_size = 22; // Doesn't work if more than 22

	public static void writeMid(Receiver rr, int[] buf, int ind, int size)
	{
		int p = 0;
		boolean running = true;
		while (running) {
			if ((p + Block_size) < size)
			{
				write(rr,buf,ind + p, Block_size);
				p += Block_size;
			}
			else
			{
				write(rr,buf,ind + p, size - p);
				running = false;
			}
		}
	}
	
	public int doFirmwareUpgrade (Upgrade parent, ConfigOptions options, File file) throws IOException {		
		int returnval = 0;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		int[] buffer = new int[0x40000];	// Data buffer for sending the data
		String	progressChars = "--\\\\||//";

		byte[] receivedBuffer;
		int receivedByte;		// One byte received from COM port
		int retries = 0;		// Number of tries so far
		int index;				// Index in the data buffer
		int frameSize;				// Number of bytes in the current frame
		int bufferSize = 0;			// Total number of bytes in the buffer
		int bytesSent = 0;			// Number of bytes sent so far
		int nBytes;
		int inDelay;

		closeAllPorts();
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bis = new BufferedInputStream(fis);
		dis = new DataInputStream(bis);

		initPorts(options);

		while (dis.available() > 1)
		{
			buffer[bufferSize] = readHex(dis);
			bufferSize++;
		}
		dis.close();
		bis.close();
		fis.close();
		System.out.printf("Firmware file is loaded\n");
		System.out.printf("Firmware size is %d bytes\n", bufferSize);
		
		int progressCharP = 0;	
		for(index = 0; index < bufferSize; index += frameSize)
		{
			frameSize = ((buffer[index] << 8) | buffer[index + 1]) + 2;
			nBytes = 1;
			while (nBytes > 0) {
				receivedBuffer = dump_receiver.getByteMessage();
				if (receivedBuffer == null)
				{
					nBytes = 0;
					//out("input buffer cleared");
				}
				else
				{
					nBytes = receivedBuffer.length;
				}
			}
			System.out.printf("\r                        \r" + 
				       "Transferring.. %c %d%% done.",
					progressChars.charAt(progressCharP),
					100 * bytesSent / bufferSize);
			progressCharP++;
			if(progressCharP >= progressChars.length())  progressCharP = 0;
			System.out.flush();	

			System.out.printf("index=%d , frameSize=%d \n", index, frameSize);

			writeMid(receiver, buffer, index, frameSize);

			nBytes = 0;
			//nBytes = 2;
			inDelay = 40;
			receivedBuffer = null;	
 			while ((nBytes == 0) && (inDelay > 0)) {

 				receivedBuffer = dump_receiver.getByteMessage();
 				if (receivedBuffer != null)
 				{
 					nBytes = receivedBuffer.length;
 					System.out.printf("!!! %d !!!!!!!!!\n", nBytes);
 				}
			    inDelay--;
			    try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
 			
 
 			receivedByte = 0;
			if (nBytes > 2)
			{
				receivedByte = receivedBuffer[1]<<4;
				receivedByte = receivedBuffer[2]|receivedByte;
				System.out.println(String.valueOf((int)receivedByte));
			} else {
				System.out.println("Read error\n");
				nBytes = 1;
				receivedByte = Constants.Error_CRC;
			}
			//receivedByte = Error_OK;
			if (nBytes > 0)
			{
				switch (receivedByte)
				{
					case Constants.Error_OK:
						bytesSent += frameSize;
						retries = 0;
						break;

					default: // Error_CRC:
						if (++retries < 4) {
							index -= frameSize;
							System.out.println("Retrying on CRC error\n");
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else 
						{
							System.out.println("\nCRC error. File damaged.\n");
							returnval = 1;
						}
						break;
				}
			}
			else
			{
				System.out.println("\nFailed: MegaDrum is not responding.\n");
				returnval = 1;
			}			
			
		}
		System.out.println("\rTransferring.. 100%% done.  \n");
		System.out.println("MegaDrum updated successfully.\n");
		
		
		return returnval;
	}
	
}
