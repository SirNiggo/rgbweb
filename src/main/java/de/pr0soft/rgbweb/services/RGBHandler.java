package de.pr0soft.rgbweb.services;

import de.pr0soft.rgbweb.exceptions.RGBContextIsShutdowned;

public interface RGBHandler {

    void setColor(int r, int g, int b) throws RGBContextIsShutdowned;
    void shutdown();

}
