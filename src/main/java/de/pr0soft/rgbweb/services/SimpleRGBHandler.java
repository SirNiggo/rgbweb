package de.pr0soft.rgbweb.services;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiFactory;
import com.pi4j.io.spi.SpiMode;
import de.pr0soft.rgbweb.exceptions.InvalidByteArrayLengthException;
import de.pr0soft.rgbweb.exceptions.InvalidChannelBrightnessException;
import de.pr0soft.rgbweb.exceptions.InvalidPwmValueException;
import de.pr0soft.rgbweb.exceptions.SpiDeviceNotInitializedException;
import de.pr0soft.rgbweb.hardware.TLC59711;
import de.pr0soft.rgbweb.hardware.TLC59711RegisterOffset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SimpleRGBHandler implements RGBHandler {

    Logger logger = LoggerFactory.getLogger(SimpleRGBHandler.class);

    private TLC59711 tlc59711;

    public SimpleRGBHandler(
            @Value("${rgbweb.pwm.spi.channel}") int channel,
            @Value("${rgbweb.pwm.spi.speed}") int speed,
            @Value("${rgbweb.pwm.spi.mode}") int mode,
            @Autowired TLC59711 tlc59711
    ) throws IOException {
        this.tlc59711 = tlc59711;
        logger.info("Setting up SPI Interface for TLC59711 implementation.");
        this.tlc59711.setSpiDevice(SpiFactory.getInstance(SpiChannel.getByNumber(channel), speed, SpiMode.getByNumber(mode)));
    }

    @Override
    public void setColor(int r, int g, int b) throws InvalidPwmValueException, InvalidByteArrayLengthException, InvalidChannelBrightnessException, SpiDeviceNotInitializedException, IOException {
        this.logger.info("Setting new color for TLC59711: r="+r+" g="+g+" b="+b);
        this.tlc59711.setChannelValue(TLC59711RegisterOffset.R0, r);
        this.tlc59711.setChannelValue(TLC59711RegisterOffset.G0, g);
        this.tlc59711.setChannelValue(TLC59711RegisterOffset.B0, b);
        this.tlc59711.write();
    }

    @Override
    public void shutdown() {

    }
}
