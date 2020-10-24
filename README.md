# SMS Sync

This is an application to back up your SMS messages!

## Motivation

Nearly everything in the virtual world can be backed up, indexed, and searched...except texts. For some reason, this archaic form of communication
got left behind in the universe of data that lives forever, and I wanted that to change. I could only find one SMS sync app on the Play Store that
looked trustworthy, but unfortunately, it was plagued with two issues: 

- It backed up messages to gmail, which fills up the storage quota, especially if you text a lot of pictures
- It seemed to be very unreliable in terms of regularly syncing, which may have been user error, but it was still a problem.

Therefore, I wanted to make my own app!

## Stack

Due to current best practices in Android app development, the app is actually exceedingly simple. The app contains a Worker task that:

1. Gets the date of the most recently synced text
2. If (1) is null, syncs all messages, and if not, syncs any newer messages
3. Retrieves the sqlite database for the app as a file
4. Uploads that file to the cloud

It really is that easy! This probably would have been very difficult even 4 years earlier than when I started this project,
but thanks to Android Jetpack, writing Android apps is very easy!
