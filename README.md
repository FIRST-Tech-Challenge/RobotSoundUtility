This repository contains a single utility OpMode for playing sounds from a Robot Controller.

# Robot Sounds Competition

FIRST is looking to retire the Star Wars sounds that the SDK currently uses to indicate system status.  We decided that it would be fun to replace them with a set of community generated sounds.  So, FIRST is offering FTC teams the opportunity to create and submit a set of 5 replacement system sounds.

The 5 status sounds that need to be replaced are:

 - running:  When the Driver Station detects an active Robot Controller.
 - connect:  When a gamepad controller is connected to the Driver Station.
 - disconnect: When a gamepad controller is disconnected from the Driver Station.
 - warning:  When a non-fatal Warning occurs.
 - error:  When a fatal Error occurs.

As always, there are some requirements and some guidelines.

## Requirements

 - All submissions must be completely original and not contain any copyrighted elements.
 - FIRST must be assigned full rights to use these sounds. Credit will be given to the submitting team on the app’s About page.
 - Sounds may not contain any spoken words
 - Sounds may combine live recordings with digitally generated elements.
 - All 5 sounds must be included, and the base file names must be “running”, “connect”, “disconnect”, “warning” and “error”.
 - Sound files must packaged into a zip file named “NewSoundsNUM.zip” where NUM is the submitting team number
 - The Zip file must be emailed to FIRST at [ftctech@firstinspires.org](mailto:ftctech@firstinspires.org) with a subject line of: “NEW SOUNDS NUM” where NUM is the submitting team number.
 - The submission email must be sent by April 30, 2025 / 11:59:59 ET.
 - Only the first submission from each team will be considered.

_Note:  It is FIRST’s goal to use all 5 sounds from a single submission, but we may ultimately decide to mix & match from more than one team’s submission at our discretion._

## Guidelines

 - The sounds should not be based on any specific FTC game season theme.
 - Sounds should be short.  The “Running” sound may be up to 1.5 seconds long, but the remaining sounds should each be less than 0.5 seconds in duration.
 - Sounds should use the full dynamic range available to enable them to be heard clearly in a noisy environment.
 - Sounds should not contain any leading or trailing “dead air”.
 - Sounds should be MONO with a bit rate of less than 1.4 Mbps.
 - Sound files will be converted to .wav files to be included in the SDK, so providing the sounds in this format will ensure the best representation.  The other acceptable format is .mp3
 - A sample OpMode has been provided to enable teams to try out their proposed sounds.  See the PlaySounds.java file in this repository.
