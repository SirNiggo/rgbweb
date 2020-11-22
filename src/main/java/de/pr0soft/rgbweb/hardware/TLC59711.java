package de.pr0soft.rgbweb.hardware;

import com.pi4j.io.spi.SpiDevice;
import de.pr0soft.rgbweb.exceptions.InvalidByteArrayLengthException;
import de.pr0soft.rgbweb.exceptions.InvalidChannelBrightnessException;
import de.pr0soft.rgbweb.exceptions.InvalidPwmValueException;
import de.pr0soft.rgbweb.exceptions.SpiDeviceNotInitializedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Inspired by https://github.com/adafruit/Adafruit_CircuitPython_TLC59711/blob/826f13075408048e0fb993b59f59f342fc326e3c/adafruit_tlc59711.py

@Component
public class TLC59711 {

    Logger logger = LoggerFactory.getLogger(TLC59711.class);

    private TLC59711Register tlc59711Register;

    private SpiDevice spiDevice;

    private int brightnessChannelRed, brightnessChannelGreen, brightnessChannelBlue;
    private boolean outtmg, extgclk, tmgrst, dsprpt, blank;

    public TLC59711(
            @Autowired TLC59711Register tlc59711Register
    ) {
        this.tlc59711Register = tlc59711Register;

        brightnessChannelRed = 127;
        brightnessChannelGreen = 127;
        brightnessChannelBlue = 127;

        outtmg = true;
        extgclk = false;
        tmgrst = true;
        dsprpt = true;
        blank = false;
    }

    public void setSpiDevice(SpiDevice spiDevice) {
        this.spiDevice = spiDevice;
    }

    public void write() throws SpiDeviceNotInitializedException, InvalidChannelBrightnessException, IOException {
        if (this.spiDevice == null) {
            throw new SpiDeviceNotInitializedException("The SPI Device has not been set up yet. Writing is not possible.");
        }
        // Configure the Chips State
        this.tlc59711Register.setChipState(brightnessChannelBlue, brightnessChannelGreen, brightnessChannelRed, outtmg, extgclk, tmgrst, dsprpt, blank);
        // Now write out the
        // the entire set of bytes.  Note there is no latch or other
        // explicit line to tell the chip when finished, it expects 28 bytes.
        byte[] shiftRegister = this.tlc59711Register.getShiftReg();
        tlc59711Register.logRegisterContents();
        logger.info("Writing register to SPI device.");
        this.spiDevice.write(shiftRegister);
    }

    public int getChannelValue(TLC59711RegisterOffset tlc59711RegisterOffset) {
        byte[] byteValue = this.tlc59711Register.getBytes(tlc59711RegisterOffset);
        return ((byteValue[0] & 0xff) << 8 | (byteValue[1] & 0xff));
    }

    public void setChannelValue(TLC59711RegisterOffset tlc59711RegisterOffset, int value) throws InvalidByteArrayLengthException, InvalidPwmValueException {
        if (value < 0 || value > 65535) {
            throw new InvalidPwmValueException("PWM Value for a specific channel can not be higher than 65535 or smaller than 0.");
        }
        byte[] result = new byte[2];
        result[0] = (byte) (value >>> 8);
        result[1] = (byte) value;
        this.tlc59711Register.setBytes(tlc59711RegisterOffset, result);
    }

    public int getBrightnessChannelRed() {
        return brightnessChannelRed;
    }

    public void setBrightnessChannelRed(int brightnessChannelRed) throws InvalidChannelBrightnessException {
        if (brightnessChannelRed < 0 || brightnessChannelRed > 127) {
            throw new InvalidChannelBrightnessException("Brightness of red channel must not be smaller than 0 or higher than 127.");
        }
        this.brightnessChannelRed = brightnessChannelRed;
    }

    public int getBrightnessChannelGreen() {
        return brightnessChannelGreen;
    }

    public void setBrightnessChannelGreen(int brightnessChannelGreen) throws InvalidChannelBrightnessException {
        if (brightnessChannelGreen < 0 || brightnessChannelGreen > 127) {
            throw new InvalidChannelBrightnessException("Brightness of red channel must not be smaller than 0 or higher than 127.");
        }
        this.brightnessChannelGreen = brightnessChannelGreen;
    }

    public int getBrightnessChannelBlue() {
        return brightnessChannelBlue;
    }

    public void setBrightnessChannelBlue(int brightnessChannelBlue) throws InvalidChannelBrightnessException {
        if (brightnessChannelBlue < 0 || brightnessChannelBlue > 127) {
            throw new InvalidChannelBrightnessException("Brightness of red channel must not be smaller than 0 or higher than 127.");
        }
        this.brightnessChannelBlue = brightnessChannelBlue;
    }
}
