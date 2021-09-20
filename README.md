# Code Documentation
## Finished Tasks:
- Project overview  : 
    - Added Fragments and directions
    - Added safe-args 
    - Added dynamic titles of toolbars
    
- Web request [CategoryListFragment] :
   - Created RecyclerView and Adapter
   - Created CategoriesInteractor which act as repository to handle data operation (Getting data from the API in this Case)
   - Updated Category column in the database and provided migration using dagger Hilt to migrate the database
   - Used Coroutines to get amount per each category asynchronously then update the RecyclerView adapter
  

- Database [SearchFoodFragment] :
  - Created RecyclerView and Adapter
  - Created FoodsInteractor which act as repository to handle data operation (Getting data from db) 
  - Getting data Paginated using Jetpack Paging V3
  - Show the paginated data in the UI

- Search [SearchFoodFragment]
   - Add SearchView in the toolbar
   - Handle search button clicked & and keyboard click
   - Added search Query in food dao
   - Edited FoodsInteractor to handle the operation
   - Update The RecyclerView with results with smooth animations
  
- Food details [FoodFragment]
    - Designed The same screen in figma 
    - User safe-args to get the details of food
    - Set food title to Toolbar title
    - Updated Category button from Rest API
    - Used a ActivityResultContracts to capture the photo and use Coil to load it in the imageView using dataBinding adapter
       - TODO : save the Uri as String in the database to load the image again
 - Category details 
   -  used SearchFood adapter
   -  setup navigation, safe-args and changing the title of the toolbar
   -  Getting the data using paging V3

## Extra :
   - Chaned (fragment to lifecycleOwner) in all fragments to avoid memory leak
   - Added 2 styles to theme Button and FloatingActionButton
   - Adding Progreebar while loading data from API






# TACO 🌮
#### [T]he [A]ndroid [C]hallenge :-[O]
Version 1.0


### General Instructions
- Fork this repository with your private GitHub account
- After you are finished provide access to your fork for the user "sebastian-hoebarth"
- Try to use libraries that are included in Android Jetpack before using any other 3rd party library
- Try to support and use Material Design where ever possible
- Use MVVM, Single Activity and other concepts where every you can...
- The challenge is divided into separate parts, complete as many as you can
- Please stop if you are hitting the 4 hour time frame!!
- Good luck & enjoy your TACOs 🌮


### Pre-setup
The following structure and libraries are already provided. Please do not start a new project,
use the current setup to continue in order to save time.
##### This project provides
- An unfinished basic Android App implementation to start width
- Dependency injection with Hilt
- Database access with Room [FoodDb]
- REST service client with Retrofit [TacoService]
- List view item layouts
- Basic navigation pattern
- ViewModels & Databinding
(if you are not familiar with any of those libraries, please check them out) 

##### Links to Libraries you might find helpful
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][4] - An Android testing framework for unit and runtime UI tests.
* [Architecture][10] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Data Binding][11] - Declaratively bind observable data to UI elements.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.
  * [Navigation][14] - Handle everything needed for in-app navigation.
  * [Room][16] - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [WorkManager][18] - Manage your Android background jobs.
* [UI][30] - Details on why and how to use UI Components in your apps - together or separate
  * [Animations & Transitions][31] - Move widgets and transition between screens.
  * [Fragment][34] - A basic unit of composable UI.
  * [Layout][35] - Lay out widgets using different algorithms.
* Third party and miscellaneous libraries
  * [Glide][90] for image loading
  * [Hilt][92]: for [dependency injection][93]
  * [Kotlin Coroutines][91] for managing background threads with simplified code and reducing needs for callbacks

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[90]: https://bumptech.github.io/glide/
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[92]: https://developer.android.com/training/dependency-injection/hilt-android
[93]: https://developer.android.com/training/dependency-injection


## Your tasks

###### 1. Project overview (Navigation & wire frames)
Use Jetpack Navigation to navigate between Fragments.
Use ViewModel to hold the content.
Store content in LiveData, access it in views with databinding.

Make yourself familiar with the project. It's a single activity-based architecture.
Here is an example of how your final UI structure should look like:<br>
![GitHub Logo](/images/nav.webp)<br>
You need to create all missing Fragments by yourself.

This is your starting point:<br>
![GitHub Logo](/images/baseui.webp)<br>
![GitHub Logo](/images/main.webp)<br>
(use bottom bar navigation to switch between [CategoryListFragment] and [SearchFoodFragment])

