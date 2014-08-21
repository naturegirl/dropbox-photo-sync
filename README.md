# Dropbox Photo Sync

**Dropbox Photo Sync** aka **PhotoDrops** is an Android app that allows you to sync selected photos on your cell phone with Dropbox. It uses Dropbox's Android Sync API and has been written as a pet project to try out the API. The inspiration came from wanting to make photo transfer between my cell phone and laptop easier. Normally I have to find a cable to connect my phone with my laptop to transfer my photos, or I have to select individual photos and select "share to" -> "dropbox" for each of them, or I am only able to sync all my photos automatically in the background. As I wasn't happy with all these options, I wrote my own syncing app :)

Right now it's still very much work in progress, but feel free to try it out and contribute.

## Usage

The preferred way to get the app is to compile and install from source code. Check out Development for that. Otherwise feel free to ping me to get the apk.

After you have installed the app on your Android phone, you should see a screen with [Connect to Dropbox], [Select photos] and [Sync with Dropbox].
The app automatically syncs to the dropbox folder /Photos/PhotoDrops, overwriting existing photos in there with the same filename.


## Development

PhotoDrops requires a Dropbox account and and Android dev environment in Eclipse to build the project.

To fix the compatibility issues, you additionally need to import appcompat-v7. Go to Import -> Import Existing Android Project. Go to your android SDK directory and select extras/android/support/v7/appcompat as the project. Finish the project import. You should have another project android-support-v7-appcompat now.

Now go to Project Properties -> Android and add android-support-v7-appcompat as a Library ("is Library" doesn't have to be checked). You might have to delete existing broken references.

I'm not sure why Eclipse now always creates these compatibility issues with Android projects, but hopefully this fixes it. After all the errors are gone, just compile and run the project like you would with any other Android project.

If you want to contribute, create issues for bugs or feature requests, or fork the project and hack on it.


## License
``` text
Copyright 2013-2014 Jennifer Guo

Permission is hereby granted, free of charge, to any person obtaining a copy of this
software and associated documentation files (the "Software"), to deal in the 
Software without restriction, including without limitation the rights to use, copy, 
modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
and to permit persons to whom the Software is furnished to do so, subject to the 
following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
