#!/bin/bash

export OPENJFX=/usr/share/openjfx/lib
java --module-path $OPENJFX --add-modules javafx.controls,javafx.fxml -jar /home/pi/Desktop/BuzzerGame_2020_WISS/target/IFZ826_LW_Buzzer-0.0.1-SNAPSHOT-jar-with-dependencies.jar

