package com.example.water_logging_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
* In order for hilt to even know where to inject the dependencies, it needs to knows where to look for the dependencies.
* This is the entry point for dagger hilt.
*
* It also inherits from Application since it lives throughout the entire app. (From OnCreate -> OnDestroyed)
* And it is created long before anything else
*/

@HiltAndroidApp
class WaterLogApplication : Application()