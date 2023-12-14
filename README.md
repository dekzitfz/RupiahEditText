# RupiahEditText

[![](https://jitpack.io/v/dekzitfz/RupiahEditText.svg)](https://jitpack.io/#dekzitfz/RupiahEditText) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RupiahEditText-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/8038)[![CI/CD](https://github.com/dekzitfz/RupiahEditText/actions/workflows/master.yml/badge.svg?branch=master)](https://github.com/dekzitfz/RupiahEditText/actions/workflows/master.yml)

An auto-formatted edittext android to Indonesia Rupiah Currency.

### Features

- support until hundred-trillion Rupiah
- no 0 (zero) in front of number

### TODO
- have request? [write an issue](https://github.com/dekzitfz/RupiahEditText/issues/new)

### Add to your android project:

#### 1. Update Project level `build.gradle`
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Or if you already using Kotlin DSL
```kotlin
pluginManagement {
    repositories {
        ...
        maven { url = uri("https://jitpack.io") }
    }
}
```

#### 2. Update App level `build.gradle` (Choose your version)

##### Groovy Version
```groovy
//build.gradle
implementation 'com.github.dekzitfz:RupiahEditText:0.1.1'
```

##### Version Catalogs Version
```toml
# libs.versions.toml
[versions]
rupiahedittext = "0.1.1"

[libraries]
rupiah-edittext = { group = "com.github.dekzitfz", name = "RupiahEditText", version.ref = "rupiahedittext" }
```

```kotlin
//build.gradle.kts (App Level)
dependencies {
    ...
    implementation(libs.rupiah.edittext)
}
```

#### 3. Add To Your Layout (XML)
```xml
<id.adiandrea.rupiahedittext.RupiahEditText
        android:id="@+id/rupiah"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

Get the value as `Long`
```kotlin
yourRupiahEditText.value //kotlin
```

```java
yourRupiahEditText.getValue(); //java
```