if [ -z $SOCIAL_GEN_HOME ]
then
   echo  "SOCIAL_GEN_HOME is undefined"
   echo " For a description fo SOCIAL_GEN_HOME, please follow README.txt"
   exit 1
fi

if [ -z $SOCIAL_GEN_LOCAL_HOME ]
then
   echo  "SOCIAL_GEN_LOCAL_HOME is undefined"
   echo " For a description fo SOCIAL_GEN_LOCAL_HOME, please follow README.txt"
   exit 1
fi

java -cp $SOCIAL_GEN_HOME/target/SocialGen.jar socialGen.PreDataGenerator $SOCIAL_GEN_HOME

for i in `cat $SOCIAL_GEN_HOME/output/partitions`
do
   machine=`echo $i |cut -d ':' -f1`
   partition=`echo $i |cut -d ':' -f2`
   localpath=`echo $i |cut -d ':' -f3`

   echo "$machine $partition $localpath"

  # machine=${i%%:*}
  # partition=${i##*:}
  ssh $machine $SOCIAL_GEN_LOCAL_HOME/scripts/db-gen.sh $SOCIAL_GEN_HOME $SOCIAL_GEN_LOCAL_HOME $partition "$@" &
done
