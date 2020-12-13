# CraftBeerApp

This app uses 100% Kotlin.

Architecture:
MVVM (android recommendation).
-Implemented using the NetworkBoundResource, found under the architecture compoments sample project from Google.

Network:
All responses are mapped to different APIResponse types, (also found under the architecture compoments sample project from Google.)

Database:
Have 2 tables, one that contains all beers, and one that contains the ids of the favourite beers, when queying the data locally,
the data is joined to form a new object, that contains the beer data, and the state if its favourite to the user.

DI:
At first I have implemented Dagger, since this is what I have experience with, however I found it exciting to implement Hilt, as recommended by Google/Android. 
(that was fun!)

UI:
-User can favout/ unafavour a beer, just on the beers details fragment.
-The beerDetailsFragment is formed of someiews, and a merged list for additional info (such as ingredients).

Testsing:
I have not covered all the tests required for production. However I implemented tests that cover most of the layers/types to be tested, some of the UI- fragment,
Adapter -ViewHolder, and some objects, like converters.
Please note, I have not used androidTests at all, since it uses the emulator to test, and is time consuming, running all tests, as unitTests, 
using the rebolectric framework, runs all tests on the jvm. (a little anoying when tests are flaky, the view isnt visible to us, but stil worth while. imo.)

-NOTE:
Code I have used from other sources, or I have just re-written the logic, (generally used to move from LiveData to Flow), I have mentioned at the top of the class.

AT LAST:
I actually really enjoyed this task, specially because I got the opportunity to implement HIlt, and figured out sqlite queries (which saves lots of logic in code...) 



