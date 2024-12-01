object Builds {
    object Release {
        const val name = "release"
        const val isMinifyEnabled = true
        const val isShrinkResources = true
        const val isDebuggable = false
    }

    object Debug {
        const val name = "debug"
        const val isMinifyEnabled = false
        const val isShrinkResources = false
        const val isDebuggable = true
    }
}