package levi.ackerman.canardus_reproductus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import levi.ackerman.canardus_reproductus.fragment.AddDuckFragment
import levi.ackerman.canardus_reproductus.fragment.CollectionFragment
import levi.ackerman.canardus_reproductus.fragment.HomeFragment

/**
 * @author Levi Ackerman
 */
class MainActivity : AppCompatActivity() {
    /**
     * calls loadFragment(fragment) to load the right fragment : home by default
     * imports the bottom navigation bar
     * and adds the corresponding title depending on the item clicked (home, collection, add duck)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title);

        //import bottom navigation view
        val navView= findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title);
                    return@setOnNavigationItemSelectedListener true;
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title);
                    return@setOnNavigationItemSelectedListener true;
                }
                R.id.add_duck_page -> {
                    loadFragment(AddDuckFragment(this), R.string.add_duck_page_title);
                    return@setOnNavigationItemSelectedListener true;
                }
                else -> false;
            }
        }
    }

    /**
     * get the right fragment to change from one fragment to another = changing pages
     * @param fragment: Fragment
     */
    private fun loadFragment(fragment: Fragment, string: Int) {
        /**
         * load DuckRepository
         */
        val repo = DuckRepository();

        //update page title with int ref
        findViewById<TextView>(R.id.page_title).text = resources.getString(string);
        //update the duck list
        repo.updateData{
            /**
             * injects a fragment in the FrameLayout in activity_main.xml
             */
            val transaction = supportFragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}