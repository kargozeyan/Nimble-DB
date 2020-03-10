# Nimble DB
 Nimble-DB is a simple and fast object storage for Android (Java/Kotlin objects)
 
## Download
``` gradle
implementation 'com.karen.nimbledb:nimbledb:1.0.0'
```
## Usage
First of all, call this method in Application.onCreate (UI thread)
``` kotlin
Nimble.initialize(context)
```
### Sections
You can either use default section
``` kotlin 
Nimble.on()
```
Or your custom section
``` kotlin
Nimble.on("YOUR SECTION NAME")
```
### Put
Its very easy to put any object
``` kotlin
Nimble.on().put("YOUR KEY", data)
```
### Get
Getting is simple and easy as well as putting 
``` kotlin
val data = Nimble.on().get("YOUR_KEY")
```
In addition, you can get all keys of any section
``` kotlin
val allKeys - Nimble.on().allKeys
```
### Remove
You can remove with exact key
``` kotlin
Nimble.on().remove("YOUR_KEY")
```
Or you can remova all obects
``` kotlin
Nimble.on().removeAll()
```
### Other features
To check if object exists or not you should call
``` kotlin
val objectExists = Nimble.on().exists("key")
```
## License

```
Copyright 2020 Karen Gozeyan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
