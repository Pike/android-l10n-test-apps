Android L10n Tests
==================

This repo is basically my eclipse workspace with the test apps. To install the
apps below, you may need to switch on 3rd-party install location in your
settings.

Now, to the apps:

AllLocales
----------

This app let's you iterate through all Mozilla-hosted locales, which at least 
build on android. That excludes all three-letter language codes, sadly, as
well as `ja-JP-mac` and `x-testing`. There's
[an apk](http://bit.ly/s4e3Wy) to test out, too.
There are two forms of test results, locales for which there is no font
support, and locales not supported at all. Leave the test results for
your device on 
[the wiki](https://wiki.mozilla.org/L10n:Mobile/Android/LocaleOSDeviceMatrix).

PluralChecker
-------------

This app goes through all our Mozilla-hosted locales and gathers the values of
the built-in plural logic. Also, there's [an apk](http://bit.ly/u6jfQN) for
this. The result is a text area which you can copy and paste to
[the wiki](https://wiki.mozilla.org/L10n:Mobile/Android/PluralForms).