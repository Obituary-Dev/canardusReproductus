package levi.ackerman.canardus_reproductus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.duckList
import levi.ackerman.canardus_reproductus.MainActivity
import levi.ackerman.canardus_reproductus.R
import levi.ackerman.canardus_reproductus.adapter.DuckAdapter
import levi.ackerman.canardus_reproductus.adapter.DuckItemDecoration

/**
 * @author Levi Ackerman
 */
class HomeFragment(private val context: MainActivity) : Fragment() {

    /**
     * injects layout: fragment_home with its horizontal and vertical display (calls the recycler view from XML)
     * @return view: View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false);

        //gets the RecyclerView
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view);
        horizontalRecyclerView.adapter = DuckAdapter(context, duckList.filter { !it.liked }, R.layout.item_horizontal_duck);

        //gets the second RecyclerView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view);
        verticalRecyclerView.adapter = DuckAdapter(context, duckList, R.layout.item_vertical_duck);
        verticalRecyclerView.addItemDecoration(DuckItemDecoration());

        return view;
    }

}