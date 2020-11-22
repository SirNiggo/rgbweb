package de.pr0soft.rgbweb.hardware;

import de.pr0soft.rgbweb.exceptions.InvalidByteArrayLengthException;
import de.pr0soft.rgbweb.exceptions.InvalidChannelBrightnessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TLC59711Register {

    Logger logger = LoggerFactory.getLogger(TLC59711Register.class);

    private byte[] shiftReg;

    TLC59711Register() {
        this.shiftReg = new byte[28];
    }

    public byte[] getBytes(TLC59711RegisterOffset tlc59711RegisterOffset) {
        return Arrays.copyOfRange(shiftReg, tlc59711RegisterOffset.getOffset(), tlc59711RegisterOffset.getOffset() + tlc59711RegisterOffset.getLength());
    }

    public void setBytes(TLC59711RegisterOffset tlc59711RegisterOffset, byte[] data) throws InvalidByteArrayLengthException {
        if (tlc59711RegisterOffset.getLength() != data.length) {
            throw new InvalidByteArrayLengthException("Channel offset had length of "+ tlc59711RegisterOffset.getLength()+" but provided data has length of "+data.length);
        }
        int offset = tlc59711RegisterOffset.getOffset();
        for (byte dataPart : data) {
            shiftReg[offset] = dataPart;
            offset++;
        }
    }

    public void setChipState(int blueBrightness, int greenBrightness, int redBrightness, boolean outtmg, boolean extgclk, boolean tmgrst, boolean dsprpt, boolean blank) throws InvalidChannelBrightnessException {
        if (blueBrightness < 0 || blueBrightness > 127) {
            throw new InvalidChannelBrightnessException("Blue Channels brightness can not be higher than 127 or lower than 0.");
        }
        if (greenBrightness < 0 || greenBrightness > 127) {
            throw new InvalidChannelBrightnessException("Green Channels brightness can not be higher than 127 or lower than 0.");
        }
        if (redBrightness < 0 || redBrightness > 127) {
            throw new InvalidChannelBrightnessException("Red Channels brightness can not be higher than 127 or lower than 0.");
        }
        // Update the preamble of chip state in the first 4 bytes (32-bits)
        // with the write command, function control bits, and brightness
        // control register values.
        shiftReg[0] = 0x25;
        // Lower two bits control OUTTMG and EXTGCLK bits, set them
        // as appropriate.
        shiftReg[0] = shiftIn(shiftReg[0], outtmg);
        shiftReg[0] = shiftIn(shiftReg[0], extgclk);
        // Next byte contains remaining function control state and start of
        // brightness control bits.
        shiftReg[1] = 0x00;
        shiftReg[1] = shiftIn(shiftReg[1], tmgrst);
        shiftReg[1] = shiftIn(shiftReg[1], dsprpt);
        shiftReg[1] = shiftIn(shiftReg[1], blank);
        // Top 5 bits from BC blue channel.
        shiftReg[1] <<= 5;
        shiftReg[1] |= (blueBrightness >> 2) & 0b11111;
        // Next byte contains lower 2 bits from BC blue channel and upper 6
        // from BC green channel.
        shiftReg[2] = (byte) (blueBrightness & 0b11);
        shiftReg[2] <<= 6;
        shiftReg[2] |= (greenBrightness >> 1) & 0b111111;
        // Final byte contains lower 1 bit from BC green and 7 bits from BC
        // red channel.
        shiftReg[3] = (byte) (greenBrightness & 0b1);
        shiftReg[3] <<= 7;
        shiftReg[3] |= redBrightness & 0b1111111;
        // The remaining bytes in the shift register are the channel PWM
        // values that have already been set by the user.
    }

    private byte shiftIn(byte sourceByte, boolean val) {
        sourceByte <<= 1;
        if (val) {
            sourceByte |= 0x01;
        }
        return sourceByte;
    }

    public byte[] getShiftReg() {
        return shiftReg;
    }

    public void logRegisterContents() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Register currently has following bytes set:\n");
        int index = 0;
        for (byte regPart : this.shiftReg) {
            stringBuilder.append("[").append(index).append("] ").append(String.format("%8s", Integer.toBinaryString(regPart & 0xFF)).replace(' ', '0')).append("\t");
            index++;
        }
        logger.info(stringBuilder.toString());
    }
}
