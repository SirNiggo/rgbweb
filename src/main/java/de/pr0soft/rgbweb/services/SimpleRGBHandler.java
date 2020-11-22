package de.pr0soft.rgbweb.services;
import de.pr0soft.rgbweb.components.PWMBoard;
import de.pr0soft.rgbweb.exceptions.RGBContextIsShutdowned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleRGBHandler implements RGBHandler {

    @Autowired
    PWMBoard pwmBoard;

    @Override
    public void setColor(int r, int g, int b) throws RGBContextIsShutdowned {

    }

    @Override
    public void shutdown() {

    }
}
