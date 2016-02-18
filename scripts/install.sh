if [ -z $SOCIAL_GEN_HOME ]
then 
  echo "SOCIAL_GEN_HOME is not defined"
  exit 1
fi 
if [ -z $SOCIAL_GEN_LOCAL_HOME ]
then 
  echo "SOCIAL_GEN_LOCAL_HOME is not defined"
  exit 1
fi 

for i in `cat $SOCIAL_GEN_HOME/conf/machines`
do
    ssh $i mkdir -p $SOCIAL_GEN_LOCAL_HOME
    ssh $i mkdir -p $SOCIAL_GEN_LOCAL_HOME/target
    scp -q $SOCIAL_GEN_HOME/target/SocialGen.jar $i:$SOCIAL_GEN_LOCAL_HOME/target
    scp -q -r $SOCIAL_GEN_HOME/conf $SOCIAL_GEN_HOME/metadata $SOCIAL_GEN_HOME/output $SOCIAL_GEN_HOME/scripts $i:$SOCIAL_GEN_LOCAL_HOME &
done
