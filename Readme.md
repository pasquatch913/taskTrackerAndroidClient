# taskTrackerAndroidClient

This repo was published as the main consumption point for the [task-tracker project](https://github.com/pasquatch913/task-tracker). You can view the readme on that repo for a high-level view of my intentions for this project.
There are definitely some rough edges as this was my first brush with both Kotlin and Android. Work on this app has definitely been a learning experience, but I'm looking forward to getting the chance to make this application excellent.

# Current state

The Android app is currently in a "usable" state as I've created a home screen appwidget that allows me to mark completions on current tasks without opening the main application.

Here's what the homescreen widget looks like today:

![Task Tracker homescreen widget](https://i.imgur.com/K1lAJbw.jpg)

# Future development goals

1. Convert multi-activity application to one activity with several fragments that are loaded as needed
2. Enhance credential management and include more helpful information in login screen for new users
3. Refine manner in which state is maintained between local and remote dbs
4. Use themes to align styling between appwidget and activity and cut down on styling configuration
5. Reduce clutter on the view tasks activity
6. Allow users to submit "custom completions" to fudge completion time for a particular task (dependent on server app update)
7. Allow users to update task details (dependent on server app update)
