if [ $# -lt 3 ]
then
   echo  "Usage DataGenerator <SOCIAL_GEN_HOME> <SOCIAL_GEN_LOCAL_HOME> <partition>"
   exit 1
fi

social_gen_home=$1
social_gen_local_home=$2
partition=$3

java -Xms1g -Xmx2g -cp $2/target/SocialGen.jar socialGen.DataGenerator $1 $3
