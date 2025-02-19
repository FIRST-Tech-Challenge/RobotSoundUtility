/*
 *  Copyright (c) 2025 FIRST and Phil Malone
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.lang.reflect.Field;

/*
 * This OpMode demonstrates how to use Android Studio, or OnBotJava to test your "new" system sounds
 *
 * Step 1: Create 5 new system sounds and convert them to .wav files.
 *  The names for these files should be as follows (note: names must be lower case):
 *      running.wav
 *      connect.wav
 *      disconnect.wav
 *      warning.wav
 *      error.wav
 *
 * Step 2: Transfer the sound files to the Robot Controller, and place them in the /sdcard/FIRST/blocks/sounds folder
 *         This can be done different ways:
 *          A) Connect the RC to your PC via a USB C cable, and transfer the files using Windows file manager.
 *          B) Connect to the RC via WiFi, and BLOCKS web page http://192.168.43.1:8080/?page=FtcBlocksProjects.html
 *              --  On the BLOCKS page, there is a link called "sounds" on the right hand side of the color bar.
 *                  Use this button to send sound files to the correct folder by default.
 *          C) Using ADB
 *
 * Step 3: Build and deploy this OpMode (using Android Studio, or OnBotJava)
 *
 * Step 4: Run this Teleop OpMode to play your sounds.  Choose your sound using the Gamepad buttons shown on the DS telemetry screen.
 */

@TeleOp(name="Play Sounds", group="Concept")
public class PlaySounds extends LinearOpMode {

    public enum Sound {
        RUNNING("running", Button.LEFT_BUMPER),
        CONNECT("connect", Button.DPAD_UP),
        DISCONNECT("disconnect", Button.DPAD_DOWN),
        WARNING("warning", Button.DPAD_LEFT),
        ERROR("error", Button.DPAD_RIGHT);

        private final String name;
        private final Button button;
        private final File file;

        Sound(final String name, final Button button) {
            this.name = name;
            this.button = button;
            this.file = new File(soundPath + name + ".wav");
        }

        @Override
        public String toString() {
            return name;
        }

        public Button getButton() {
            return button;
        }

        public File getFile() {
            return file;
        }
    }

    /*
     * Point to sound files on the Robot Controller's file system
     */
    private static String soundPath = "/sdcard/FIRST/blocks/sounds/";
    private static boolean idle = true;
    Telemetry.Item playingItem;

    @Override
    public void runOpMode() {

        telemetry.setMsTransmissionInterval(10);
        telemetry.setAutoClear(false);

        showTelemetry();

        while (!isStopRequested()) {

            Sound sound = readButtons();

            /*
             * Play the requested sound if we aren't already playing a sound.
             *
             * This will play the the sound from the first button pressed, in the
             * order defined by the Sound enumeration.
             */
            if (idle) {
                playSound(sound);
            }

            telemetry.update();
        }
    }

    void playSound(Sound sound) {
        if (sound == null || !sound.getFile().exists()) {
            return;
        }

        idle = false;
        playingItem = telemetry.addData("Playing", sound.toString());
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, sound.getFile(), new SoundPlayer.PlaySoundParams(true),
            null,
            () -> handleSoundFinished()
        );
    }

    void handleSoundFinished() {
        idle = true;
        telemetry.removeItem(playingItem);
        telemetry.clear();
        showTelemetry();
    }

    void showTelemetry() {
        telemetry.addLine("Press a gamepad button to play sound\n");

        for (Sound s : Sound.values()) {
            if (s.getFile().exists()) {
                telemetry.addData("To play \'" + s.toString() + "\'", "Press " + s.getButton().name());
            } else {
                telemetry.addData(s.toString(), "Not Found");
            }
        }
        telemetry.addLine(" ");
    }

    /*
     * For simplicity, we'll only play one sound at a time.  Hence, just return the
     * sound that matches the first button pressed.
     */
    Sound readButtons() {
        for (Sound s : Sound.values()) {
            if (s.getButton().isPressed(gamepad1)) {
                return s;
            }
        }
        return null;
    }

    public enum Button {
        LEFT_BUMPER(getField("left_bumper")),
        DPAD_UP(getField("dpad_up")),
        DPAD_DOWN(getField("dpad_down")),
        DPAD_LEFT(getField("dpad_left")),
        DPAD_RIGHT(getField("dpad_right"));

        private final Field field;

        Button(final Field field) {
            this.field = field;
        }

        private static Field getField(String fieldName) {
            try {
                return Class.forName("com.qualcomm.robotcore.hardware.Gamepad").getField(fieldName);
            } catch (ClassNotFoundException e) {
            } catch (NoSuchFieldException e) { }

            return null;
        }

        public boolean isPressed(Gamepad gamepad) {
            try {
                return field.getBoolean(gamepad);
            } catch (IllegalAccessException e) {
                return false;
            }
        }
    }

}
