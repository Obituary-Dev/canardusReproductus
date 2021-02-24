package levi.ackerman.canardus_reproductus.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Levi Ackerman
 */
class DuckItemDecoration: RecyclerView.ItemDecoration() {

    /**
     * adds an offset from the bottom rect = rectangle from the component
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = 0
    }

}