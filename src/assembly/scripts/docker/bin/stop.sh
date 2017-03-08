#!/bin/sh

DIRNAME=`dirname $0`
RUNHOME=`cd $DIRNAME/;pwd`
echo @RUNHOME@ $RUNHOME

if [ -f "$RUNHOME/setenv.sh" ];then
    . "$RUNHOME/setenv.sh"
else
    echo "can not found $RUNHOME/setenv.sh"
fi

echo =================ENV INFO=================
echo RUNHOME=$RUNHOME
echo Main_Class=$Main_Class
echo APP_INFO=$APP_INFO
echo ==========================================

cd $RUNHOME;pwd

save_app_pid(){
    app_id=$(ps -ef | grep $Main_Class | grep $RUNHOME | grep -v grep | awk '{print $2}')
    echo @app_id@  $app_id
}

kill_app_process(){
    ps -p $app_id
    if [ $? == 0 ];then
        kill -9 $app_id
    fi
}

#################
##### main ######
#################
save_app_id
echo @Kill_SmartSight_Policy@ kill -9 $app_id
kill_app_process