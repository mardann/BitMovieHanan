# BitMovieHanan
## Job Qualification app for Bit - **Hanan Dann**

## Design
App uses Andrid MVVM architecture. ViewModels emit data to views, who bind to it via DataBinding directly in XML.
Almost no responsibilities are left for the Fragments themselfs.

Notice - the app (hardly) touches API response objects directly. All API and DB responses are mapped into internal **model** objects

API and DB are injected into ViewModels via Hilt DI
Navigation Done with JetPack Navigation.

## Image Caching
Used Glide library to load images from URL into Image Views.
Glide provides robust caching mechanism built-in, including ON-DISK caching.
I added a Job-Scheduler (ClearCacheScheduler.kt) that runs once a day, when device is idel to clear Glide Cache forecfully.

## Favorites
User can toggle Un/Favorite movies in any "Movie List" by clicking the Star icon in upper-right corner of Poster.
This will wirght user selections to internal Room DB, and reflect immediatly in UI.

## Paged Loading
executed with Android "PagingSource"

## Video Playing
Done with YouTube SDK. 
It's realy outdated and not maintained 🤯. So that's why it's implemented by a seperate Activity


## Known Issues
-app isn't pretiest. Uses default Android UI components. But all margins and sizes are uniform (multiples of 4)
-animation- also just basic. If had more time, would do "Shared Element" transitions.
