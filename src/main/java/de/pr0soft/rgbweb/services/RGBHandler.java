package de.pr0soft.rgbweb.services;

import de.pr0soft.rgbweb.exceptions.InvalidByteArrayLengthException;
import de.pr0soft.rgbweb.exceptions.InvalidChannelBrightnessException;
import de.pr0soft.rgbweb.exceptions.InvalidPwmValueException;
import de.pr0soft.rgbweb.exceptions.SpiDeviceNotInitializedException;

import java.io.IOException;

public interface RGBHandler {

    void setColor(int r, int g, int b) throws InvalidPwmValueException, InvalidByteArrayLengthException, InvalidChannelBrightnessException, SpiDeviceNotInitializedException, IOException;
    void shutdown();

}
