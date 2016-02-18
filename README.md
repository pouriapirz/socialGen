# SocialGen
**SocialGen** is a tool for generating large quantities of synthetic data that models status updates/tweets from users of two imaginary social networks. SocialGen can be configured to run in a parallel/distributed fashion generating partitions of data in parallel using a cluster of machines. SocialGen takes care of managing (primary) key conflicts across data partitions.

Social-Gen generates data in Asterix Data Model (ADM) format. Here is a sample data type definition for the output of SocialGen:

```
create type EmploymentType as {
organization_name: string,
start_date: date,
end_date: date?
}

create type FacebookUserType as {
id: int64,
alias: string,
name: string,
user_since: datetime,
friend_ids: {{ int64 }},
employment: [EmploymentType]
}

create type FacebookMessageType as {
message_id: int64,
author_id: int64,
in_response_to: int64?,
sender_location: point,
send_time: datetime,
message: string
}

create type TwitterUserType as {
screen_name: string,
lang: string,
friends_count: int32,
statuses_count: int32,
name: string,
followers_count: int32
}

create type TweetMessageType as {
tweetid: int64,    
user: TwitterUserType,
sender_location: point,
send_time: datetime,
referred_topics: {{ string }},
message_text: string
}
```


# Using SocialGen
## Prerequisites
* JDK 1.8+
* Maven 3.1.1 or greater
