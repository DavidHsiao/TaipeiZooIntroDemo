package android.example.com.taipeizoointrodemo

import android.example.com.taipeizoointrodemo.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        // 設定Action bar with 回去上一頁的箭頭
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        // 讓NavigationUI認識navView
        NavigationUI.setupWithNavController(binding.navView, navController)

        // 鎖住側滑關閉功能
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

//        // prevent nav gesture if not on start destination
//        // 判斷只讓Navigation Drawer出現在首頁(可被側滑開啟)，其它頁面就關閉側滑開啟的功能。
//        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
//            if (nd.id == nc.graph.startDestination) {
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            } else {
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        // 不需要Drawer的按鈕，只需要讓後面頁面有返回鍵頭時使用
        return navController.navigateUp()
        // 讓Drawer的按鈕出現在Action Bar的左上方並可使用
//        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}
