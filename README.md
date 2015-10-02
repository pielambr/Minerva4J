# Minerva4J

[![Join the chat at https://gitter.im/pielambr/Minerva4J](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/pielambr/Minerva4J?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
Java library for Minerva (UGent) - Unofficial

[![Build Status](https://travis-ci.org/pielambr/Minerva4J.svg?branch=master)](https://travis-ci.org/pielambr/Minerva4J)

## Using Minerva4J
This library is pretty easy to use.
```
MinervaClient client = new MinervaClient(username, password)
client.connect()
````
This makes a new client, with your username and password. 
Afterwards you try to login using connect. If the login fails, it throws an exception.
```
client.getCourses();
client.getDocuments(Course course);
client.getAnnouncements(Course course);
client.getEvents();
client.getEvents(Date start, Date end);
```
After that it's easy to get the content that you want. 
You can find more information in the public API.

## Public API
The public API can be found on the Github pages for this project; 
http://pielambr.github.io/Minerva4J

## Using Maven with Minerva4J
You can search the central repository for Minerva4J, or use this in your pom.xml;
```
<dependency>
    <groupId>be.pielambr</groupId>
    <artifactId>Minerva4J</artifactId>
    <version>0.4</version>
</dependency>
```

## Running tests
Before running the tests, fill out your username and password in the src/test/resources/settings.properties file.
After you have done that it's pretty straight forward, just run;
```
mvn test
```

## Suggestions, issues, thoughts, concerns
If any issues arise, then you can always make a github issue, I will probably read it within a few days. You can also send me a tweet [@Pieterjan_] (https://twitter.com/Pieterjan_)
