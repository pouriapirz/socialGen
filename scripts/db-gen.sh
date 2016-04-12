if [ $# -lt 3 ]
then
   echo  "Usage DataGenerator <SOCIAL_GEN_HOME> <SOCIAL_GEN_LOCAL_HOME> <partition>"
   exit 1
fi

SOCIAL_GEN_HOME=$1
shift
SOCIAL_GEN_LOCAL_HOME=$1
shift
PARTITION=$1
shift

java -Xms1g -Xmx2g -cp $SOCIAL_GEN_LOCAL_HOME/target/SocialGen.jar socialGen.DataGenerator $SOCIAL_GEN_HOME $PARTITION $@
