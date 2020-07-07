# compile

mvn javafx:run

# package

mvn clean compile package


# run

export OPENJFX=/opt/openjfx/armv6hf-sdk/lib
java --module-path $OPENJFX --add-modules javafx.controls,javafx.fxml -jar /home/pi/Desktop/BuzzerGame_2020_WISS/target/IFZ826_LW_Buzzer-0.0.1-SNAPSHOT-jar-with-dependencies.jar application.GameController


