# Pre-work - *ToDoApp*

**ToDoApp** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Rashmi Sharma**

Time spent: **8** hours spent in total

## User Stories

The following **required** functionality is completed:

* [ X ] User can **successfully add and remove items** from the todo list
* [X ] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [ X ] User can **persist todo items** and retrieve them properly on app restart

All this can be found in first version.

The following **optional** features are implemented:

* [ X ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ X ] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ X ] Add support for completion due dates for todo items (and display within listview item)
* [ X ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ X ] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Android app development is smooth in most ways, but what I found little laborious and intriguing is various layouts we have for displaying screens and interplay between them. I have not really worked with any UI applications per-say, mostly all backend stuff, so this is something which I would need to learn.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** 
ArrayAdaptor is Adaptor design pattern, where-in two seemingly different objects interact to perform single operation. 
In ListView, we might want our own custom way a list is displayed, Adaptor's make it possible, as we can define our custom object's attributes and view mapping in CustomAdaptor, and after we pass adaptor object in ListView, view can ask custom adaptor to render view when required. This is how, developer's logic and view can work seamlessly with Android framework.

## Notes

Describe any challenges encountered while building the app.

1) As I mentioned earlier, I am bad with whole UI stuff, layouts were a hard time for me.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
