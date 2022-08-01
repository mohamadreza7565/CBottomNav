# FindRandom

[![](https://jitpack.io/v/mohamadreza7565/CBottomNav.svg)](https://jitpack.io/#mohamadreza7565/CBottomNav)


![alt text](https://github.com/mohamadreza7565/CBottomNav/blob/master/ScreenShot/gif_1.gif?raw=true)
![alt text](https://github.com/mohamadreza7565/CBottomNav/blob/master/ScreenShot/gif_3.gif?raw=true)


# Gradle

## Dependency

## Step 1. Add the JitPack repository to your build file

```Gradle

allprojects {
     repositories {
	...
     maven { url 'https://jitpack.io' }
	}
}
```

## Step 2. Add the dependency

```Gradle

dependencies {
	 implementation 'com.github.mohamadreza7565:CBottomNav:lastVersion'
}

```

# Gradle

## Maven

## Step 1. Add the JitPack repository to your build file

```Gradle

<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
```

## Step 2. Add the dependency

```Gradle

	<dependency>
	    <groupId>com.github.mohamadreza7565</groupId>
	    <artifactId>CBottomNav</artifactId>
	    <version>Tag</version>
	</dependency>

```

### Examples

```xml

<com.mohamadreza7565.cbottomnav.CBottomNavigation 
    android:id="@+id/activityHome_bottomBar"
    android:layout_width="match_parent"
    android:layout_height="75dp"
    android:layout_alignParentBottom="true"
    app:menuChildView="@menu/menu" />

```

### With navigation component

```kotlin

private var currentNavController: LiveData<NavController>? = null

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
        setupBottomNavigationBar()
    }

}


override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    setupBottomNavigationBar()
}


private fun setupBottomNavigationBar() {
    val bottomNavigationView = findViewById<CBottomNavigation>(R.id.activityHome_bottomBar)

    val navGraphIds = listOf(
        R.navigation.home,
        R.navigation.setting,
    )

    val controller = bottomNavigationView.setupWithNavController(
        navGraphIds = navGraphIds,
        fragmentManager = supportFragmentManager,
        containerId = R.id.nav_host_container,
        intent = intent
    )

    currentNavController = controller

}

override fun onSupportNavigateUp(): Boolean {
    return currentNavController?.value?.navigateUp() ?: false
}

```

## With custom action in activity

```kotlin



bottomNavigationView.setOnTabItemClickListener(object : CBottomNavigation.OnTabItemClickListener {
    override fun onTabClicked(item: MenuItem, reselect: Boolean) {
        when (item.itemId) {
            R.id.home -> {
                // Change fragment or activity
            }
            R.id.setting -> {
                // Change fragment or activity
            }
        }
    }

})

```

## Change the function of one of the navigationBar items

```kotlin

bottomNavigationView.setSelectedTab(R.id.home,
    object : CBottomNavigation.OnOneTabItemClickListener {
        override fun onTabClicked() {
            // Do action
        }
    })

```

## Attributes

app.menuChildView="@menu/menu"
---------------------------------------
app.background="@drawable/bottomNavigationBackground"
---------------------------------------
app.textColor="@color/textBottomNavigationColor"
---------------------------------------
app.textSize="@dimens/textBottomNavigationSize"
---------------------------------------
app.textShadowColor="@color/textBottomNavigationShadowColor"
---------------------------------------
app.iconTint="@color/iconBottomNavigationColor"
---------------------------------------
app.badgeTextColor="@color/textBottomNavigationBadgeColor"
---------------------------------------
app.badgeBackground="@drawable/textBottomNavigationBadgeBackground"
---------------------------------------


