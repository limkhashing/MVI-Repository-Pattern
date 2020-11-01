package io.limkhashing.mvirepositorypattern.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.limkhashing.mvirepositorypattern.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG: String = "AppDebug"

//    @ExperimentalCoroutinesApi
//    @Inject
//    lateinit var fragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        // Hilt will auto inject before super.oncreate()
        // this might cause problem because if rotation happen fragment that created
        // would not be able to find constructor that created before
        // Workaround: MainNavHostFragment
//        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.fragmentFactory = fragmentFactory
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fragment_container, MainFragment::class.java, null)
    }
}