###### 2. Web request [CategoryListFragment]
Request and display all taco categories in a list.
Show the list of categories in a two-column staggered RecyclerView.
Use the remote repository with REST Api [TacoService]. Documentation can be found here:
https://taco-food-api.herokuapp.com/#api-Category-GetCategoriesList
Use "item_category.xml" layout as list item. (use databinding)
Load the total amount of "Food" items referenced in a category and display that information in the item.

TODO:
- Create list view
- Create Adapter & ViewHolder
- Load content from REST client
- Async update item amount per category from the local database

###### 3. Database [SearchFoodFragment]
Access the local database [FoodDb] provided in the App assets to load "food" information.
Display all food elements in a single column list view.
Use paging adapters, not load all search results at once into your list.
Use "item_food.xml" layout as list item.

TODOs:
- Create list view
- Create Adapter & ViewHolder
- Load paged content from database

###### 5. Search [SearchFoodFragment]
Add a search box into the toolbar, filter local database [FoodDb] food results by the search term.
Filter the search results based on "description" of a food item.

###### 6. Food details [FoodFragment]
Selecting an item on SearchFoodFragment should lead to the detail screen of a food (FoodFragment). 
More details of a single food should be displayed on that screen, exactly like
the UI/UX from Figma describes it UI components. (Font, Color, Shape, Size...)
Load missing category name information from the web endpoint.

![GitHub Logo](/images/detail.webp)

TODOs:
- Create new Fragment
- Use navigation component and safe args
- Link [SearchFoodFragment] item to open [FoodFragment]
- Build UI/UX exactly like described in Figma (free registration required)
https://www.figma.com/file/5UjQCIHu8yv9si2tdBiO47/Taco---Detail?node-id=0%3A1
- Async update category name with REST API

###### 7. Add an image to a food [FoodFragment]
Let the user add an Image via an ImagePicker or Camera to a single food item. The image should be stored locally
and displayed when the user shows the detailed food item.

TODOs:
- Make sure to use ActivityResultContracts. 
- Use an image cache library to display a picture

###### 8. Load category name on food detail screen [FoodFragment]
Request category name for this food item from web dynamically when it's shown on the detail screen.
By clicking on the category the user can jump to the category detail page [CategoryFragment]. 

###### 9. Category details [CategoryFragment]
Selecting a category in the [CategoryListFragment] or from [FoodFragment] opens the [CategoryFragment], showing all 
foods that are added to this category. Show the category name at the top, use navigation arguments to 
send information between fragments. Access the food database [FoodDb] and query all foods that
have the selected category, again use paging. Display preview information of a food item. 
Might make sense to use the same 

TODOs:
- Create CategoryFragment
- Setup navigation component and safe args
- Prepare database query
- Tip: reuse [SearchFoodFragment]

## 🏁 Congratulation you finished the challenge 🏁


### Some extra candy
#### If you are still hungry for more, choose one of the below bonus assignments for implementation. Happy coding!

###### 🍭 Web requests
On mobile devices, the internet might fail due to connection issues.
Prepare error indications when content can't be loaded from the web.

###### 🍭 Edge to Edge 
Work with display insets and create an edge to edge feel. Top and bottom bars should fade
away when scrolling in a List view. 
Tip: use helper library from Chris
https://github.com/chrisbanes/insetter

###### 🍭 Create a custom theme
Introduce your custom design for font, colors, small medium and large components.
Tip: checkout material design guidelines
https://material.io/design/material-studies/fortnightly.html#shape

###### 🍭 Memory leak
Finding memory leaks is a very important task when developing high-quality apps. 
Make sure there is no memory leak. I might have inserted one ;-)
Tip: there is a nice library for detecting leaks
https://square.github.io/leakcanary/

###### 🍭 Performance
Developing apps for all kinds of devices is essential on Android and therefore performance 
optimization is crucial. Initialize all necessary libraries before Application.onCreate() is executed.
Move your DB initialization into a content provider, checkout this helper library
https://developer.android.com/topic/libraries/app-startup

###### 🍭 Deep linking
Make a single taco category accessible via url (e.g: https://taco-food-api.herokuapp.com/api/v1/category/1)

###### 🍭 Fragment transitions
Use Material motion transitions of your choice to transform between fragments.
Take a look at material components API

- Tip: https://codelabs.developers.google.com/codelabs/material-motion-android

## Footnote
#### Make sure everything is pushed to your git repository. Please inform us when you have finished the challenge. We are not automatically notified.
#### Tip: don't forget about code documentation
#### We’d be happy to receive your feedback on the challenge. Let us know how you liked it.
