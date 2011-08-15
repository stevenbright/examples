# !/bin/bash

JBOSSESB_ROOT="/home/bob/jbossesb/jbossesb-server-4.9"
SERVER_JARS=$(ls $JBOSSESB_ROOT/server/default/lib/*.jar | sed ':a;N;$!ba;s/\n/:/g')
SAR_JARS=$(ls $JBOSSESB_ROOT/server/default/deploy/jbossesb.sar/lib/*.jar | sed ':a;N;$!ba;s/\n/:/g')
export JARS="/hostshare/h.jar:$SAR_JARS:$SERVER_JARS:$(./bin/classpath.sh -c)";
#echo $JARS

#for JAR_FILE in `echo $JARS | sed 's/:/\n/g'`
#do
#       echo "Contents of JAR $JAR_FILE :"
#       echo `zipinfo -1 $JAR_FILE`;
        #echo "in $JAR_FILE"
        #RESULT=`unzip -l $JAR_FILE | grep Message.class`
        #if [ -n "$RESULT" ]; then
        #       echo "in $JAR_FILE :"
        #       echo $RESULT
        #       echo ""
        #fi
#done

java -classpath $JARS com.mysite.jbossesb.App jms ehw
