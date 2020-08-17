#!/bin/bash

t_now=$(vcgencmd measure_temp | grep -Eo '[0-9]*\.[0-9]')°C
t_date=$(date +"%Y-%m-%d %H:%M:%S")
echo $t_now $t_date >> /home/pi/raspitemperatur.txt

# as cronjob: */5 * * * * /home/pi/log_temperature.sh
