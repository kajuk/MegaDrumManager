package gui;

public class ConfigPad {
	public boolean changed;
	public short note;
	public short channel;
	public short curve;
	public short threshold;
	public short retrigger;
	public short levelMax;
	public short minScan;
	public boolean type;
	public boolean autoLevel;
	public boolean dual;
	public boolean threeWay;
	public short gain;
	public short xtalkLevel;
	public short xtalkGroup;
	public short dynTime;
	public short dynLevel;
	public short compression;
	public short shift;
	public short name;
	public short altNote;
	public short pressrollNote;
	
	private byte [] sysex_byte;
	private byte [] sysex_short;
	private byte [] sysex;
	private byte flags;

	
	public ConfigPad (){
		changed = false;
		sysex = new byte[Constants.MD_SYSEX_PAD_SIZE];

	}
	
	public byte [] getSysex(int chain_id) {
		int i = 0;

		sysex[i++] = Constants.SYSEX_START;
		sysex[i++] = Constants.MD_SYSEX;
		sysex[i++] = (byte) chain_id;
		sysex[i++] = Constants.MD_SYSEX_PAD;
		sysex[i++] = 0;	//Placeholder got pad id
		
		sysex_byte = Utils.byte2sysex((byte)note);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)((channel<<4)|(curve)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)threshold);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)retrigger);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_short = Utils.short2sysex((short)levelMax);
		sysex[i++] = sysex_short[0];
		sysex[i++] = sysex_short[1];
		sysex[i++] = sysex_short[2];
		sysex[i++] = sysex_short[3];
		sysex_byte = Utils.byte2sysex((byte)minScan);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		flags = (byte) (((type)?1:0)|(((autoLevel)?1:0)<<1)|(((dual)?1:0)<<2)|(((threeWay)?1:0)<<3)
				|(gain<<4));
		sysex_byte = Utils.byte2sysex(flags);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)((xtalkGroup<<3)|(xtalkLevel)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)((dynLevel<<4)|(dynTime)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)((shift<<3)|(compression)));
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];		
		sysex_byte = Utils.byte2sysex((byte)name);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)pressrollNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex_byte = Utils.byte2sysex((byte)altNote);
		sysex[i++] = sysex_byte[0];
		sysex[i++] = sysex_byte[1];
		sysex[i++] = Constants.SYSEX_END;
		return sysex;
	}

	public void setFromSysex(byte [] sx) {
		int i = 5;
		if (sx.length >= Constants.MD_SYSEX_PAD_SIZE) {
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			note = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			flags = Utils.sysex2byte(sysex_byte);
			curve = (short)(flags&0x0f);
			channel = (short)((flags&0xf0)>>4);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			threshold = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			retrigger = Utils.sysex2byte(sysex_byte);
			sysex_short[0] = sx[i++];
			sysex_short[0] = sx[i++];
			sysex_short[0] = sx[i++];
			sysex_short[0] = sx[i++];
			levelMax = Utils.sysex2short(sysex_short);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			minScan = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			flags = Utils.sysex2byte(sysex_byte);
			gain = (short)((flags&0xf0)>>4);
			type = ((flags&1) != 0);
			autoLevel = ((flags&(1<<1)) != 0);
			dual = ((flags&(1<<2)) != 0);
			threeWay = ((flags&(1<<3)) != 0);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			flags = Utils.sysex2byte(sysex_byte);
			xtalkGroup = (short)((flags&0x38)>>3);
			xtalkLevel = (short)(flags&0x07);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			flags = Utils.sysex2byte(sysex_byte);
			dynTime = (short)(flags&0x0f);
			dynLevel = (short)((flags&0xf0)>>4);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			flags = Utils.sysex2byte(sysex_byte);
			shift = (short)((flags&0x38)>>3);
			compression = (short)(flags&0x07);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			name = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			pressrollNote = Utils.sysex2byte(sysex_byte);
			sysex_byte[0] = sx[i++];
			sysex_byte[1] = sx[i++];
			altNote = Utils.sysex2byte(sysex_byte);
			changed = true;
		}
	}

}
