# RGBWeb

RGBWeb is a spring based service for controlling your RGB Strip!

## Setup

- Install Maven dependencies
- Setup a Raspberry-Pi (max. 3B+) with Raspbian-Lite
- Install openjdk-8-jdk on the Raspberry `sudo apt install openjdk-8-jdk`
- Install wiringpi on the raspberry `sudo apt install wiringpi`
- Set up IntelliJ to run the software on the Pi:
![IntelliJ Setup](https://raw.githubusercontent.com/SirNiggo/rgbweb/main/intellij-setup.png)
- Setup Raspbery Pi wiring according to fritzing schematics in /fritzing folder
- Run the application
## Roadmap

- Finish and test Adafruit PWM Module interaction with Pi4j
- Implement simple Spring controller for setting RGB colors
- Implement webhook Controller for Twitch
- Implement WS28xx compatibility
- Implement more types of color modes/changes
## Contribute
Want to add your own code and ideas? Please fork the repository and create pull requests!