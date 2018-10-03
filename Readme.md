FilmChecker [![Build Status](https://travis-ci.org/zouroboros/filmchecker.svg?branch=master)](https://travis-ci.org/zouroboros/filmchecker)
===========

A simple android app to check the status of (photographic) film orders. Currently Rossmann (only in Germany), DM (Germany and Austria) and Müller (Germany and Austria) are supported.

The app is available from the [F-Droid](https://f-droid.org/ "F-Droid") app
store.

Pushing Updates
---------------
F-Droid is
[configured](https://gitlab.com/fdroid/fdroiddata/blob/master/metadata/me.murks.filmchecker.txt) to automatically scan this repo and publish new versions.
To release a new version the version string and number has to be incremented in
the app/build.gradle file and a tag consisting of the verionName.0 has to be
created in the master branch. E.g. to publish a new version with the versionName
2.1.1 a tag named 2.1.1.0 needs to exist.

License
-------
FilmChecker is published under the MIT License, see the license file.
