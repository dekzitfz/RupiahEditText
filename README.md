# RupiahEditText

[![](https://jitpack.io/v/dekzitfz/RupiahEditText.svg)](https://jitpack.io/#dekzitfz/RupiahEditText) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RupiahEditText-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/8038)

An auto-formatted edittext android to Indonesia Rupiah Currency.

### Add to you android project:

Project level `build.gradle`
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

App level `build.gradle`
```groovy
implementation 'com.github.dekzitfz:RupiahEditText:(insert latest version)'
```

Add To Your Layout
```xml
<id.adiandrea.rupiahedittext.RupiahEditText
        android:id="@+id/rupiah"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

Get the value as Long
```kotlin
yourRupiahEditText.value //kotlin
```

```java
yourRupiahEditText.getValue(); //java
```