# RupiahEditText

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
implementation 'com.github.dekzitfz:RupiahEditText:0.0.1'
```

Add To Your Layout
```xml
<id.adiandrea.rupiahedittext.RupiahEditText
        android:id="@+id/rupiah"
        android:inputType="number"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```