package levi.ackerman.canardus_reproductus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import levi.ackerman.canardus_reproductus.DuckRepository.Singleton.duckList
import levi.ackerman.canardus_reproductus.MainActivity
import levi.ackerman.canardus_reproductus.R
import levi.ackerman.canardus_reproductus.adapter.DuckAdapter
import levi.ackerman.canardus_reproductus.adapter.DuckItemDecoration

/**
 * @author Levi Ackerman
 */
class CollectionFragment(private val context: MainActivity) : Fragment() {


    /**
     * creates view with inflate + corresponding layout, retrieve recycler view created in the layout, sets the adapter, and the layout manager
     * @return view: View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false);
        //gets recycler view
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list);
        collectionRecyclerView.adapter = DuckAdapter(context, duckList.filter { it.liked }, R.layout.item_vertical_duck);
        collectionRecyclerView.layoutManager = LinearLayoutManager(context);
        collectionRecyclerView.addItemDecoration(DuckItemDecoration());
        return view;
    }

}